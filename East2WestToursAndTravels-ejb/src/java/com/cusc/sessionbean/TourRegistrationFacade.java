/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.TourRegistration;
import com.cusc.entities.Tours;
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
public class TourRegistrationFacade extends AbstractFacade<TourRegistration> implements TourRegistrationFacadeLocal {

    @PersistenceContext(unitName = "East2WestToursAndTravels-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<TourRegistration> findHistory(int customerId){
        Query query = em.createQuery("SELECT t FROM TourRegistration t WHERE t.customerId.customerId = ?1 ORDER BY t.registrationId DESC");
        query.setParameter(1, customerId);
        return query.getResultList();
    }
    
    public TourRegistrationFacade() {
        super(TourRegistration.class);
    }
    
}
