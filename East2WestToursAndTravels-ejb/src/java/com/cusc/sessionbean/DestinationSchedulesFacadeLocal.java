/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.DestinationSchedules;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface DestinationSchedulesFacadeLocal {

    void create(DestinationSchedules destinationSchedules);

    void edit(DestinationSchedules destinationSchedules);

    void remove(DestinationSchedules destinationSchedules);

    DestinationSchedules find(Object id);

    List<DestinationSchedules> findAll();

    List<DestinationSchedules> findRange(int[] range);

    int count();
    
}
