/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.Destinations;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface DestinationsFacadeLocal {

    void create(Destinations destinations);

    void edit(Destinations destinations);

    void remove(Destinations destinations);

    Destinations find(Object id);

    List<Destinations> findAll();

    List<Destinations> findRange(int[] range);

    int count();
    
}
