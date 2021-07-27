/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.Cars;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface CarsFacadeLocal {

    void create(Cars cars);

    void edit(Cars cars);

    void remove(Cars cars);

    Cars find(Object id);

    List<Cars> findAll();

    List<Cars> findRange(int[] range);

    int count();

    public List<Cars> findNewCars(int records, int page);

    public List<Cars> findLowPriceCars(int records, int page);

    public List<Cars> findNewCarsByType(int typeId, int records, int page);

    public List<Cars> findNewCarsByModel(int modelId, int records, int page);

    public int countNewCarsByModel(int modelId);

    public int countNewCarsByType(int typeId);

    public int countNewCars();
    
}
