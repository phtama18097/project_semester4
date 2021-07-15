/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Customers;
import com.cusc.helps.PasswordTools;
import com.cusc.sessionbean.CustomersFacadeLocal;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

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
    private final String UPLOAD_DIRECTORY = "uploads" + File.separator + "imgCustomers";

    private Customers customers;
    private String notice = "";
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
            c.setUsername(customers.getUsername());
            c.setPassword(PasswordTools.encrypt(customers.getPassword()));
            c.setFirstName(customers.getFirstName());
            c.setLastName(customers.getLastName());
            c.setGender(gender);
            c.setBirthDate(customers.getBirthDate());
            c.setPhone(customers.getPhone());
            c.setEmail(customers.getEmail());
            c.setAddress(customers.getAddress());
            c.setAvatar(uploadFile());
            c.setPoint(0);
            if (customerStatus) {
                c.setStatus((short) 1);
            } else {
                c.setStatus((short) 0);
            }
            customersFacade.create(c);
            resetForm();
            notice = "toastr.success(\"New customer has been added successfully!\");";
        } catch (Exception ex) {
            notice = "toastr.error(\"New customer has not added. Try again\");";
        }
    }

    public void delete(Customers cus) {
        try{
            customersFacade.remove(cus);
            notice = "toastr.success(\"The customer has been deleted successfully!\");";
            deleteFile(cus.getAvatar());
        }catch(Exception ex){
            notice = "toastr.error(\"The customer has a constraint. You cannot delete it.\");";
        }
    }

    public void update() {
        try {
            Customers c = customersFacade.find(editID);
            c.setFirstName(customers.getFirstName());
            c.setLastName(customers.getLastName());
            c.setGender(gender);
            c.setBirthDate(customers.getBirthDate());
            c.setPhone(customers.getPhone());
            c.setEmail(customers.getEmail());
            c.setAddress(customers.getAddress());
            if(file != null){
                deleteFile(c.getAvatar());
                c.setAvatar(uploadFile());
            }
            if (customerStatus) {
                c.setStatus((short) 1);
            } else {
                c.setStatus((short) 0);
            }
            customersFacade.edit(c);
            resetForm();
            notice = "toastr.success(\"The customer has been updated successfully!\");";
        } catch (Exception ex) {
            notice = "toastr.error(\"The customer has not updated. Try again\");";
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

    private String uploadFile() {
        String fileName = "";
        if (file != null) {
            InputStream content = null;
            try {
                String type = file.getContentType();
                if (type.equals("image/jpeg") || type.equals("image/png") || type.equals("image/jpg")) {
                    if (file.getSize() > 5242880) {
                        //notice = "toastr.error(\"The thumbnail must less than 5MB. Try again\");";
                    }
                    Date date = new Date();
                    fileName = file.getSubmittedFileName().substring(0, file.getSubmittedFileName().lastIndexOf("."));
                    String extension = file.getSubmittedFileName().substring(file.getSubmittedFileName().lastIndexOf("."), file.getSubmittedFileName().length());

                    fileName = fileName + date.getDate() + date.getMonth() + date.getYear() + date.getHours() + date.getMinutes() + date.getSeconds() + date.getTimezoneOffset() + extension;

                    content = file.getInputStream();

                    FacesContext context = FacesContext.getCurrentInstance();
                    ExternalContext ec = context.getExternalContext();
                    HttpServletRequest request = (HttpServletRequest) ec.getRequest();

                    String applicationPath = request.getServletContext().getRealPath("");

                    String uploadFilePath = applicationPath + File.separator + UPLOAD_DIRECTORY;

                    File fileSaveDir = new File(uploadFilePath);
                    if (!fileSaveDir.exists()) {
                        fileSaveDir.mkdirs();
                    }
                    OutputStream outputStream = null;
                    try {
                        File outputFilePath = new File(uploadFilePath + File.separator + fileName);
                        content = file.getInputStream();
                        outputStream = new FileOutputStream(outputFilePath);
                        int read = 0;
                        final byte[] bytes = new byte[1024];
                        while ((read = content.read(bytes)) != -1) {
                            outputStream.write(bytes, 0, read);
                        }
                    } catch (IOException e) {
                        e.toString();
                    } finally {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        if (content != null) {
                            content.close();
                        }
                    }
                }
            } catch (IOException ex) {
            } finally {
                try {
                    content.close();
                } catch (IOException ex) {
                }
            }
        }
        return fileName;
    }

    private void deleteFile(String fileName) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext ec = context.getExternalContext();
            HttpServletRequest request = (HttpServletRequest) ec.getRequest();

            String applicationPath = request.getServletContext().getRealPath("");

            String uploadFilePath = applicationPath + File.separator + UPLOAD_DIRECTORY;

            File fl = new File(uploadFilePath + File.separator + fileName);
            fl.delete();
        } catch (Exception ex) {
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

}
