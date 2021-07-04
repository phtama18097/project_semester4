/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Admin
 */
@Named(value = "carMB")
@SessionScoped
public class CarMB implements Serializable {

    /**
     * Creates a new instance of CarMB
     */
    public CarMB() {
    }
    
}
