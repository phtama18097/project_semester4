/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.RestaurantSchedules;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface RestaurantSchedulesFacadeLocal {

    void create(RestaurantSchedules restaurantSchedules);

    void edit(RestaurantSchedules restaurantSchedules);

    void remove(RestaurantSchedules restaurantSchedules);

    RestaurantSchedules find(Object id);

    List<RestaurantSchedules> findAll();

    List<RestaurantSchedules> findRange(int[] range);

    int count();

    public List<RestaurantSchedules> findRestaurantsOfTour(int tourID);

    
}
