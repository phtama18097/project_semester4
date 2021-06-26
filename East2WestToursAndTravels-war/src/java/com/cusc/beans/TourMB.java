/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Tours;
import com.cusc.sessionbean.EmployeesFacadeLocal;
import com.cusc.sessionbean.TourPackagesFacadeLocal;
import com.cusc.sessionbean.ToursFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

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
    private String notice = "";
    private int editID = 0;
    private boolean tourStatus = true;
    private int packageID = 0;
            
    public TourMB() {
        tours = new Tours();
    }
    
    public List<Tours> showAll() {
        return toursFacade.findAll();
    }

    public void create() {
        try {
            Tours t = new Tours();
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
            if(tourStatus){
                t.setStatus((short)1);
            }else{
                t.setStatus((short)0);
            }
            toursFacade.create(t);
            resetForm();
            notice = "toastr.success(\"New tour has been added successfully!\");";
        } catch (Exception ex) {
            notice = "toastr.error(\"New tour has not added. Try again\");";
        }
    }

    public void delete(Tours t){
        try{
            toursFacade.remove(t);
            notice = "toastr.success(\"The tour has been deleted successfully!\");";
        }catch(Exception ex){
            notice = "toastr.error(\"The tour has a constraint. You cannot delete it.\");";
        }
    }
    
    public void update() {
        try{
            Tours t = toursFacade.find(editID);
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
            if(tourStatus){
                t.setStatus((short)1);
            }else{
                t.setStatus((short)0);
            }
            toursFacade.edit(t);
            resetForm();
            notice = "toastr.success(\"The tour has been updated successfully!\");";
        }catch(Exception ex){
            notice = "toastr.error(\"The tour has not updated. Try again.\");";
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
    
    
}
