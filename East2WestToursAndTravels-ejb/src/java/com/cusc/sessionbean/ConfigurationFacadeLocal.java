/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.Configuration;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface ConfigurationFacadeLocal {

    void create(Configuration configuration);

    void edit(Configuration configuration);

    void remove(Configuration configuration);

    Configuration find(Object id);

    List<Configuration> findAll();

    List<Configuration> findRange(int[] range);

    int count();

    public Configuration findByConfigName(String configName);
    
}
