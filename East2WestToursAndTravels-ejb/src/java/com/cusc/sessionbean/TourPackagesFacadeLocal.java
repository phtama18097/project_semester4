/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.TourPackages;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface TourPackagesFacadeLocal {

    void create(TourPackages tourPackages);

    void edit(TourPackages tourPackages);

    void remove(TourPackages tourPackages);

    TourPackages find(Object id);

    List<TourPackages> findAll();

    List<TourPackages> findRange(int[] range);

    int count();
    
}
