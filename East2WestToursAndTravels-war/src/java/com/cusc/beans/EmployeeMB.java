/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Employees;
import com.cusc.helps.CommonConstant;
import com.cusc.helps.ImageTools;
import com.cusc.helps.NotificationTools;
import com.cusc.helps.PasswordTools;
import com.cusc.sessionbean.EmployeesFacadeLocal;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.Part;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;

/**
 *
 * @author Admin
 */
@Named(value = "employeeMB")
@SessionScoped
public class EmployeeMB implements Serializable {

    @EJB
    private EmployeesFacadeLocal employeesFacade;

    private Part file;
    private static final String UPLOAD_DIRECTORY = "imgEmployees";
    private static final String BEAN_OBJECT = "employee";
    private Part excelFile;

    private Employees employees;
    private String notice = "";
    private String message = "";
    private int editID = 0;
    private boolean gender = true;
    private boolean employeeStatus = true;
    private boolean isAdmin = false;

    public EmployeeMB() {
        employees = new Employees();
    }

    public void create() {
        try {
            Employees c = new Employees();
            boolean isInvalid = false;
            if (employees.getUsername().length() < 5 || employees.getUsername().length() > 50) {
                notice += NotificationTools.error("The length of the username must be between 5 and 50 characters.");
                isInvalid = true;
            }
            if (employeesFacade.validateUsername(employees.getUsername())) {
                notice += NotificationTools.error(CommonConstant.EXISTED_USERNAME_NOTICE);
                isInvalid = true;
            }
            if (employees.getPassword().length() < 8) {
                notice += NotificationTools.error(CommonConstant.INVALID_PASSWORD);
                isInvalid = true;
            }
            if (employees.getFirstName().length() < 1 || employees.getFirstName().length() > 20) {
                notice += NotificationTools.error("The length of the firstname must be between 1 and 20 characters.");
                isInvalid = true;
            }
            if (employees.getLastName().length() < 1 || employees.getLastName().length() > 20) {
                notice += NotificationTools.error("The length of the lastname must be between 1 and 20 characters.");
                isInvalid = true;
            }
            if (employees.getBirthDate() == null) {
                notice += NotificationTools.error("Customer's birthdate must be entered.");
                isInvalid = true;              
            }
            if (employees.getBirthDate() != null && !dobIsValid(employees.getBirthDate())) {
                    notice += NotificationTools.error("Customer's age must be greater than 14.");
                    isInvalid = true;
                }
            if (employees.getPhone().length() < 5 || employees.getPhone().length() > 20) {
                notice += NotificationTools.error("The length of the phone number must be between 5 and 20 characters.");
                isInvalid = true;
            }
            if (employees.getEmail().length() < 5 || employees.getEmail().length() > 60) {
                notice += NotificationTools.error("The length of the email must be between 5 and 60 characters.");
                isInvalid = true;
            }
            if (employeesFacade.validateEmail(employees.getEmail())) {
                notice += NotificationTools.error(CommonConstant.EXISTED_EMAIL_NOTICE);
                isInvalid = true;
            }
            if (employees.getAddress().length() < 5 || employees.getAddress().length() > 120) {
                notice += NotificationTools.error("The length of the address must be between 5 and 120 characters.");
                isInvalid = true;
            }
            String result = ImageTools.uploadFile(file, UPLOAD_DIRECTORY);
            switch (result) {
                case "":
                    notice += NotificationTools.error(CommonConstant.IMAGE_IS_NULL_NOTICE);
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
                    break;
            }
            if (isInvalid) {
                message = NotificationTools.openModal();
                return;
            }
            c.setUsername(employees.getUsername());
            c.setPassword(PasswordTools.encrypt(employees.getPassword()));
            c.setFirstName(employees.getFirstName());
            c.setLastName(employees.getLastName());
            c.setGender(gender);
            c.setBirthDate(employees.getBirthDate());
            c.setPhone(employees.getPhone());
            c.setEmail(employees.getEmail());
            c.setAddress(employees.getAddress());
            c.setPoint(0);
            c.setIsAdmin(isAdmin);
            if (employeeStatus) {
                c.setStatus((short) 1);
            } else {
                c.setStatus((short) 0);
            }
            employeesFacade.create(c);
            resetForm();
            notice = NotificationTools.createSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.createFail(BEAN_OBJECT);
        }
    }

    public void delete(Employees cus) {
        try {
            employeesFacade.remove(cus);
            if (ImageTools.deleteFile(cus.getAvatar(), UPLOAD_DIRECTORY)) {
                notice = NotificationTools.deleteSuccess(BEAN_OBJECT);
            } else {
                notice = NotificationTools.deleteFail(BEAN_OBJECT);
            }
        } catch (Exception ex) {
            notice = NotificationTools.deleteFail(BEAN_OBJECT);
        }
    }

    public void update() {
        try {
            Employees c = employeesFacade.find(editID);
            boolean isInvalid = false;
            if (employees.getFirstName().length() < 1 || employees.getFirstName().length() > 20) {
                notice += NotificationTools.error("The length of the firstname must be between 1 and 20 characters.");
                isInvalid = true;
            }
            if (employees.getLastName().length() < 1 || employees.getLastName().length() > 20) {
                notice += NotificationTools.error("The length of the lastname must be between 1 and 20 characters.");
                isInvalid = true;
            }
            if (employees.getBirthDate() == null) {
                notice += NotificationTools.error("Customer's birthdate must be entered.");
                isInvalid = true;              
            }
            if (employees.getBirthDate() != null && !dobIsValid(employees.getBirthDate())) {
                    notice += NotificationTools.error("Customer's age must be greater than 14.");
                    isInvalid = true;
                }
            if (employees.getPhone().length() < 5 || employees.getPhone().length() > 20) {
                notice += NotificationTools.error("The length of the phone number must be between 5 and 20 characters.");
                isInvalid = true;
            }
            if (employees.getEmail().length() < 5 || employees.getEmail().length() > 60) {
                notice += NotificationTools.error("The length of the email must be between 5 and 60 characters.");
                isInvalid = true;
            }
            if (employeesFacade.validateExistedEmail(employees.getEmail(), c.getEmail())) {
                notice += NotificationTools.error(CommonConstant.EXISTED_EMAIL_NOTICE);
                isInvalid = true;
            }
            if (employees.getAddress().length() < 5 || employees.getAddress().length() > 120) {
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
                        ImageTools.deleteFile(c.getAvatar(), UPLOAD_DIRECTORY);
                        break;
                }
            }
            if (isInvalid) {
                message = NotificationTools.editModal(c.getEmployeeId());
                return;
            }
            c.setFirstName(employees.getFirstName());
            c.setLastName(employees.getLastName());
            c.setGender(gender);
            c.setBirthDate(employees.getBirthDate());
            c.setPhone(employees.getPhone());
            c.setEmail(employees.getEmail());
            c.setAddress(employees.getAddress());
            if (employeeStatus) {
                c.setStatus((short) 1);
            } else {
                c.setStatus((short) 0);
            }
            c.setIsAdmin(isAdmin);
            employeesFacade.edit(c);
            resetForm();
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

    public void resetForm() {
        employees.setUsername(null);
        employees.setPassword(null);
        employees.setFirstName(null);
        employees.setLastName(null);
        employees.setGender(null);
        employees.setBirthDate(null);
        employees.setPhone(null);
        employees.setEmail(null);
        employees.setAddress(null);
        employees.setAvatar(null);
        employees.setPoint(null);
        employees.setStatus(null);
        setEditID(0);
        setEmployeeStatus(true);
        setGender(true);
        setIsAdmin(false);
        setFile(null);
    }

    public void importData() {
        if (excelFile != null) {
            String type = excelFile.getContentType();
            if (type.equals("application/vnd.ms-excel")) {
                try {
                    InputStream input = excelFile.getInputStream();
                    POIFSFileSystem fs = new POIFSFileSystem(input);
                    HSSFWorkbook wb = new HSSFWorkbook(fs);
                    HSSFSheet sheet = wb.getSheetAt(0);
                    Iterator rows = sheet.rowIterator();
                    rows.next();
                    while (rows.hasNext()) {
                        Employees cus = new Employees();
                        HSSFRow row = (HSSFRow) rows.next();

                        Iterator cells = row.cellIterator();

                        while (cells.hasNext()) {
                            HSSFCell cell = (HSSFCell) cells.next();
                            int columnIndex = cell.getColumnIndex();

                            switch (columnIndex) {
                                case 0:
                                    cus.setUsername(cell.getStringCellValue());
                                    break;
                                case 1:
                                    cus.setPassword(PasswordTools.encrypt(cell.getStringCellValue()));
                                    break;
                                case 2:
                                    cus.setFirstName(cell.getStringCellValue());
                                    break;
                                case 3:
                                    cus.setLastName(cell.getStringCellValue());
                                    break;
                                case 4:
                                    cus.setGender(cell.getBooleanCellValue());
                                    break;
                                case 5:
                                    cus.setBirthDate(cell.getDateCellValue());
                                    break;
                                case 6:
                                    cus.setEmail(cell.getStringCellValue());
                                    break;
                                case 7:
                                    if (cell.getCellType() == CellType.NUMERIC) {
                                        cus.setPhone(cell.getNumericCellValue() + "");
                                    } else {
                                        cus.setPhone(cell.getStringCellValue());
                                    }
                                    break;
                                case 8:
                                    cus.setAddress(cell.getStringCellValue());
                                    break;
                                case 9:
                                    cus.setStatus((short) cell.getNumericCellValue());
                                    break;
                                case 10:
                                    cus.setIsAdmin(cell.getBooleanCellValue());
                                    break;
                            }
                        }
                        cus.setPoint(0);
                        employeesFacade.create(cus);

                    }
                    notice = NotificationTools.success("The file has been imported successfully.");
                } catch (IOException e) {
                    notice = NotificationTools.error("The file has not imported. Try again.");
                } catch (Exception e) {
                    notice = NotificationTools.error("Data of the file is invalid. Try again!");
                }
            } else {
                notice = NotificationTools.error("The extension of the file is invalid. Extentions: .xls");
            }
        } else {
            notice = NotificationTools.error("Please select a excel file to imported to database.");
        }

    }

    public List<Employees> showAll() {
        return employeesFacade.findAll();
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public int getEditID() {
        return editID;
    }

    public void setEditID(int editID) {
        this.editID = editID;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public boolean isEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(boolean employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Part getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(Part excelFile) {
        this.excelFile = excelFile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
