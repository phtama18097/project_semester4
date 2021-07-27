/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.CarImages;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface CarImagesFacadeLocal {

    void create(CarImages carImages);

    void edit(CarImages carImages);

    void remove(CarImages carImages);

    CarImages find(Object id);

    List<CarImages> findAll();

    List<CarImages> findRange(int[] range);
    
    public List<CarImages> findImagesByCar(int carID);

    int count();

    public List<CarImages> find4Images(int carID);
    
}
