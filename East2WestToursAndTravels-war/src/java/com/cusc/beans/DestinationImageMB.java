/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.DestinationImages;
import com.cusc.entities.Destinations;
import com.cusc.sessionbean.DestinationImagesFacadeLocal;
import com.cusc.sessionbean.DestinationsFacadeLocal;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author Admin
 */
@Named(value = "destinationImageMB")
@SessionScoped
public class DestinationImageMB implements Serializable {

    @EJB
    private DestinationsFacadeLocal destinationsFacade;

    @EJB
    private DestinationImagesFacadeLocal destinationImagesFacade;

    private Part file;
    private final String UPLOAD_DIRECTORY = "uploads" + File.separator + "imgDestinations";

    private List<DestinationImages> imageList;
    private Destinations destination;
    private String notice = "";

    public DestinationImageMB() {
    }

    public String editImages(int desID) {
        imageList = null;
        destination = null;
        imageList = destinationImagesFacade.findImagesByDestination(desID);
        destination = destinationsFacade.find(desID);
        return "manageDestinationImages";
    }
    
    public void delete(DestinationImages image){
        try {
            destinationImagesFacade.remove(image);
            notice = "toastr.success(\"The image has been deleted successfully!\");";
            deleteFile(image.getFileName());
        } catch (Exception ex) {
            notice = "toastr.error(\"The image has a constraint. You cannot delete it.\");";
        }
        imageList = null;
        imageList = destinationImagesFacade.findImagesByDestination(destination.getDestinationId());
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

    public static Collection<Part> getAllParts(Part part) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getParts().stream().filter(p -> part.getName().equals(p.getName())).collect(Collectors.toList());
    }

    public String create() throws ServletException, IOException {
        if (file != null) {
            for (Part part : getAllParts(file)) {
                String type = part.getContentType();
                if (type.equals("image/jpeg") || type.equals("image/png") || type.equals("image/jpg")) {
                    if (part.getSize() > 5242880) {
                        notice = "toastr.error(\"File size must be less than 5MB.\");";
                        imageList = null;
                        imageList = destinationImagesFacade.findImagesByDestination(destination.getDestinationId());
                        return "manageDestinationImages";
                    }
                    Date date = new Date();
                    String fileName = part.getSubmittedFileName().substring(0, part.getSubmittedFileName().lastIndexOf("."));
                    String extension = part.getSubmittedFileName().substring(part.getSubmittedFileName().lastIndexOf("."), part.getSubmittedFileName().length());

                    fileName = fileName + date.getDate() + date.getMonth() + date.getYear() + date.getHours() + date.getMinutes() + date.getSeconds() + date.getTimezoneOffset() + extension;
                    InputStream fileContent = part.getInputStream();

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
                        fileContent = part.getInputStream();
                        outputStream = new FileOutputStream(outputFilePath);
                        int read = 0;
                        final byte[] bytes = new byte[1024];
                        while ((read = fileContent.read(bytes)) != -1) {
                            outputStream.write(bytes, 0, read);
                        }
                    } catch (IOException e) {
                        e.toString();
                    } finally {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        if (fileContent != null) {
                            fileContent.close();
                        }
                    }
                    DestinationImages img = new DestinationImages();
                    img.setDestinationId(destination);
                    img.setFileName(fileName);
                    destinationImagesFacade.create(img);
                } else {
                    notice = "toastr.error(\"The extension of the file is invalid. Extentions: .jpg, .png, .jpeg\");";
                    imageList = null;
                    imageList = destinationImagesFacade.findImagesByDestination(destination.getDestinationId());
                    return "manageDestinationImages";
                }
            }
            notice = "toastr.success(\"The destination has been added successfully!\");";
        } else {
            notice = "toastr.info(\"You must select images to add to database.\");";
        }
        imageList = null;
        imageList = destinationImagesFacade.findImagesByDestination(destination.getDestinationId());
        return "manageDestinationImages";
    }

    public Part getFile() {
        return null;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public List<DestinationImages> getImageList() {
        return imageList;
    }

    public void setImageList(List<DestinationImages> imageList) {
        this.imageList = imageList;
    }

    public Destinations getDestination() {
        return destination;
    }

    public void setDestination(Destinations destination) {
        this.destination = destination;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

}
