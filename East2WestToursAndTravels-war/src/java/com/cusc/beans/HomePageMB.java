/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Cars;
import com.cusc.entities.Tours;
import com.cusc.sessionbean.CarsFacadeLocal;
import com.cusc.sessionbean.ConfigurationFacadeLocal;
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
@Named(value = "homePageMB")
@SessionScoped
public class HomePageMB implements Serializable {

    @EJB
    private ToursFacadeLocal toursFacade;

    @EJB
    private ConfigurationFacadeLocal configurationFacade;

    @EJB
    private CarsFacadeLocal carFacade;
    
    private static final String HOME_BANNER_1 = "HOME_BANNER_1";
    private static final String HOME_BANNER_2 = "HOME_BANNER_2";
    private static final String HOME_BANNER_3 = "HOME_BANNER_3";
    private static final String HOME_BANNER_4 = "HOME_BANNER_4";
    private static final String HOME_BANNER_5 = "HOME_BANNER_5";
    private static final String HOME_BANNER_6 = "HOME_BANNER_6";
    private static final String HOME_BANNER_7 = "HOME_BANNER_7";
    
    public HomePageMB() {
    }
    
    public String homeBanner1(){
        return configurationFacade.findByConfigName(HOME_BANNER_1).getFileLocation();
    }
    
    public String homeBanner2(){
        return configurationFacade.findByConfigName(HOME_BANNER_2).getFileLocation();
    }
    
    public String homeBanner3(){
        return configurationFacade.findByConfigName(HOME_BANNER_3).getFileLocation();
    }
    
    public String homeBanner4(){
        return configurationFacade.findByConfigName(HOME_BANNER_4).getFileLocation();
    }
    
    public String homeBanner5(){
        return configurationFacade.findByConfigName(HOME_BANNER_5).getFileLocation();
    }
    
    public String homeBanner6(){
        return configurationFacade.findByConfigName(HOME_BANNER_6).getFileLocation();
    }
    
    public String homeBanner7(){
        return configurationFacade.findByConfigName(HOME_BANNER_7).getFileLocation();
    }
    
    public List<Tours> showNewTours(){
        return toursFacade.findNewTours(6, 1);
    }
    
    public List<Cars> showNewCarsOnLeft(){
        return carFacade.findNewCars(5, 1);
    }
    
    public List<Cars> showNewCarsOnRight(){
        return carFacade.findNewCars(5, 2);
    }
}
