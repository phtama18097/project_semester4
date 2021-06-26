/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.Restaurants;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface RestaurantsFacadeLocal {

    void create(Restaurants restaurants);

    void edit(Restaurants restaurants);

    void remove(Restaurants restaurants);

    Restaurants find(Object id);

    List<Restaurants> findAll();

    List<Restaurants> findRange(int[] range);

    int count();
    
}
