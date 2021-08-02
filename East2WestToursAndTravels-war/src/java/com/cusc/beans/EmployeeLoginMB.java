/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Employees;
import com.cusc.entities.Tours;
import com.cusc.helps.CommonConstant;
import com.cusc.helps.ImageTools;
import com.cusc.helps.NotificationTools;
import com.cusc.helps.PasswordTools;
import com.cusc.sessionbean.EmployeesFacadeLocal;
import com.cusc.sessionbean.ToursFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.Part;

/**
 *
 * @author Admin
 */
@Named(value = "employeeLoginMB")
@SessionScoped
public class EmployeeLoginMB implements Serializable {

    @EJB
    private ToursFacadeLocal toursFacade;

    @EJB
    private EmployeesFacadeLocal employeesFacade;

    private Employees empSignedIn;
    private String username = "";
    private String password = "";
    private String notice = "";
    private String message = "";
    private boolean isLoggedIn = false;

    private Part file;
    private static final String UPLOAD_DIRECTORY = "imgEmployees";
    private static final String BEAN_OBJECT = "profile";
    private boolean gender = true;
    private String verifyPassword = "";
    private String newPassword = "";

    public EmployeeLoginMB() {
    }

    public String login() {
        if (username.equals("")) {
            setNotice("You must enter your username.");
            return "adminLoginPage";
        } else if (password.equals("")) {
            setNotice("You must enter your password.");
            return "adminLoginPage";
        } else {
            Employees isValidUser = employeesFacade.login(username, PasswordTools.encrypt(password));
            if (isValidUser != null) {
                empSignedIn = isValidUser;
                if (empSignedIn.getStatus() == 0) {
                    setNotice("Your account has been blocked. You may get in touch administrator to know information!");
                    setPassword("");
                    return "adminLoginPage";
                } else {
                    setIsLoggedIn(true);
                    setNotice(NotificationTools.success("Congratulation! You signed in successfully."));
                    return "admin/mainLayout";
                }
            } else {
                setNotice("Username or password is incorrect.");
                setPassword("");
                return "adminLoginPage";
            }
        }
    }

    public String logout() {
        setUsername("");
        setPassword("");
        setVerifyPassword("");
        setNewPassword("");
        setIsLoggedIn(false);
        setEmpSignedIn(null);
        return "/adminLoginPage";
    }

    public List<Tours> showYourCreatedTours(int id) {
        return toursFacade.findToursByEmployee(id);
    }

    public void updateProfile() {
        try {
            Employees c = employeesFacade.find(empSignedIn.getEmployeeId());
            boolean isInvalid = false;
            if (empSignedIn.getFirstName().length() < 1 || empSignedIn.getFirstName().length() > 20) {
                notice += NotificationTools.error("The length of the firstname must be between 1 and 20 characters.");
                isInvalid = true;
            }
            if (empSignedIn.getLastName().length() < 1 || empSignedIn.getLastName().length() > 20) {
                notice += NotificationTools.error("The length of the lastname must be between 1 and 20 characters.");
                isInvalid = true;
            }
            if (empSignedIn.getBirthDate() == null) {
                notice += NotificationTools.error("Customer's birthdate must be entered.");
                isInvalid = true;              
            }
            if (empSignedIn.getBirthDate() != null && !dobIsValid(empSignedIn.getBirthDate())) {
                    notice += NotificationTools.error("Customer's age must be greater than 14.");
                    isInvalid = true;
                }
            if (empSignedIn.getPhone().length() < 5 || empSignedIn.getPhone().length() > 20) {
                notice += NotificationTools.error("The length of the phone number must be between 5 and 20 characters.");
                isInvalid = true;
            }
            if (empSignedIn.getEmail().length() < 5 || empSignedIn.getEmail().length() > 60) {
                notice += NotificationTools.error("The length of the email must be between 5 and 60 characters.");
                isInvalid = true;
            }
            if (employeesFacade.validateExistedEmail(empSignedIn.getEmail(), c.getEmail())) {
                notice += NotificationTools.error(CommonConstant.EXISTED_EMAIL_NOTICE);
                isInvalid = true;
            }
            if (empSignedIn.getAddress().length() < 5 || empSignedIn.getAddress().length() > 120) {
                notice += NotificationTools.error("The length of the address must be between 5 and 120 characters.");
                isInvalid = true;
            }
            if (file != null) {
                String result = ImageTools.uploadFile(file, UPLOAD_DIRECTORY);
                switch (result) {
                    case "":
                        notice += NotificationTools.info(CommonConstant.IMAGE_IS_NULL_NOTICE);
                        isInvalid = true;
                        break;
                    case CommonConstant.FILE_IO:
                        notice += NotificationTools.error(CommonConstant.UPLOADING_FAIL_NOTICE);
                        isInvalid = true;
                        break;
                    case CommonConstant.FILE_EXTENSION:
                        notice += NotificationTools.error(CommonConstant.INVALID_IMAGE_EXTENSION_NOTICE);
                        isInvalid = true;
                        break;
                    case CommonConstant.FILE_SIZE:
                        notice += NotificationTools.error(CommonConstant.INVALID_FILE_SIZE_NOTICE);
                        isInvalid = true;
                        break;
                    default:
                        c.setAvatar(result);
                        ImageTools.deleteFile(empSignedIn.getAvatar(), UPLOAD_DIRECTORY);
                        break;
                }
            }
            if (isInvalid) {
                message = NotificationTools.editModal(c.getEmployeeId());
                setEmpSignedIn(c);
                return;
            }
            c.setFirstName(empSignedIn.getFirstName());
            c.setLastName(empSignedIn.getLastName());
            c.setGender(gender);
            c.setBirthDate(empSignedIn.getBirthDate());
            c.setPhone(empSignedIn.getPhone());
            c.setEmail(empSignedIn.getEmail());
            c.setAddress(empSignedIn.getAddress());

            employeesFacade.edit(c);
            setEmpSignedIn(c);
            notice = NotificationTools.updateSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.updateFail(BEAN_OBJECT);
        }
    }

    private boolean dobIsValid(Date value) {
        Date today = new Date(System.currentTimeMillis());
        if ((today.getYear() - value.getYear()) == 14) {
            if ((today.getMonth() - value.getMonth()) == 0) {
                if ((today.getDate() - value.getDate()) > 0) {
                    return false;
                }
            } else if ((today.getMonth() - value.getMonth()) > 0) {
                return false;
            }
        } else if ((today.getYear() - value.getYear()) < 14) {
            return false;
        }
        return true;
    }
    
    public void changePassword() {
        if (!PasswordTools.encrypt(password).equals(empSignedIn.getPassword())) {
            notice = NotificationTools.error("Your current password is inccorrect.");
        } else if (newPassword.length() < 8) {
            notice = NotificationTools.error("Your new password must greater than 8 characters.");
        } else if (!newPassword.equals(verifyPassword)) {
            notice = NotificationTools.error("Your verified password is not matched.");
        } else {
            try {
                Employees c = employeesFacade.find(empSignedIn.getEmployeeId());
                c.setPassword(PasswordTools.encrypt(newPassword));
                employeesFacade.edit(c);
                setEmpSignedIn(c);
                setPassword("");
                setVerifyPassword("");
                setNewPassword("");
                notice = NotificationTools.updateSuccess("password");
            } catch (Exception ex) {
                setPassword("");
                setVerifyPassword("");
                setNewPassword("");
                notice = NotificationTools.updateFail("password");
            }
        }
    }

    public Employees getEmpSignedIn() {
        return empSignedIn;
    }

    public void setEmpSignedIn(Employees empSignedIn) {
        this.empSignedIn = empSignedIn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
