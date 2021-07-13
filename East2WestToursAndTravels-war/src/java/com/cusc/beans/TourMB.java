/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Tours;
import com.cusc.helps.CommonConstant;
import com.cusc.helps.ImageTools;
import com.cusc.helps.NotificationTools;
import com.cusc.helps.StatusTools;
import com.cusc.sessionbean.EmployeesFacadeLocal;
import com.cusc.sessionbean.TourPackagesFacadeLocal;
import com.cusc.sessionbean.ToursFacadeLocal;
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
@Named(value = "tourMB")
@SessionScoped
public class TourMB implements Serializable {

    @EJB
    private EmployeesFacadeLocal employeesFacade;

    @EJB
    private TourPackagesFacadeLocal tourPackagesFacade;

    @EJB
    private ToursFacadeLocal toursFacade;
    
    private Tours tours;
    private Part file;
    private String notice = "";
    private int editID = 0;
    private boolean tourStatus = true;
    private int packageID = 0;
    
    private static final String UPLOAD_DIRECTORY = "imgTours";
    private static final String BEAN_OBJECT = "TOUR";
            
    public TourMB() {
        tours = new Tours();
    }
    
    public List<Tours> showAll() {
        return toursFacade.findAll();
    }

    public void create() {
        try {
            Tours t = new Tours();
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
                    t.setThumbnail(result);
                    break;
            }
            t.setTourName(tours.getTourName());
            t.setPackageId(tourPackagesFacade.find(packageID));
            t.setUnitPrice(tours.getUnitPrice());
            t.setShortDescription(tours.getShortDescription());
            t.setDescription(tours.getDescription());
            t.setDepartureDate(tours.getDepartureDate());
            t.setReturnDate(tours.getReturnDate());
            t.setMinQuantity(tours.getMinQuantity());
            t.setMaxQuantity(tours.getMaxQuantity());
            t.setEmployeeId(null); // Them employee khi xong dang nhap
            t.setStatus(StatusTools.readStatus(tourStatus));
            toursFacade.create(t);
            resetForm();
            notice = NotificationTools.createSuccess(BEAN_OBJECT);
        } catch (Exception ex) {
            notice = NotificationTools.createFail(BEAN_OBJECT);
        }
    }

    public void delete(Tours t){
        try{
            toursFacade.remove(t);
            if(ImageTools.deleteFile(t.getThumbnail(), UPLOAD_DIRECTORY)){
                notice = NotificationTools.deleteSuccess(BEAN_OBJECT);
            }else{
                notice = NotificationTools.deleteFail(BEAN_OBJECT);
            }
        }catch(Exception ex){
            notice = NotificationTools.deleteFail(BEAN_OBJECT);
        }
    }
    
    public void update() {
        try{
            Tours t = toursFacade.find(editID);
            if (file != null) {
                ImageTools.deleteFile(t.getThumbnail(), UPLOAD_DIRECTORY);
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
                        t.setThumbnail(result);
                        break;
                }
            }
            t.setTourName(tours.getTourName());
            t.setPackageId(tourPackagesFacade.find(packageID));
            t.setUnitPrice(tours.getUnitPrice());
            t.setShortDescription(tours.getShortDescription());
            t.setDescription(tours.getDescription());
            t.setDepartureDate(tours.getDepartureDate());
            t.setReturnDate(tours.getReturnDate());
            t.setMinQuantity(tours.getMinQuantity());
            t.setMaxQuantity(tours.getMaxQuantity());
            t.setEmployeeId(null); // Them employee khi xong dang nhap
            t.setStatus(StatusTools.readStatus(tourStatus));
            toursFacade.edit(t);
            resetForm();
            notice = NotificationTools.updateSuccess(BEAN_OBJECT);
        }catch(Exception ex){
            notice = NotificationTools.updateFail(BEAN_OBJECT);
        }
    }
    
    public void resetForm(){
        tours.setTourName(null);
        tours.setPackageId(null);
        tours.setUnitPrice(null);
        tours.setShortDescription(null);
        tours.setDescription(null);
        tours.setDepartureDate(null);
        tours.setReturnDate(null);
        tours.setMinQuantity(null);
        tours.setMaxQuantity(null);
        tours.setEmployeeId(null);
        tours.setStatus((short)0);
        setTourStatus(true);
        setPackageID(0);
        setFile(null);
        editID = 0;
    }

    public Tours getTours() {
        return tours;
    }

    public void setTours(Tours tours) {
        this.tours = tours;
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

    public int getPackageID() {
        return packageID;
    }

    public void setPackageID(int packageID) {
        this.packageID = packageID;
    }

    public boolean isTourStatus() {
        return tourStatus;
    }

    public void setTourStatus(boolean tourStatus) {
        this.tourStatus = tourStatus;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    
    
}
