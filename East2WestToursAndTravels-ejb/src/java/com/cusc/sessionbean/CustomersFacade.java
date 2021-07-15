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

    public CustomersFacade() {
        super(Customers.class);
    }
    @Override
     public boolean checkLoginCustomer(String username, String password) {
        Query query = em.createQuery("SELECT c FROM Customers c  WHERE c.username = :username AND c.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
       List<Customers> list = query.getResultList();
       return list.size()>0;
    }
}
