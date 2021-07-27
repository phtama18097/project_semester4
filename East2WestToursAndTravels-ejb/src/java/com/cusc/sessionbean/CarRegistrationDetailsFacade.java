/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.CarRegistrationDetails;
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
public class CarRegistrationDetailsFacade extends AbstractFacade<CarRegistrationDetails> implements CarRegistrationDetailsFacadeLocal {

    @PersistenceContext(unitName = "East2WestToursAndTravels-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<CarRegistrationDetails> findDetailsByRegistration(int registrationId){
        Query query = em.createNamedQuery("CarRegistrationDetails.findByRegistrationId");
        query.setParameter("registrationId", registrationId);
        return query.getResultList();
    }
    
    public CarRegistrationDetailsFacade() {
        super(CarRegistrationDetails.class);
    }
    
}
