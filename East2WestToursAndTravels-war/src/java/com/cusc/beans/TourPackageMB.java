/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.TourPackages;
import com.cusc.sessionbean.TourPackagesFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Admin
 */
@Named(value = "tourPackageMB")
@SessionScoped
public class TourPackageMB implements Serializable {

    @EJB
    private TourPackagesFacadeLocal tourPackagesFacade;

    private TourPackages tourPackages;
    private String notice = "";
    private int editID = 0;
    private boolean packageStatus = true;

    public TourPackageMB() {
        tourPackages = new TourPackages();
    }

    public List<TourPackages> showAll(){
        return tourPackagesFacade.findAll();
    }
    
    public void create() {
        try {
            TourPackages tp = new TourPackages();
            tp.setPackageName(tourPackages.getPackageName());
            tp.setDescription(tourPackages.getDescription());
            if(packageStatus){
                tp.setStatus((short)1);
            }else{
                tp.setStatus((short)0);
            }
            tourPackagesFacade.create(tp);
            resetForm();
            notice = "toastr.success(\"New tour package has been added successfully!\");";
        } catch (Exception ex) {
            notice = "toastr.error(\"New tour package has not added. Try again\");";
        }
    }

    public void delete(TourPackages tp){
        try{
            tourPackagesFacade.remove(tp);
            notice = "toastr.success(\"The tour package has been deleted successfully!\");";
        }catch(Exception ex){
            notice = "toastr.error(\"The tour package has a constraint. You cannot delete it.\");";
        }
    }
    
    public void update() {
        try{
            TourPackages tp = tourPackagesFacade.find(editID);
            tp.setPackageName(tourPackages.getPackageName());
            tp.setDescription(tourPackages.getDescription());
            if(packageStatus){
                tp.setStatus((short)1);
            }else{
                tp.setStatus((short)0);
            }
            tourPackagesFacade.edit(tp);
            resetForm();
            notice = "toastr.success(\"The tour package has been updated successfully!\");";
        }catch(Exception ex){
            notice = "toastr.error(\"The tour package has not updated. Try again.\");";
        }
    }
    
    public void resetForm(){
        tourPackages.setPackageName(null);
        tourPackages.setDescription(null);
        tourPackages.setStatus(null);
        editID = 0;
    }
    
    public TourPackages getTourPackages() {
        return tourPackages;
    }

    public void setTourPackages(TourPackages tourPackages) {
        this.tourPackages = tourPackages;
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

    public boolean isPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(boolean packageStatus) {
        this.packageStatus = packageStatus;
    }
    
}
