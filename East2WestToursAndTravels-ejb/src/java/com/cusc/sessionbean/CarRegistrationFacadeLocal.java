/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.CarRegistration;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface CarRegistrationFacadeLocal {

    void create(CarRegistration carRegistration);

    void edit(CarRegistration carRegistration);

    void remove(CarRegistration carRegistration);

    CarRegistration find(Object id);

    List<CarRegistration> findAll();

    List<CarRegistration> findRange(int[] range);

    int count();

    public List<CarRegistration> findHistory(int customerId);

    public List<CarRegistration> findAllOrderByDesc();
    
}
