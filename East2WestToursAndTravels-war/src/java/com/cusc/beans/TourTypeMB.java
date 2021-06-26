/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.TourTypes;
import com.cusc.sessionbean.TourTypesFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Admin
 */
@Named(value = "tourTypeMB")
@SessionScoped
public class TourTypeMB implements Serializable {

    @EJB
    private TourTypesFacadeLocal tourTypesFacade;

    private TourTypes tourTypes;
    private String notice = "";
    private int editID = 0;
    
    
    public TourTypeMB() {
        tourTypes = new TourTypes();
    }
    
    public List<TourTypes> showAll() {
        return tourTypesFacade.findAll();
    }

    public void create() {
        try {
            TourTypes tt = new TourTypes();
            tt.setTypeName(tourTypes.getTypeName());
            tourTypesFacade.create(tt);
            resetForm();
            notice = "toastr.success(\"New tour type has been added successfully!\");";
        } catch (Exception ex) {
            notice = "toastr.error(\"New tour type has not added. Try again\");";
        }
    }

    public void delete(TourTypes tt){
        try{
            tourTypesFacade.remove(tt);
            notice = "toastr.success(\"The tour type has been deleted successfully!\");";
        }catch(Exception ex){
            notice = "toastr.error(\"The tour type has a constraint. You cannot delete it.\");";
        }
    }
    
    public void update() {
        try{
            TourTypes tt = tourTypesFacade.find(editID);
            tt.setTypeName(tourTypes.getTypeName());
            tourTypesFacade.edit(tt);
            resetForm();
            notice = "toastr.success(\"The tour type has been updated successfully!\");";
        }catch(Exception ex){
            notice = "toastr.error(\"The tour type has not updated. Try again.\");";
        }
    }
    
    public void resetForm(){
        tourTypes.setTypeName(null);
        editID = 0;
    }

    public TourTypes getTourTypes() {
        return tourTypes;
    }

    public void setTourTypes(TourTypes tourTypes) {
        this.tourTypes = tourTypes;
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
    
}
