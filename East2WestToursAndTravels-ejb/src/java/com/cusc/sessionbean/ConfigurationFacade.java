/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.Configuration;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
@Stateless
public class ConfigurationFacade extends AbstractFacade<Configuration> implements ConfigurationFacadeLocal {

    @PersistenceContext(unitName = "East2WestToursAndTravels-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConfigurationFacade() {
        super(Configuration.class);
    }
    
    
    @Override
    public Configuration findByConfigName(String configName){
        Query query = em.createQuery("SELECT c FROM Configuration c WHERE c.configName = ?1");
        query.setParameter(1, configName);
        List<Configuration> list = query.getResultList();
        if(list == null){
            return null;
        }else{
            return list.get(0);
        }
    }
    
}
