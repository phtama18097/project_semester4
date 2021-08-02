/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.CarImages;
import com.cusc.entities.Cars;
import com.cusc.helps.CommonConstant;
import com.cusc.helps.ImageTools;
import com.cusc.sessionbean.CarImagesFacadeLocal;
import com.cusc.sessionbean.CarsFacadeLocal;
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
import com.cusc.helps.NotificationTools;

/**
 *
 * @author Admin
 */
@Named(value = "carImageMB")
@SessionScoped
public class CarImageMB implements Serializable {

    @EJB
    private CarImagesFacadeLocal carImagesFacade;

    @EJB
    private CarsFacadeLocal carsFacade;

    private Part file;
    private final String UPLOAD_DIRECTORY = "uploads" + File.separator + "imgCars";

    private List<CarImages> imageList;
    private Cars car;
    private String notice = "";
    
    public CarImageMB() {
    }
    
    public String editImages(int carID) {
        imageList = null;
        car = null;
        imageList = carImagesFacade.findImagesByCar(carID);
        car = carsFacade.find(carID);
        return "manageCarImages";
    }
    
    public void delete(CarImages image){
        try {
            carImagesFacade.remove(image);
            notice = NotificationTools.deleteSuccess("image");
            ImageTools.deleteFile(image.getFileName(), "imgCars");
        } catch (Exception ex) {
            notice = NotificationTools.deleteFail("image");
        }
        imageList = null;
        imageList = carImagesFacade.findImagesByCar(car.getCarId());
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
                        notice = NotificationTools.error(CommonConstant.INVALID_FILE_SIZE_NOTICE);
                        imageList = null;
                        imageList = carImagesFacade.findImagesByCar(car.getCarId());
                        return "manageCarImages";
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
                    CarImages img = new CarImages();
                    img.setCarId(car);
                    img.setFileName(fileName);
                    carImagesFacade.create(img);
                } else {
                    notice = NotificationTools.error(CommonConstant.INVALID_IMAGE_EXTENSION_NOTICE);
                    imageList = null;
                    imageList = carImagesFacade.findImagesByCar(car.getCarId());
                    return "manageCarImages";
                }
            }
            notice = NotificationTools.createSuccess("image");
        } else {
            notice = NotificationTools.error(CommonConstant.IMAGE_IS_NULL_NOTICE);
        }
        imageList = null;
        imageList = carImagesFacade.findImagesByCar(car.getCarId());
        return "manageCarImages";
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public List<CarImages> getImageList() {
        return imageList;
    }

    public void setImageList(List<CarImages> imageList) {
        this.imageList = imageList;
    }

    public Cars getCar() {
        return car;
    }

    public void setCar(Cars car) {
        this.car = car;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
    
}
