/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.CarImages;
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
public class CarImagesFacade extends AbstractFacade<CarImages> implements CarImagesFacadeLocal {

    @PersistenceContext(unitName = "East2WestToursAndTravels-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public List<CarImages> findImagesByCar(int carID){
        Query query = em.createQuery("SELECT c FROM CarImages c WHERE c.carId.carId = ?1", CarImages.class);
        query.setParameter(1, carID);
        return query.getResultList();
    }
    
    @Override
    public List<CarImages> find4Images(int carID){
        Query query = em.createQuery("SELECT c FROM CarImages c WHERE c.carId.carId = ?1", CarImages.class);
        query.setParameter(1, carID);
        query.setMaxResults(4);
        return query.getResultList();
    }

    public CarImagesFacade() {
        super(CarImages.class);
    }
    
}
