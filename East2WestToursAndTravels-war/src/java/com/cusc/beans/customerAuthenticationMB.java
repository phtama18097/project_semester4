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
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author Admin
 */
@Named(value = "customerAuthenticationMB")
@SessionScoped
public class customerAuthenticationMB implements Serializable {

    @EJB
    private CustomersFacadeLocal customersFacade;
    private Part file;

    private final String UPLOAD_DIRECTORY = "uploads" + File.separator + "imgCustomers";
    private String username="";
    private String password="";
    private Customers customers;
    private String cfpassword="";
    private boolean gender = true;
    private String notice = "";
    private String error = "";

    /**
     * Creates a new instance of customerAuthenticationMB
     */
    public customerAuthenticationMB() {
        customers = new Customers();
    }

    public String registerCustomer() {
        
//        if (password == cfpassword) {
            try  {
                System.out.println("vo ts try");
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
                c.setStatus((short) 1);
                customersFacade.create(c);
                notice = "toastr.success(\"Sign up successfully!\");";            
                return "succes";
            } catch (Exception ex) {       
                notice = "toastr.error(\" Please,try again\");";
                return "test";
            }
        
//        } else {
//            
//            error = "Confirm Password doesn't match !";
//            return "test";
//        }
        
    }
    public String login() {

        if (customersFacade.checkLoginCustomer(username, password)) {
//             customers = customersFacade.find(username);
            return "homepage";
        }
        else {
            error="Login Fail";
            return "test";
        }

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

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
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

    public Customers getCustomers() {
        return customers;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public String getCfpassword() {
        return cfpassword;
    }

    public void setCfpassword(String cfpassword) {
        this.cfpassword = cfpassword;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
