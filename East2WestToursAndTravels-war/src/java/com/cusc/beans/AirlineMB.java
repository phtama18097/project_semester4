/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Airlines;
import com.cusc.sessionbean.AirlinesFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Admin
 */
@Named(value = "airlineMB")
@SessionScoped
public class AirlineMB implements Serializable {

    @EJB
    private AirlinesFacadeLocal airlinesFacade;
    
    private Airlines airlines;
    private String notice = "";
    private int editID = 0;

            
    /**
     * Creates a new instance of AirlineMB
     */
    public AirlineMB() {
        airlines = new Airlines();
    }
    
    public List<Airlines> showAll() {
        return airlinesFacade.findAll();
    }
    
    public void create() {
        try {
            Airlines al = new Airlines();
            al.setAirlineName(airlines.getAirlineName());
            airlinesFacade.create(al);
            resetForm();
            notice = "toastr.success(\"New airline has been added successfully!\");";
        } catch (Exception ex) {
            notice = "toastr.error(\"New airline has not added. Try again\");";
        }
    }
    public void delete(Airlines pm){
        try{
            airlinesFacade.remove(pm);
            notice = "toastr.success(\"The airline has been deleted successfully!\");";
        }catch(Exception ex){
            notice = "toastr.error(\"The airline has a constraint. You cannot delete it.\");";
        }
    }
    
    public void update() {
        try{
            Airlines al = airlinesFacade.find(editID);
            al.setAirlineName(airlines.getAirlineName());
            airlinesFacade.edit(al);
            resetForm();
            notice = "toastr.success(\"The airline has been updated successfully!\");";
        }catch(Exception ex){
            notice = "toastr.error(\"The airline has not updated. Try again.\");";
        }
    }

    public void resetForm(){
        airlines.setAirlineName(null);
        editID = 0;
    }
    public Airlines getAirlines() {
        return airlines;
    }

    public void setAirlines(Airlines airlines) {
        this.airlines = airlines;
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
