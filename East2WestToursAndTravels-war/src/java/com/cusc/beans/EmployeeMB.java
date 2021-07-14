/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Employees;
import com.cusc.sessionbean.EmployeesFacadeLocal;
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
@Named(value = "employeeMB")
@SessionScoped
public class EmployeeMB implements Serializable {

    @EJB
    private EmployeesFacadeLocal employeesFacade;

    private Part file;
    private final String UPLOAD_DIRECTORY = "uploads" + File.separator + "imgEmployees";

    private Employees employees;
    private String notice = "";
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
            c.setUsername(employees.getUsername());
            c.setPassword(employees.getPassword());
            c.setFirstName(employees.getFirstName());
            c.setLastName(employees.getLastName());
            c.setGender(gender);
            c.setBirthDate(employees.getBirthDate());
            c.setPhone(employees.getPhone());
            c.setEmail(employees.getEmail());
            c.setAddress(employees.getAddress());
            c.setAvatar(uploadFile());
            c.setPoint(0);
            c.setIsAdmin(isAdmin);
            if (employeeStatus) {
                c.setStatus((short) 1);
            } else {
                c.setStatus((short) 0);
            }
            employeesFacade.create(c);
            resetForm();
            notice = "toastr.success(\"New customer has been added successfully!\");";
        } catch (Exception ex) {
            notice = "toastr.error(\"New customer has not added. Try again\");";
        }
    }

    public void delete(Employees cus) {
        try{
            employeesFacade.remove(cus);
            notice = "toastr.success(\"The customer has been deleted successfully!\");";
            deleteFile(cus.getAvatar());
        }catch(Exception ex){
            notice = "toastr.error(\"The customer has a constraint. You cannot delete it.\");";
        }
    }

    public void update() {
        try {
            Employees c = employeesFacade.find(editID);
            c.setFirstName(employees.getFirstName());
            c.setLastName(employees.getLastName());
            c.setGender(gender);
            c.setBirthDate(employees.getBirthDate());
            c.setPhone(employees.getPhone());
            c.setEmail(employees.getEmail());
            c.setAddress(employees.getAddress());
            if(file != null){
                deleteFile(c.getAvatar());
                c.setAvatar(uploadFile());
            }
            if (employeeStatus) {
                c.setStatus((short) 1);
            } else {
                c.setStatus((short) 0);
            }
            c.setIsAdmin(isAdmin);
            employeesFacade.edit(c);
            resetForm();
            notice = "toastr.success(\"The customer has been updated successfully!\");";
        } catch (Exception ex) {
            notice = "toastr.error(\"The customer has not updated. Try again\");";
        }
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
    
}
