/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.CarModels;
import com.cusc.sessionbean.CarModelsFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Admin
 */
@Named(value = "carModelMB")
@SessionScoped
public class CarModelMB implements Serializable {

    @EJB
    private CarModelsFacadeLocal carModelsFacade;

    private CarModels carModels;
    private String notice = "";
    private int editID = 0;
    
    public CarModelMB() {
        carModels = new CarModels();
    }
    
    public List<CarModels> showAll() {
        return carModelsFacade.findAll();
    }

    public void create() {
        try {
            CarModels ct = new CarModels();
            ct.setModelName(carModels.getModelName());
            carModelsFacade.create(ct);
            resetForm();
            notice = "toastr.success(\"New car model has been added successfully!\");";
        } catch (Exception ex) {
            notice = "toastr.error(\"New car model has not added. Try again\");";
        }
    }
    public void delete(CarModels ct){
        try{
            carModelsFacade.remove(ct);
            notice = "toastr.success(\"The car model has been deleted successfully!\");";
        }catch(Exception ex){
            notice = "toastr.error(\"The car model has a constraint. You cannot delete it.\");";
        }
    }
    
    public void update() {
        try{
            CarModels ct = carModelsFacade.find(editID);
            ct.setModelName(carModels.getModelName());
            carModelsFacade.edit(ct);
            resetForm();
            notice = "toastr.success(\"The car model has been updated successfully!\");";
        }catch(Exception ex){
            notice = "toastr.error(\"The car model has not updated. Try again.\");";
        }
    }

    public void resetForm() {
        carModels.setModelName(null);
        setEditID(0);
    }

    public CarModels getCarModels() {
        return carModels;
    }

    public void setCarModels(CarModels carModels) {
        this.carModels = carModels;
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
