/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.sessionbean.CarsFacadeLocal;
import com.cusc.sessionbean.ConfigurationFacadeLocal;
import com.cusc.sessionbean.ToursFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
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
    
}
