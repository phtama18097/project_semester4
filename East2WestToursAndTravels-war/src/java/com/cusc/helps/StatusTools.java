/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.helps;

/**
 *
 * @author Admin
 */
public class StatusTools {
    public static Short readStatus(boolean status){
        if(status){
            return 1;
        }else{
            return 0;
        }
    }
}
