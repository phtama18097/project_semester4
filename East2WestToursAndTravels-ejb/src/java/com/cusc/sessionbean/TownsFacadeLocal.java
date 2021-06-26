/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.Towns;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface TownsFacadeLocal {

    void create(Towns towns);

    void edit(Towns towns);

    void remove(Towns towns);

    Towns find(Object id);

    List<Towns> findAll();

    List<Towns> findRange(int[] range);

    int count();
    
}
