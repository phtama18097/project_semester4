/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.CarImages;
import com.cusc.entities.Cars;
import com.cusc.sessionbean.CarModelsFacadeLocal;
import com.cusc.sessionbean.CarTypesFacadeLocal;
import com.cusc.sessionbean.CarsFacadeLocal;
import com.cusc.helps.ImageTools;
import com.cusc.helps.*;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
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
    private Cars carClient;
    private String notice = "";
    private int editID = 0;
    private int typeID = 0;
    private int modelID = 0;
    
    private List<Cars> listCars;
    
    private int currentModel;
    private int currentCarType;
    private int currentPage;
    

    public CarMB() {
        car = new Cars();
        listCars = null;
    }

    public List<Cars> showAll() {
        return carsFacade.findAll();
    }
    
   public String showAllClient(int page) {
       reset();
       listCars = carsFacade.findAllAvai(12, page);
       setCurrentPage(page);
        return "car";
    }
    
    public int countCar() {
        return carsFacade.count();
    }
    
    public String showByModel(int modelId, int page) {
        reset();
        listCars = carsFacade.findByModel(modelId, 12, page);
        setCurrentModel(modelId);
        setCurrentPage(page);
        return "car";
    }
    
    public String showByType(int typeId, int page) {
        reset();
        listCars = carsFacade.findByType(typeId, 12, page);
        setCurrentCarType(typeId);
        setCurrentPage(page);
        return "car";
    }
    
    public int totalCarPage() {
        if(currentModel != 0) {
            return (int)Math.ceil((float)carModelsFacade.carCount(currentModel).size()/(float)12);
        }
        else if(currentCarType != 0) {
            return (int)Math.ceil((float)carTypesFacade.countCarByType(currentCarType)/(float)12);
        }
        else {
            return (int)Math.ceil((float)carsFacade.countAllCars()/(float)12);
        }
    }
    public void reset() {
        listCars = null;
        setCurrentModel(0);
        setCurrentCarType(0);
        setCurrentPage(0);
    }
    
    public List<Cars> showCarOther(int typeId, int modelId, int carId) {
        return carsFacade.carOther(typeId, modelId, carId);
    }
    public List<CarImages> showAllImages(int carId, String thumbnail) {
        return carsFacade.showImage(carId, thumbnail);
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
            c.setLicencePlate(car.getLicencePlate());
            c.setModelId(carModelsFacade.find(modelID));
            c.setTypeId(carTypesFacade.find(typeID));
            c.setUnitPrice(car.getUnitPrice());
            c.setShortDescripiton(car.getShortDescripiton());
            c.setDescription(car.getDescription());
            c.setStatus(car.getStatus());
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
                notice = NotificationTools.deleteFail(BEAN_OBJECT);
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
            c.setLicencePlate(car.getLicencePlate());
            c.setModelId(carModelsFacade.find(modelID));
            c.setTypeId(carTypesFacade.find(typeID));
            c.setUnitPrice(car.getUnitPrice());
            c.setShortDescripiton(car.getShortDescripiton());
            c.setDescription(car.getDescription());
            c.setStatus(car.getStatus());
            carsFacade.edit(c);
            resetForm();
            notice = NotificationTools.updateSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.updateFail(BEAN_OBJECT);
        }
    }
    public String detail(int carId) {
        carClient = carsFacade.find(carId);
        carClient.setCarName(carClient.getCarName());
            carClient.setLicencePlate(carClient.getLicencePlate());
            carClient.setModelId(carClient.getModelId());
            carClient.setTypeId(carClient.getTypeId());
            carClient.setUnitPrice(carClient.getUnitPrice());
            carClient.setShortDescripiton(carClient.getShortDescripiton());
            carClient.setDescription(carClient.getDescription());
            carClient.setStatus(carClient.getStatus());
            return "carDetail";
    }

    public void resetForm() {
        car.setCarName(null);
        car.setLicencePlate(null);
        car.setModelId(null);
        car.setTypeId(null);
        car.setUnitPrice(null);
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

    public List<Cars> getListCars() {
        return listCars;
    }

    public void setListCars(List<Cars> listCars) {
        this.listCars = listCars;
    }

    public Cars getCarClient() {
        return carClient;
    }

    public void setCarClient(Cars carClient) {
        this.carClient = carClient;
    }

    public int getCurrentModel() {
        return currentModel;
    }

    public void setCurrentModel(int currentModel) {
        this.currentModel = currentModel;
    }

    public int getCurrentCarType() {
        return currentCarType;
    }

    public void setCurrentCarType(int currentCarType) {
        this.currentCarType = currentCarType;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
