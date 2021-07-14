/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.Employees;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
@Stateless
public class EmployeesFacade extends AbstractFacade<Employees> implements EmployeesFacadeLocal {

    @PersistenceContext(unitName = "East2WestToursAndTravels-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeesFacade() {
        super(Employees.class);
    }
    @Override
     public boolean checkLoginEmployee(String username, String password) {
        Query query = em.createQuery("SELECT e FROM Employees e  WHERE e.username = :username AND e.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        try {
            query.getSingleResult();
            return true;
        } catch (Exception e) {
            return false;
        }
    } 
}
