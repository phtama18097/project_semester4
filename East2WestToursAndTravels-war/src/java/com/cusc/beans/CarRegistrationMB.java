/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.CarRegistration;
import com.cusc.entities.CarRegistrationDetails;
import com.cusc.sessionbean.CarRegistrationDetailsFacadeLocal;
import com.cusc.sessionbean.CarRegistrationFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Admin
 */
@Named(value = "carRegistrationMB")
@SessionScoped
public class CarRegistrationMB implements Serializable {

    @EJB
    private CarRegistrationDetailsFacadeLocal carRegistrationDetailsFacade;

    @EJB
    private CarRegistrationFacadeLocal carRegistrationFacade;

    private String notice = "";
    private int editID = 0;
    private CarRegistration carRegistration;
    private List<CarRegistrationDetails> detailList;
    
    public CarRegistrationMB() {
        carRegistration = new CarRegistration();
    }
    
    public List<CarRegistration> showAll(){
        return carRegistrationFacade.findAll();
    }
    
    public String detail(int id){
        carRegistration = carRegistrationFacade.find(id);
        detailList = carRegistrationDetailsFacade.findDetailsByRegistration(id);
        return "rentingCarInvoice";
    }

    public String toDateTime(Date date) {
        return date == null? "" : date.getDate() + "/" + (date.getMonth()+1) + "/" + (date.getYear()+1900) + " " + date.getHours() + ":" + date.getMinutes()+ ":" + date.getSeconds();
    }
    
    public String toDate(Date date) {
        return date == null? "" : date.getDate() + "/" + (date.getMonth()+1) + "/" + (date.getYear()+1900);
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

    public CarRegistration getCarRegistration() {
        return carRegistration;
    }

    public void setCarRegistration(CarRegistration carRegistration) {
        this.carRegistration = carRegistration;
    }

    public List<CarRegistrationDetails> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<CarRegistrationDetails> detailList) {
        this.detailList = detailList;
    }
    
}
