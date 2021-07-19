/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.CarModels;
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
public class CarModelsFacade extends AbstractFacade<CarModels> implements CarModelsFacadeLocal {

    @PersistenceContext(unitName = "East2WestToursAndTravels-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CarModelsFacade() {
        super(CarModels.class);
    }

    @Override
    public List<Cars> carCount(int modelId) {
        Query query = em.createQuery("SELECT c FROM Cars c WHERE c.modelId.modelId = :modelId AND c.status = 1");
        query.setParameter("modelId", modelId);
        return query.getResultList();
                //query.getSingleResult() != null ? Integer.parseInt(query.getSingleResult().toString()) : 0;
    }
    
}
