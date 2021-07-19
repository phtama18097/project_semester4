/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.CarTypes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
@Stateless
public class CarTypesFacade extends AbstractFacade<CarTypes> implements CarTypesFacadeLocal {

    @PersistenceContext(unitName = "East2WestToursAndTravels-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CarTypesFacade() {
        super(CarTypes.class);
    }
    
    @Override
    public int countCarByType(int typeId) {
        Query query = em.createQuery("SELECT c FROM Cars c WHERE c.typeId.typeId = :typeId AND c.status = 1");
        query.setParameter("typeId", typeId);
        return query.getResultList().size();
    }
    
}
