/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.Employees;
import com.cusc.sessionbean.EmployeesFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;

/**
 *
 * @author Admin
 */
@Named(value = "loginMB")
@SessionScoped
public class LoginMB implements Serializable {

    @EJB
    private EmployeesFacadeLocal employeesFacade;

    private String username;
    private String password;
    Employees employee;
    private String error;
    /**
     * Creates a new instance of LoginMB
     */
    public LoginMB() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
   
    public String login() {
        return "mainLayout";
//        if (employeesFacade.checkLoginEmployee(username, password )) {
//             employee = employeesFacade.find(username);
//            return "mainLayout";
//        }
//        else {
//            error="Login Fail";
//            return "loginEmployee";
//        }

    }
//    public String registerCustomer(){
//        if (pwd.equals(cfpwd)) {
//            Customer d = new Customer(username, pwd);
//            customerFacade.create(d);
//            return "index";
//        } else { 
//            error="Please enter correctly";
//            return "register?faces-redirect=true";
//        }
//    } 

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
