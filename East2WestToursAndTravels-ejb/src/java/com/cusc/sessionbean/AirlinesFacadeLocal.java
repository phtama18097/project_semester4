/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.Airlines;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface AirlinesFacadeLocal {

    void create(Airlines airlines);

    void edit(Airlines airlines);

    void remove(Airlines airlines);

    Airlines find(Object id);

    List<Airlines> findAll();

    List<Airlines> findRange(int[] range);

    int count();
    
}
