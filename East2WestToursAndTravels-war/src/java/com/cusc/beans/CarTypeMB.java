/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.CarTypes;
import com.cusc.sessionbean.CarTypesFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Admin
 */
@Named(value = "carTypeMB")
@SessionScoped
public class CarTypeMB implements Serializable {

    @EJB
    private CarTypesFacadeLocal carTypesFacade;

    private CarTypes carTypes;
    private String notice = "";
    private int editID = 0;

    /**
     * Creates a new instance of FeedbackTypeMB
     */
    public CarTypeMB() {
        carTypes = new CarTypes();
    }

    public List<CarTypes> showAll() {
        return carTypesFacade.findAll();
    }

    public void create() {
        try {
            CarTypes ct = new CarTypes();
            ct.setDescription(carTypes.getDescription());
            ct.setSeat(carTypes.getSeat());
            carTypesFacade.create(ct);
            resetForm();
            notice = "toastr.success(\"New car type has been added successfully!\");";
        } catch (Exception ex) {
            notice = "toastr.error(\"New car type has not added. Try again\");";
        }
    }
    public void delete(CarTypes ct){
        try{
            carTypesFacade.remove(ct);
            notice = "toastr.success(\"The car type has been deleted successfully!\");";
        }catch(Exception ex){
            notice = "toastr.error(\"The car type has a constraint. You cannot delete it.\");";
        }
    }
    
    public void update() {
        try{
            CarTypes ct = carTypesFacade.find(editID);
            ct.setDescription(carTypes.getDescription());
            ct.setSeat(carTypes.getSeat());
            carTypesFacade.edit(ct);
            resetForm();
            notice = "toastr.success(\"The car type has been updated successfully!\");";
        }catch(Exception ex){
            notice = "toastr.error(\"The car type has not updated. Try again.\");";
        }
    }

    public void resetForm() {
        carTypes.setDescription(null);
        carTypes.setSeat(null);
        setEditID(0);
    }

    public CarTypes getCarTypes() {
        return carTypes;
    }

    public void setCarTypes(CarTypes carTypes) {
        this.carTypes = carTypes;
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
