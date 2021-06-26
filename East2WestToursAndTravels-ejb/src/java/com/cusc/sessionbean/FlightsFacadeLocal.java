/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.Flights;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface FlightsFacadeLocal {

    void create(Flights flights);

    void edit(Flights flights);

    void remove(Flights flights);

    Flights find(Object id);

    List<Flights> findAll();

    List<Flights> findRange(int[] range);

    int count();
    
}
