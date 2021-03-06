/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.PaymentMethods;
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
public class PaymentMethodsFacade extends AbstractFacade<PaymentMethods> implements PaymentMethodsFacadeLocal {

    @PersistenceContext(unitName = "East2WestToursAndTravels-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public List<PaymentMethods> findActivePaymentMethods(){
        Query query = em.createQuery("SELECT p FROM PaymentMethods p WHERE p.status = ?1", PaymentMethods.class);
        query.setParameter(1, true);
        return query.getResultList();
    }

    public PaymentMethodsFacade() {
        super(PaymentMethods.class);
    }
    
}
