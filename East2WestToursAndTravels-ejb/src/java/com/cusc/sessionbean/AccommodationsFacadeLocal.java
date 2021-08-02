/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.Accommodations;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface AccommodationsFacadeLocal {

    void create(Accommodations accommodations);

    void edit(Accommodations accommodations);

    void remove(Accommodations accommodations);

    Accommodations find(Object id);

    List<Accommodations> findAll();

    List<Accommodations> findRange(int[] range);

    int count();

    public List<Accommodations> findAccommodationsForTour(int id);

    public int countSchedule(int id);
    
}
