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
public class NotificationTools {
    public static String createSuccess(String object){
        return "toastr.success(\"New " + object + " has been added successfully!\");";
    }
    
    public static String createFail(String object){
        return "toastr.error(\"New " + object + " has not added. Try again\");";
    }
    
    public static String updateSuccess(String object){
        return "toastr.success(\"The " + object + " has been updated successfully!\");";
    }
    
    public static String updateFail(String object){
        return "toastr.error(\"The " + object + " has not updated. Try again\");";
    }
    
    public static String deleteSuccess(String object){
        return "toastr.success(\"The " + object + " has been deleted successfully!\");";
    }
    
    public static String deleteFail(String object){
        return "toastr.error(\"The " + object + " has a constraint. You cannot delete it.\");";
    }
    
    public static String success(String message){
        return "toastr.success(\"" + message + "\");";
    }
    
    public static String info(String message){
        return "toastr.info(\"" + message + "\");";
    }
    
    public static String error(String message){
        return "toastr.error(\"" + message + "\");";
    }
    
}
