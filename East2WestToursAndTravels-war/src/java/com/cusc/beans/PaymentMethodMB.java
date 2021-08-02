/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.PaymentMethods;
import com.cusc.helps.NotificationTools;
import com.cusc.sessionbean.PaymentMethodsFacadeLocal;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.Part;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author Admin
 */
@Named(value = "paymentMethodMB")
@SessionScoped
public class PaymentMethodMB implements Serializable {

    @EJB
    private PaymentMethodsFacadeLocal paymentMethodsFacade;

    private PaymentMethods payment;
    private String notice = "";
    private String message = "";
    private int editID = 0;

    private Part excelFile;

    public PaymentMethodMB() {
        payment = new PaymentMethods();
    }

    public List<PaymentMethods> showAll() {
        return paymentMethodsFacade.findAll();
    }

    public void create() {
        try {
            PaymentMethods pm = new PaymentMethods();
            boolean isInvalid = false;
            if (payment.getMethodName().length() < 2 || payment.getMethodName().length() > 50) {
                notice += NotificationTools.error("The length of the method name must be between 2 and 50 characters.");
                isInvalid = true;
            }
            if (isInvalid) {
                message = NotificationTools.openModal();
                return;
            }
            pm.setMethodName(payment.getMethodName());
            pm.setStatus(payment.getStatus());
            paymentMethodsFacade.create(pm);
            resetForm();
            notice = "toastr.success(\"New payment method has been added successfully!\");";
        } catch (Exception ex) {
            notice = "toastr.error(\"New payment method has not added. Try again\");";
        }
    }

    public void delete(PaymentMethods pm) {
        try {
            paymentMethodsFacade.remove(pm);
            notice = "toastr.success(\"The payment method has been deleted successfully!\");";
        } catch (Exception ex) {
            notice = "toastr.error(\"The payment method has a constraint. You cannot delete it.\");";
        }
    }

    public void update() {
        try {
            PaymentMethods pm = paymentMethodsFacade.find(editID);
            boolean isInvalid = false;
            if (payment.getMethodName().length() < 2 || payment.getMethodName().length() > 50) {
                notice += NotificationTools.error("The length of the method name must be between 2 and 50 characters.");
                isInvalid = true;
            }
            if (isInvalid) {
                message = NotificationTools.editModal(pm.getMethodId());
                return;
            }
            pm.setMethodName(payment.getMethodName());
            pm.setStatus(payment.getStatus());
            paymentMethodsFacade.edit(pm);
            resetForm();
            notice = "toastr.success(\"The payment method has been updated successfully!\");";
        } catch (Exception ex) {
            notice = "toastr.error(\"The payment method has not updated. Try again.\");";
        }
    }

    public void resetForm() {
        payment.setMethodName(null);
        payment.setStatus(null);
        setExcelFile(null);
        editID = 0;
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
                        PaymentMethods pmt = new PaymentMethods();
                        HSSFRow row = (HSSFRow) rows.next();
                        
                        Iterator cells = row.cellIterator();

                        while (cells.hasNext()) {
                            HSSFCell cell = (HSSFCell) cells.next();
                            int columnIndex = cell.getColumnIndex();

                            switch (columnIndex) {
                                case 0:
                                    pmt.setMethodName(cell.getStringCellValue());
                                    break;
                                case 1:
                                    pmt.setStatus(cell.getBooleanCellValue());
                                    break;
                            }
                        }
                        paymentMethodsFacade.create(pmt);

                    }
                    notice = NotificationTools.success("The file has been imported successfully.");
                } catch (IOException e) {
                    notice = NotificationTools.error("The file has not imported. Try again.");
                } catch (Exception e){
                    notice = NotificationTools.error("Data of the file is invalid. Try again!");
                }
            }else{
                notice = NotificationTools.error("The extension of the file is invalid. Extentions: .xls");
            }
        } else {
            notice = NotificationTools.error("Please select a excel file to imported to database.");
        }

    }

    public PaymentMethods getPayment() {
        return payment;
    }

    public void setPayment(PaymentMethods payment) {
        this.payment = payment;
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
