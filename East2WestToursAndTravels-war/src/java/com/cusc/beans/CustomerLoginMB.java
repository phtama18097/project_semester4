/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Customers;
import com.cusc.entities.Feedbacks;
import com.cusc.helps.CommonConstant;
import com.cusc.helps.ImageTools;
import com.cusc.helps.JavaMailUtil;
import com.cusc.helps.NotificationTools;
import com.cusc.helps.PasswordTools;
import com.cusc.sessionbean.CustomersFacadeLocal;
import com.cusc.sessionbean.FeedbackTypesFacadeLocal;
import com.cusc.sessionbean.FeedbacksFacadeLocal;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.servlet.http.Part;

/**
 *
 * @author Admin
 */
@Named(value = "customerLoginMB")
@SessionScoped
public class CustomerLoginMB implements Serializable {

    @EJB
    private FeedbacksFacadeLocal feedbacksFacade;

    @EJB
    private FeedbackTypesFacadeLocal feedbackTypesFacade;

    @EJB
    private CustomersFacadeLocal customersFacade;

    /**
     * Parameter for sign in, sign up, change password, update profile
     */
    private Customers cusSignedIn;
    private Customers cusSignedUp;
    private String username = "";
    private String password = "";
    private String notice = "";
    private String message = "";
    private boolean isLoggedIn = false;

    private Part file;
    private static final String UPLOAD_DIRECTORY = "imgCustomers";
    private static final String BEAN_OBJECT = "profile";
    private boolean gender = true;
    private String verifyPassword = "";
    private String newPassword = "";
    
    /**
     * Parameter for feedbacks
     */
    private int feedbackTypeId = 0;
    private String feedbackMessage = "";

    public CustomerLoginMB() {
        cusSignedUp = new Customers();
    }

    public String login() {
        if (username.equals("")) {
            setNotice(NotificationTools.error("You must enter your username."));
            return "customerLoginPage";
        } else if (password.equals("")) {
            setNotice(NotificationTools.error("You must enter your password."));
            return "customerLoginPage";
        } else {
            Customers isValidUser = customersFacade.login(username, PasswordTools.encrypt(password));
            if (isValidUser != null) {
                cusSignedIn = isValidUser;
                if (cusSignedIn.getStatus() == 0) {
                    notice = NotificationTools.info("Your account has been blocked. You may get in touch administrator to know information!");
                    setPassword("");
                    setUsername("");
                    return "customerLoginPage";
                } else {
                    setIsLoggedIn(true);
                    setPassword("");
                    setUsername("");
                    notice = NotificationTools.success("Congratulation! You signed in successfully.");
                    return "homepage";
                }
            } else {
                notice = NotificationTools.error("Username or password is incorrect.");
                setPassword("");
                return "customerLoginPage";
            }
        }
    }

    public String logout() throws IOException {
        setUsername("");
        setPassword("");
        setMessage("");
        setNewPassword("");
        setVerifyPassword("");
        setIsLoggedIn(false);
        setCusSignedIn(null);
        notice = NotificationTools.success("Signed out");
        return "homepage";
    }

    public String signUp() {
        if (!cusSignedUp.getPassword().equals(verifyPassword)) {
            setNotice(NotificationTools.error("Confirm password is incorrect."));
            return "register";
        } else {
            try {
                Customers c = new Customers();
                String result = ImageTools.uploadFile(file, UPLOAD_DIRECTORY);
                switch (result) {
                    case "":
                        notice = NotificationTools.error(CommonConstant.IMAGE_IS_NULL_NOTICE);
                        return "register";
                    case CommonConstant.FILE_IO:
                        notice = NotificationTools.error(CommonConstant.UPLOADING_FAIL_NOTICE);
                        return "register";
                    case CommonConstant.FILE_EXTENSION:
                        notice = NotificationTools.error(CommonConstant.INVALID_IMAGE_EXTENSION_NOTICE);
                        return "register";
                    case CommonConstant.FILE_SIZE:
                        notice = NotificationTools.error(CommonConstant.INVALID_FILE_SIZE_NOTICE);
                        return "register";
                    default:
                        c.setAvatar(result);
                        break;
                }
                c.setUsername(cusSignedUp.getUsername());
                c.setPassword(PasswordTools.encrypt(cusSignedUp.getPassword()));
                c.setFirstName(cusSignedUp.getFirstName());
                c.setLastName(cusSignedUp.getLastName());
                c.setGender(gender);
                c.setBirthDate(cusSignedUp.getBirthDate());
                c.setPhone(cusSignedUp.getPhone());
                c.setEmail(cusSignedUp.getEmail());
                c.setAddress(cusSignedUp.getAddress());
                c.setPoint(0);
                c.setStatus((short) 1);
                customersFacade.create(c);
                JavaMailUtil.sendMail(cusSignedUp.getEmail(), "Signed up successfully", JavaMailUtil.createSignedUpMessage(cusSignedUp.getLastName() + " " + cusSignedUp.getFirstName()));
                resetForm();
                setNotice(NotificationTools.success("Congratulation! Your account has registered successfully."));
                return "homepage";
                
            } catch (Exception ex) {
                setNotice(NotificationTools.error("Sorry! Your account has not registered. Try again!"));
                return "register";
            }
        }
    }
    
    public void updateProfile() {
        try {
            Customers c = customersFacade.find(cusSignedIn.getCustomerId());
            if (file != null) {
                ImageTools.deleteFile(c.getAvatar(), UPLOAD_DIRECTORY);
                String result = ImageTools.uploadFile(file, UPLOAD_DIRECTORY);
                switch (result) {
                    case "":
                        notice = NotificationTools.info(CommonConstant.IMAGE_IS_NULL_NOTICE);
                        return;
                    case CommonConstant.FILE_IO:
                        notice = NotificationTools.error(CommonConstant.UPLOADING_FAIL_NOTICE);
                        return;
                    case CommonConstant.FILE_EXTENSION:
                        notice = NotificationTools.error(CommonConstant.INVALID_IMAGE_EXTENSION_NOTICE);
                        return;
                    case CommonConstant.FILE_SIZE:
                        notice = NotificationTools.error(CommonConstant.INVALID_FILE_SIZE_NOTICE);
                        return;
                    default:
                        c.setAvatar(result);
                        break;
                }
            }
            c.setFirstName(cusSignedIn.getFirstName());
            c.setLastName(cusSignedIn.getLastName());
            c.setGender(gender);
            c.setBirthDate(cusSignedIn.getBirthDate());
            c.setPhone(cusSignedIn.getPhone());
            c.setEmail(cusSignedIn.getEmail());
            c.setAddress(cusSignedIn.getAddress());

            customersFacade.edit(c);
            setCusSignedIn(c);
            notice = NotificationTools.updateSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.updateFail(BEAN_OBJECT);
        }
    }
    
    public void changePassword() {
        if (!PasswordTools.encrypt(password).equals(cusSignedIn.getPassword())) {
            notice = NotificationTools.error("Your current password is inccorrect.");
        } else if (newPassword.length() < 8) {
            notice = NotificationTools.error("Your new password must greater than 8 characters.");
        } else if (!newPassword.equals(verifyPassword)) {
            notice = NotificationTools.error("Your verified password is not matched.");
        } else {
            try {
                Customers c = customersFacade.find(cusSignedIn.getCustomerId());
                c.setPassword(PasswordTools.encrypt(newPassword));
                customersFacade.edit(c);
                setPassword("");
                setVerifyPassword("");
                setNewPassword("");
                setCusSignedIn(c);
                notice = NotificationTools.updateSuccess("password");
            } catch (Exception ex) {
                setPassword("");
                setVerifyPassword("");
                setNewPassword("");
                notice = NotificationTools.updateFail("password");
            }
        }
    }

    public void resetForm() {
        setUsername("");
        cusSignedUp.setUsername(null);
        cusSignedUp.setPassword(null);
        cusSignedUp.setFirstName(null);
        cusSignedUp.setLastName(null);
        cusSignedUp.setGender(null);
        cusSignedUp.setBirthDate(null);
        cusSignedUp.setPhone(null);
        cusSignedUp.setEmail(null);
        cusSignedUp.setAddress(null);
        cusSignedUp.setAvatar(null);
        cusSignedUp.setPoint(null);
        cusSignedUp.setStatus(null);
        setNotice("");
        setGender(true);
        setFile(null);
    }

    public void sendFeedback(){
        if(isLoggedIn && cusSignedIn != null){
            if(feedbackTypeId == 0){
                notice = NotificationTools.error("You must select a subject.");
            } else if(feedbackMessage.length() < 10){
                notice = NotificationTools.error("Message must greater than 10 characters.");
            } else {
                Feedbacks feedback = new Feedbacks();
                feedback.setCustomerId(customersFacade.find(cusSignedIn.getCustomerId()));
                feedback.setTypeId(feedbackTypesFacade.find(feedbackTypeId));
                feedback.setMessage(feedbackMessage);
                feedbacksFacade.create(feedback);
                setFeedbackTypeId(0);
                setFeedbackMessage("");
                notice = NotificationTools.success("Feedback has been sent successfully.");
            }
        } else {
            notice = NotificationTools.error("You must sign in to send feedback.");
        }
    }
    public Customers getCusSignedIn() {
        return cusSignedIn;
    }

    public void setCusSignedIn(Customers cusSignedIn) {
        this.cusSignedIn = cusSignedIn;
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

    public Customers getCusSignedUp() {
        return cusSignedUp;
    }

    public void setCusSignedUp(Customers cusSignedUp) {
        this.cusSignedUp = cusSignedUp;
    }

    public int getFeedbackTypeId() {
        return feedbackTypeId;
    }

    public void setFeedbackTypeId(int feedbackTypeId) {
        this.feedbackTypeId = feedbackTypeId;
    }

    public String getFeedbackMessage() {
        return feedbackMessage;
    }

    public void setFeedbackMessage(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }

}
