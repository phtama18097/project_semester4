/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.AccommodationSchedules;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface AccommodationSchedulesFacadeLocal {

    void create(AccommodationSchedules accommodationSchedules);

    void edit(AccommodationSchedules accommodationSchedules);

    void remove(AccommodationSchedules accommodationSchedules);

    AccommodationSchedules find(Object id);

    List<AccommodationSchedules> findAll();

    List<AccommodationSchedules> findRange(int[] range);

    int count();

    public List<AccommodationSchedules> findAccommodationsOfTour(int tourID);
    
}
