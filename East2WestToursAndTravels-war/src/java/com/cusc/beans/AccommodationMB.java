/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Accommodations;
import com.cusc.sessionbean.AccommodationsFacadeLocal;
import com.cusc.sessionbean.TownsFacadeLocal;
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
@Named(value = "accommodationMB")
@SessionScoped
public class AccommodationMB implements Serializable {

    @EJB
    private AccommodationsFacadeLocal accommodationsFacade;

    @EJB
    private TownsFacadeLocal townsFacade;


    private Part file;
    private final String UPLOAD_DIRECTORY = "uploads" + File.separator + "imgAccommodations";

    private Accommodations accommodations;
    private String notice = "";
    private int editID = 0;
    private int townID = 0;

    public AccommodationMB() {
        accommodations = new Accommodations();
    }

    public List<Accommodations> showAll() {
        return accommodationsFacade.findAll();
    }

    public void create() {
        try {
            Accommodations r = new Accommodations();
            r.setAccommodationName(accommodations.getAccommodationName());
            r.setDescription(accommodations.getDescription());
            r.setMinPrice(accommodations.getMinPrice());
            r.setMaxPrice(accommodations.getMaxPrice());
            r.setLocation(accommodations.getLocation());
            r.setTownId(townsFacade.find(townID));
            r.setThumbnail(uploadFile());

            accommodationsFacade.create(r);
            resetForm();
            notice = "toastr.success(\"New accommodation has been added successfully!\");";
        } catch (Exception ex) {
            notice = "toastr.error(\"New accommodation has not added. Try again\");";
        }
    }
    
    public void delete(Accommodations r){
        try{
            accommodationsFacade.remove(r);
            notice = "toastr.success(\"The accommodation has been deleted successfully!\");";
            deleteFile(r.getThumbnail());
        }catch(Exception ex){
            notice = "toastr.error(\"The accommodation has a constraint. You cannot delete it.\");";
        }
    }

    public void update() {
        try{
            Accommodations r = accommodationsFacade.find(editID);
            r.setAccommodationName(accommodations.getAccommodationName());
            r.setDescription(accommodations.getDescription());
            r.setMinPrice(accommodations.getMinPrice());
            r.setMaxPrice(accommodations.getMaxPrice());
            r.setLocation(accommodations.getLocation());
            r.setTownId(townsFacade.find(townID));

            if(file != null){
                deleteFile(r.getThumbnail());
                r.setThumbnail(uploadFile());
            }
            accommodationsFacade.edit(r);
            resetForm();
            notice = "toastr.success(\"The accommodation has been updated successfully!\");";
        }catch(Exception ex){
            notice = "toastr.error(\"The accommodation has not updated. Try again.\");";
        }
    }

    public void resetForm() {
        accommodations.setAccommodationName(null);
        accommodations.setDescription(null);
        accommodations.setLocation(null);
        accommodations.setMinPrice(null);
        accommodations.setMaxPrice(null);
        accommodations.setThumbnail(null);
        accommodations.setTownId(null);
        setEditID(0);
        setTownID(0);
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

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Accommodations getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(Accommodations accommodations) {
        this.accommodations = accommodations;
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

    public int getTownID() {
        return townID;
    }

    public void setTownID(int townID) {
        this.townID = townID;
    }
    
}
