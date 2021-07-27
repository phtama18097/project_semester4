/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.CarRegistrationDetails;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface CarRegistrationDetailsFacadeLocal {

    void create(CarRegistrationDetails carRegistrationDetails);

    void edit(CarRegistrationDetails carRegistrationDetails);

    void remove(CarRegistrationDetails carRegistrationDetails);

    CarRegistrationDetails find(Object id);

    List<CarRegistrationDetails> findAll();

    List<CarRegistrationDetails> findRange(int[] range);

    int count();

    public List<CarRegistrationDetails> findDetailsByRegistration(int registrationId);
    
}
