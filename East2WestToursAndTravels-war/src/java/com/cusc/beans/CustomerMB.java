/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Customers;
import com.cusc.helps.CommonConstant;
import com.cusc.helps.ImageTools;
import com.cusc.helps.NotificationTools;
import com.cusc.helps.PasswordTools;
import com.cusc.sessionbean.CustomersFacadeLocal;
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
@Named(value = "customerMB")
@SessionScoped
public class CustomerMB implements Serializable {

    @EJB
    private CustomersFacadeLocal customersFacade;

    private Part file;
    private static final String UPLOAD_DIRECTORY = "imgCustomers";
    private static final String BEAN_OBJECT = "customer";
    private Part excelFile;

    private Customers customers;
    private String notice = "";
    private String message = "";
    private int editID = 0;
    private boolean gender = true;
    private boolean customerStatus = true;

    public CustomerMB() {
        customers = new Customers();
    }

    public List<Customers> showAll() {
        return customersFacade.findAll();
    }

    public void create() {
        try {
            Customers c = new Customers();
            boolean isInvalid = false;
            if (customers.getUsername().length() < 5 || customers.getUsername().length() > 50) {
                notice += NotificationTools.error("The length of the username must be between 5 and 50 characters.");
                isInvalid = true;
            }
            if (customersFacade.validateUsername(customers.getUsername())) {
                notice += NotificationTools.error(CommonConstant.EXISTED_USERNAME_NOTICE);
                isInvalid = true;
            }
            if (customers.getPassword().length() < 8) {
                notice += NotificationTools.error(CommonConstant.INVALID_PASSWORD);
                isInvalid = true;
            }
            if (customers.getFirstName().length() < 1 || customers.getFirstName().length() > 20) {
                notice += NotificationTools.error("The length of the firstname must be between 1 and 20 characters.");
                isInvalid = true;
            }
            if (customers.getLastName().length() < 1 || customers.getLastName().length() > 20) {
                notice += NotificationTools.error("The length of the lastname must be between 1 and 20 characters.");
                isInvalid = true;
            }
            if (customers.getBirthDate() == null) {
                notice += NotificationTools.error("Customer's birthdate must be entered.");
                isInvalid = true;
            }
            if (customers.getBirthDate() != null && !dobIsValid(customers.getBirthDate())) {
                notice += NotificationTools.error("Customer's age must be greater than 14.");
                isInvalid = true;
            }
            if (customers.getPhone().length() < 5 || customers.getPhone().length() > 20) {
                notice += NotificationTools.error("The length of the phone number must be between 5 and 20 characters.");
                isInvalid = true;
            }
            if (customers.getEmail().length() < 5 || customers.getEmail().length() > 60) {
                notice += NotificationTools.error("The length of the email must be between 5 and 60 characters.");
                isInvalid = true;
            }
            if (customersFacade.validateEmail(customers.getEmail())) {
                notice += NotificationTools.error(CommonConstant.EXISTED_EMAIL_NOTICE);
                isInvalid = true;
            }
            if (customers.getAddress().length() < 5 || customers.getAddress().length() > 120) {
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
            c.setUsername(customers.getUsername());
            c.setPassword(PasswordTools.encrypt(customers.getPassword()));
            c.setFirstName(customers.getFirstName());
            c.setLastName(customers.getLastName());
            c.setGender(gender);
            c.setBirthDate(customers.getBirthDate());
            c.setPhone(customers.getPhone());
            c.setEmail(customers.getEmail());
            c.setAddress(customers.getAddress());

            c.setPoint(0);
            if (customerStatus) {
                c.setStatus((short) 1);
            } else {
                c.setStatus((short) 0);
            }
            customersFacade.create(c);
            resetForm();
            notice = NotificationTools.createSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.createFail(BEAN_OBJECT);
        }
    }

    public void delete(Customers cus) {
        try {
            customersFacade.remove(cus);
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
            Customers c = customersFacade.find(editID);
            boolean isInvalid = false;
            if (customers.getFirstName().length() < 1 || customers.getFirstName().length() > 20) {
                notice += NotificationTools.error("The length of the firstname must be between 1 and 20 characters.");
                isInvalid = true;
            }
            if (customers.getLastName().length() < 1 || customers.getLastName().length() > 20) {
                notice += NotificationTools.error("The length of the lastname must be between 1 and 20 characters.");
                isInvalid = true;
            }
            if (customers.getBirthDate() == null) {
                notice += NotificationTools.error("Customer's birthdate must be entered.");
                isInvalid = true;
            }
            if (customers.getBirthDate() != null && !dobIsValid(customers.getBirthDate())) {
                notice += NotificationTools.error("Customer's age must be greater than 14.");
                isInvalid = true;
            }
            if (customers.getPhone().length() < 5 || customers.getPhone().length() > 20) {
                notice += NotificationTools.error("The length of the phone number must be between 5 and 20 characters.");
                isInvalid = true;
            }
            if (customers.getEmail().length() < 5 || customers.getEmail().length() > 60) {
                notice += NotificationTools.error("The length of the email must be between 5 and 60 characters.");
                isInvalid = true;
            }
            if (customersFacade.validateExistedEmail(customers.getEmail(), c.getEmail())) {
                notice += NotificationTools.error(CommonConstant.EXISTED_EMAIL_NOTICE);
                isInvalid = true;
            }
            if (customers.getAddress().length() < 5 || customers.getAddress().length() > 120) {
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
                message = NotificationTools.editModal(c.getCustomerId());
                return;
            }
            c.setFirstName(customers.getFirstName());
            c.setLastName(customers.getLastName());
            c.setGender(gender);
            c.setBirthDate(customers.getBirthDate());
            c.setPhone(customers.getPhone());
            c.setEmail(customers.getEmail());
            c.setAddress(customers.getAddress());

            if (customerStatus) {
                c.setStatus((short) 1);
            } else {
                c.setStatus((short) 0);
            }
            customersFacade.edit(c);
            resetForm();
            notice = NotificationTools.updateSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.updateFail(BEAN_OBJECT);
        }
    }

    public void resetForm() {
        customers.setUsername(null);
        customers.setPassword(null);
        customers.setFirstName(null);
        customers.setLastName(null);
        customers.setGender(null);
        customers.setBirthDate(null);
        customers.setPhone(null);
        customers.setEmail(null);
        customers.setAddress(null);
        customers.setAvatar(null);
        customers.setPoint(null);
        customers.setStatus(null);
        setEditID(0);
        setCustomerStatus(true);
        setGender(true);
        setFile(null);
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
                        Customers cus = new Customers();
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
                            }
                        }
                        cus.setPoint(0);
                        customersFacade.create(cus);

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

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
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

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public boolean isCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(boolean customerStatus) {
        this.customerStatus = customerStatus;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
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
