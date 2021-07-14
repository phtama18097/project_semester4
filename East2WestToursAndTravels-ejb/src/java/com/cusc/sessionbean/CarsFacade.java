/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.Cars;
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
public class CarsFacade extends AbstractFacade<Cars> implements CarsFacadeLocal {

    @PersistenceContext(unitName = "East2WestToursAndTravels-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    
    @Override
    public List<Cars> findNewCars(int records, int page){
        Query query = em.createQuery("SELECT c FROM Cars c WHERE c.status = 1 ORDER BY c.carId DESC");
        query.setMaxResults(records);
        query.setFirstResult((page-1)*records);
        return query.getResultList();
    }
    
    public CarsFacade() {
        super(Cars.class);
    }
    
}
