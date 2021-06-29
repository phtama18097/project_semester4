/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Restaurants;
import com.cusc.sessionbean.RestaurantsFacadeLocal;
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
@Named(value = "restaurantMB")
@SessionScoped
public class RestaurantMB implements Serializable {

    @EJB
    private TownsFacadeLocal townsFacade;

    @EJB
    private RestaurantsFacadeLocal restaurantsFacade;

    private Part file;
    private final String UPLOAD_DIRECTORY = "uploads" + File.separator + "imgRestaurants";

    private Restaurants restaurants;
    private String notice = "";
    private int editID = 0;
    private int townID = 0;

    public RestaurantMB() {
        restaurants = new Restaurants();
    }

    public List<Restaurants> showAll() {
        return restaurantsFacade.findAll();
    }

    public void create() {
        try {
            Restaurants r = new Restaurants();
            r.setRestaurantName(restaurants.getRestaurantName());
            r.setDescription(restaurants.getDescription());
            r.setMinPrice(restaurants.getMinPrice());
            r.setMaxPrice(restaurants.getMaxPrice());
            r.setLocation(restaurants.getLocation());
            r.setTownId(townsFacade.find(townID));
            r.setThumbnail(uploadFile());

            restaurantsFacade.create(r);
            resetForm();
            notice = "toastr.success(\"New restaurant has been added successfully!\");";
        } catch (Exception ex) {
            notice = "toastr.error(\"New restaurant has not added. Try again\");";
        }
    }
    
    public void delete(Restaurants r){
        try{
            restaurantsFacade.remove(r);
            notice = "toastr.success(\"The restaurant has been deleted successfully!\");";
            deleteFile(r.getThumbnail());
        }catch(Exception ex){
            notice = "toastr.error(\"The restaurant has a constraint. You cannot delete it.\");";
        }
    }

    public void update() {
        try{
            Restaurants r = restaurantsFacade.find(editID);
            r.setRestaurantName(restaurants.getRestaurantName());
            r.setDescription(restaurants.getDescription());
            r.setMinPrice(restaurants.getMinPrice());
            r.setMaxPrice(restaurants.getMaxPrice());
            r.setLocation(restaurants.getLocation());
            r.setTownId(townsFacade.find(townID));

            if(file != null){
                deleteFile(r.getThumbnail());
                r.setThumbnail(uploadFile());
            }
            restaurantsFacade.edit(r);
            resetForm();
            notice = "toastr.success(\"The restaurant has been updated successfully!\");";
        }catch(Exception ex){
            notice = "toastr.error(\"The restaurant has not updated. Try again.\");";
        }
    }

    public void resetForm() {
        restaurants.setRestaurantName(null);
        restaurants.setDescription(null);
        restaurants.setLocation(null);
        restaurants.setMinPrice(null);
        restaurants.setMaxPrice(null);
        restaurants.setThumbnail(null);
        restaurants.setTownId(null);
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

    public Restaurants getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Restaurants restaurants) {
        this.restaurants = restaurants;
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

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

}
