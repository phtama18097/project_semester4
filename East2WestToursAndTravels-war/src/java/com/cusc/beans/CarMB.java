/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Cars;
import com.cusc.sessionbean.CarModelsFacadeLocal;
import com.cusc.sessionbean.CarTypesFacadeLocal;
import com.cusc.sessionbean.CarsFacadeLocal;
import com.cusc.sessionbean.EmployeesFacadeLocal;
import com.cusc.helps.ImageTools;
import com.cusc.helps.*;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.Part;

/**
 *
 * @author Admin
 */
@Named(value = "carMB")
@SessionScoped
public class CarMB implements Serializable {

    @EJB
    private CarModelsFacadeLocal carModelsFacade;

    @EJB
    private CarTypesFacadeLocal carTypesFacade;

    @EJB
    private CarsFacadeLocal carsFacade;

    private static final String UPLOAD_DIRECTORY = "imgCars";
    private static final String BEAN_OBJECT = "CAR";

    private Part file;
    private Cars car;
    private String notice = "";
    private int editID = 0;
    private boolean carStatus = true;
    private int typeID = 0;
    private int modelID = 0;

    public CarMB() {
        car = new Cars();
    }

    public List<Cars> showAll() {
        return carsFacade.findAll();
    }

    public void create() {
        try {
            Cars c = new Cars();
            String result = ImageTools.uploadFile(file, UPLOAD_DIRECTORY);
            switch (result) {
                case "":
                    notice = NotificationTools.info(CommonConstant.IMAGE_IS_NULL_NOTICE);
                    return;
                case CommonConstant.FILE_IO:
                    notice = NotificationTools.error(CommonConstant.UPLOADING_FAIL_NOTICE);
                    return;
                case CommonConstant.FILE_EXTENSION:
                    notice = NotificationTools.error(CommonConstant.INVALID_IMAGE_EXTENSION_NOTICE);
                    return;
                case CommonConstant.FILE_SIZE:
                    notice = NotificationTools.error(CommonConstant.INVALID_FILE_SIZE_NOTICE);
                    return;
                default:
                    c.setThumbnail(result);
                    break;
            }
            c.setCarName(car.getCarName());
            c.setModelId(carModelsFacade.find(modelID));
            c.setTypeId(carTypesFacade.find(typeID));
            c.setUnitPrice(car.getUnitPrice());
            c.setUnitInStock(car.getUnitInStock());
            c.setUnitOnOrder(0);
            c.setShortDescripiton(car.getShortDescripiton());
            c.setDescription(car.getDescription());
            c.setStatus(StatusTools.readStatus(carStatus));
            carsFacade.create(c);
            resetForm();
            notice = NotificationTools.createSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.createFail(BEAN_OBJECT);
        }
    }

    public void delete(Cars c) {
        try {
            carsFacade.remove(c);
            if(ImageTools.deleteFile(c.getThumbnail(), UPLOAD_DIRECTORY)){
                notice = NotificationTools.deleteSuccess(BEAN_OBJECT);
            }else{
                notice = NotificationTools.createFail(BEAN_OBJECT);
            }         
        } catch (Exception ex) {
            notice = NotificationTools.createFail(BEAN_OBJECT);
        }
    }

    public void update() {
        try {
            Cars c = carsFacade.find(editID);
            if (file != null) {
                ImageTools.deleteFile(c.getThumbnail(), UPLOAD_DIRECTORY);
                String result = ImageTools.uploadFile(file, UPLOAD_DIRECTORY);
                switch (result) {
                    case "":
                        notice = NotificationTools.info(CommonConstant.IMAGE_IS_NULL_NOTICE);
                        return;
                    case CommonConstant.FILE_IO:
                        notice = NotificationTools.error(CommonConstant.UPLOADING_FAIL_NOTICE);
                        return;
                    case CommonConstant.FILE_EXTENSION:
                        notice = NotificationTools.error(CommonConstant.INVALID_IMAGE_EXTENSION_NOTICE);
                        return;
                    case CommonConstant.FILE_SIZE:
                        notice = NotificationTools.error(CommonConstant.INVALID_FILE_SIZE_NOTICE);
                        return;
                    default:
                        c.setThumbnail(result);
                        break;
                }
            }
            c.setCarName(car.getCarName());
            c.setModelId(carModelsFacade.find(modelID));
            c.setTypeId(carTypesFacade.find(typeID));
            c.setUnitPrice(car.getUnitPrice());
            c.setUnitInStock(car.getUnitInStock());
            c.setShortDescripiton(car.getShortDescripiton());
            c.setDescription(car.getDescription());
            c.setStatus(StatusTools.readStatus(carStatus));
            carsFacade.edit(c);
            resetForm();
            notice = NotificationTools.updateSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.updateFail(BEAN_OBJECT);
        }
    }

    public void resetForm() {
        car.setCarName(null);
        car.setModelId(null);
        car.setTypeId(null);
        car.setUnitPrice(null);
        car.setUnitInStock(null);
        car.setUnitOnOrder(null);
        car.setShortDescripiton(null);
        car.setDescription(null);
        car.setThumbnail(null);
        car.setStatus(null);
        setEditID(0);
        setModelID(0);
        setTypeID(0);
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

    public boolean isCarStatus() {
        return carStatus;
    }

    public void setCarStatus(boolean carStatus) {
        this.carStatus = carStatus;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public int getModelID() {
        return modelID;
    }

    public void setModelID(int modelID) {
        this.modelID = modelID;
    }

    public Cars getCar() {
        return car;
    }

    public void setCar(Cars car) {
        this.car = car;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

}
