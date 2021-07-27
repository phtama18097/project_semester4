/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.CarRegistration;
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
public class CarRegistrationFacade extends AbstractFacade<CarRegistration> implements CarRegistrationFacadeLocal {

    @PersistenceContext(unitName = "East2WestToursAndTravels-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public List<CarRegistration> findAllOrderByDesc(){
        Query query = em.createQuery("SELECT t FROM CarRegistration t ORDER BY t.registrationId DESC");
        return query.getResultList();
    }
    
    @Override
    public List<CarRegistration> findHistory(int customerId){
        Query query = em.createQuery("SELECT t FROM CarRegistration t WHERE t.customerId.customerId = ?1 ORDER BY t.registrationId DESC");
        query.setParameter(1, customerId);
        return query.getResultList();
    }

    public CarRegistrationFacade() {
        super(CarRegistration.class);
    }
    
}
