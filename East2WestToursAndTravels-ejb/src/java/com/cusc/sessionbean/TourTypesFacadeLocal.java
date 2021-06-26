/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.TourTypes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface TourTypesFacadeLocal {

    void create(TourTypes tourTypes);

    void edit(TourTypes tourTypes);

    void remove(TourTypes tourTypes);

    TourTypes find(Object id);

    List<TourTypes> findAll();

    List<TourTypes> findRange(int[] range);

    int count();
    
}
