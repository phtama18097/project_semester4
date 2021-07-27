/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.PaymentMethods;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface PaymentMethodsFacadeLocal {

    void create(PaymentMethods paymentMethods);

    void edit(PaymentMethods paymentMethods);

    void remove(PaymentMethods paymentMethods);

    PaymentMethods find(Object id);

    List<PaymentMethods> findAll();

    List<PaymentMethods> findRange(int[] range);

    int count();

    public List<PaymentMethods> findActivePaymentMethods();
    
}
