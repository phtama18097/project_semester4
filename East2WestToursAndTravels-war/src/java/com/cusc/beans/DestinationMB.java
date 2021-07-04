/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Destinations;
import com.cusc.sessionbean.DestinationsFacadeLocal;
import com.cusc.sessionbean.TourTypesFacadeLocal;
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
@Named(value = "destinationMB")
@SessionScoped
public class DestinationMB implements Serializable {

    @EJB
    private TourTypesFacadeLocal tourTypesFacade;

    @EJB
    private TownsFacadeLocal townsFacade;

    @EJB
    private DestinationsFacadeLocal destinationsFacade;

    private Part file;
    private final String UPLOAD_DIRECTORY = "uploads" + File.separator + "imgDestinations";

    private Destinations destinations;
    private String notice = "";
    private int editID = 0;
    private int townID = 0;
    private int typeID = 0;

    public DestinationMB() {
        destinations = new Destinations();
    }

    public List<Destinations> showAll() {
        return destinationsFacade.findAll();
    }

    public void create() {
        try {
            Destinations d = new Destinations();
            d.setDestinationName(destinations.getDestinationName());
            d.setDescription(destinations.getDescription());
            d.setThumbnail(uploadFile());
            d.setTypeId(tourTypesFacade.find(typeID));
            d.setTownId(townsFacade.find(townID));

            destinationsFacade.create(d);
            resetForm();
            notice = "toastr.success(\"New destination has been added successfully!\");";
        } catch (Exception ex) {
            notice = "toastr.error(\"New destination has not added. Try again\");";
        }
    }

    public void delete(Destinations d) {
        try {
            destinationsFacade.remove(d);
            notice = "toastr.success(\"The destination has been deleted successfully!\");";
            deleteFile(d.getThumbnail());
        } catch (Exception ex) {
            notice = "toastr.error(\"The destination has a constraint. You cannot delete it.\");";
        }
    }

    public void update() {
        try {
            Destinations d = destinationsFacade.find(editID);
            d.setDestinationName(destinations.getDestinationName());
            d.setDescription(destinations.getDescription());
            d.setTypeId(tourTypesFacade.find(typeID));
            d.setTownId(townsFacade.find(townID));

            if (file != null) {
                deleteFile(d.getThumbnail());
                d.setThumbnail(uploadFile());
            }
            destinationsFacade.edit(d);
            resetForm();
            notice = "toastr.success(\"The destination has been updated successfully!\");";
        } catch (Exception ex) {
            notice = "toastr.error(\"The detination has not updated. Try again.\");";
        }
    }

    public void resetForm() {
        destinations.setDestinationName(null);
        destinations.setDescription(null);
        destinations.setThumbnail(null);
        destinations.setTypeId(null);
        destinations.setTownId(null);
        setEditID(0);
        setTownID(0);
        setTypeID(0);
        setFile(null);
    }

    private String uploadFile() {
        String fileName = "";
        if (file != null) {
            InputStream content = null;
            try {
                String type = file.getContentType();
                if (type.equals("image/jpeg") || type.equals("image/png") || type.equals("image/jpg")) {
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

    public Destinations getDestinations() {
        return destinations;
    }

    public void setDestinations(Destinations destinations) {
        this.destinations = destinations;
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

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

}
