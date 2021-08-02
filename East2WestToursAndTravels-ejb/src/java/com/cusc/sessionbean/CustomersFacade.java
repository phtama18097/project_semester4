/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.Customers;
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
public class CustomersFacade extends AbstractFacade<Customers> implements CustomersFacadeLocal {

    @PersistenceContext(unitName = "East2WestToursAndTravels-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Customers login(String username, String password){
        Query query = em.createQuery("SELECT u FROM Customers u WHERE u.username = ?1 AND u.password = ?2");
        query.setParameter(1, username);
        query.setParameter(2, password);
        List<Customers> list = query.getResultList();
        return list.size() > 0 ? list.get(0) : null;
    }
    
    @Override
    public boolean validateEmail(String email){
        Query query = em.createQuery("SELECT u FROM Customers u WHERE u.email = ?1");
        query.setParameter(1, email);
        return query.getResultList().size() > 0;
    }
    
    @Override
    public boolean validateUsername(String username){
        Query query = em.createQuery("SELECT u FROM Customers u WHERE u.username = ?1");
        query.setParameter(1, username);
        return query.getResultList().size() > 0;
    }
    
    @Override
    public boolean validateExistedUsername(String newUsername, String existedUsername){
        Query query = em.createQuery("SELECT u FROM Customers u WHERE u.username NOT IN (SELECT c.username FROM Customers c WHERE c.username = ?1) AND u.username = ?2");
        query.setParameter(1, existedUsername);
        query.setParameter(2, newUsername);
        return query.getResultList().size() > 0;
    }
    
    @Override
    public boolean validateExistedEmail(String newEmail, String existedEmail){
        Query query = em.createQuery("SELECT u FROM Customers u WHERE u.email NOT IN (SELECT c.email FROM Customers c WHERE c.email = ?1) AND u.email = ?2");
        query.setParameter(1, existedEmail);
        query.setParameter(2, newEmail);
        return query.getResultList().size() > 0;
    }
    
    public CustomersFacade() {
        super(Customers.class);
    }
    
}
