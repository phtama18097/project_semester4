/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.TourRegistration;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface TourRegistrationFacadeLocal {

    void create(TourRegistration tourRegistration);

    void edit(TourRegistration tourRegistration);

    void remove(TourRegistration tourRegistration);

    TourRegistration find(Object id);

    List<TourRegistration> findAll();

    List<TourRegistration> findRange(int[] range);

    int count();

    public List<TourRegistration> findHistory(int customerId);
    
}
