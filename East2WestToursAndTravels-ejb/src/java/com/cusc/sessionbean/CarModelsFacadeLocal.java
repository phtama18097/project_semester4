/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.CarModels;
import com.cusc.entities.Cars;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface CarModelsFacadeLocal {

    void create(CarModels carModels);

    void edit(CarModels carModels);

    void remove(CarModels carModels);

    CarModels find(Object id);

    List<CarModels> findAll();

    List<CarModels> findRange(int[] range);

    int count();
    List<Cars> carCount(int modelId);
    
}
