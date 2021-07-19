/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.CarImages;
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
    public List<Cars> findAllAvai(int record, int page);

    public List<Cars> findByModel(int modelId, int record, int page);

    public List<Cars> findByType(int typeId, int record, int page);

    public List<Cars> carOther(int typeId, int modelId, int carId);

    public List<CarImages> showImage(int carId, String thumbnail);

    public int countAllCars();

    
}
