/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Flights;
import com.cusc.sessionbean.FlightsFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Admin
 */
@Named(value = "flightMB")
@SessionScoped
public class FlightMB implements Serializable {

    @EJB
    private FlightsFacadeLocal flightsFacade;

    private String notice = ""; 
    
    
    public FlightMB() {
    }
    
    public List<Flights> showAll(){
        return flightsFacade.findAll();
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
    
}
