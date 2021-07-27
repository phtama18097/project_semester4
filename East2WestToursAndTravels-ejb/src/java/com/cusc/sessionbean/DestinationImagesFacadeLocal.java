/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.DestinationImages;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface DestinationImagesFacadeLocal {

    void create(DestinationImages destinationImages);

    void edit(DestinationImages destinationImages);

    void remove(DestinationImages destinationImages);

    DestinationImages find(Object id);

    List<DestinationImages> findAll();

    List<DestinationImages> findRange(int[] range);
    
    public List<DestinationImages> findImagesByDestination(int destinationID);

    int count();

    public List<DestinationImages> findImages(int destinationID, int records, int page);
    
}
