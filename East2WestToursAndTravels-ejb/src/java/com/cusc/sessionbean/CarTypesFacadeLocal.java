/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.CarTypes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface CarTypesFacadeLocal {

    void create(CarTypes carTypes);

    void edit(CarTypes carTypes);

    void remove(CarTypes carTypes);

    CarTypes find(Object id);

    List<CarTypes> findAll();

    List<CarTypes> findRange(int[] range);

    int count();

    public int countCarByType(int typeId);
    
}
