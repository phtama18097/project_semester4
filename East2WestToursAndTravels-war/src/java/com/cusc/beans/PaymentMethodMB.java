/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.beans;

import com.cusc.entities.PaymentMethods;
import com.cusc.sessionbean.PaymentMethodsFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Admin
 */
@Named(value = "paymentMethodMB")
@SessionScoped
public class PaymentMethodMB implements Serializable {

    @EJB
    private PaymentMethodsFacadeLocal paymentMethodsFacade;

    private PaymentMethods payment;
    private String notice = "";
    private int editID = 0;
    
    public PaymentMethodMB() {
        payment = new PaymentMethods();
    }
    
    public List<PaymentMethods> showAll() {
        return paymentMethodsFacade.findAll();
    }

    public void create() {
        try {
            PaymentMethods pm = new PaymentMethods();
            pm.setMethodName(payment.getMethodName());
            pm.setStatus(payment.getStatus());
            paymentMethodsFacade.create(pm);
            resetForm();
            notice = "toastr.success(\"New payment method has been added successfully!\");";
        } catch (Exception ex) {
            notice = "toastr.error(\"New payment method has not added. Try again\");";
        }
    }

    public void delete(PaymentMethods pm){
        try{
            paymentMethodsFacade.remove(pm);
            notice = "toastr.success(\"The payment method has been deleted successfully!\");";
        }catch(Exception ex){
            notice = "toastr.error(\"The payment method has a constraint. You cannot delete it.\");";
        }
    }
    
    public void update() {
        try{
            PaymentMethods pm = paymentMethodsFacade.find(editID);
            pm.setMethodName(payment.getMethodName());
            pm.setStatus(payment.getStatus());
            paymentMethodsFacade.edit(pm);
            resetForm();
            notice = "toastr.success(\"The payment method has been updated successfully!\");";
        }catch(Exception ex){
            notice = "toastr.error(\"The payment method has not updated. Try again.\");";
        }
    }
    
    public void resetForm(){
        payment.setMethodName(null);
        payment.setStatus(null);
        editID = 0;
    }
    
    public PaymentMethods getPayment() {
        return payment;
    }

    public void setPayment(PaymentMethods payment) {
        this.payment = payment;
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


    
    
}
