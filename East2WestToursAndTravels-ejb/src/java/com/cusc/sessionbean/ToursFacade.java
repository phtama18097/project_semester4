/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

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
public class ToursFacade extends AbstractFacade<Tours> implements ToursFacadeLocal {

    @PersistenceContext(unitName = "East2WestToursAndTravels-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Tours> findNewTours(int records, int page){
        Query query = em.createQuery("SELECT t FROM Tours t WHERE t.status = 1 ORDER BY t.tourId DESC");
        query.setMaxResults(records);
        query.setFirstResult((page-1)*records);
        return query.getResultList();
    }
    
    @Override
    public List<Tours> findLowPriceTours(int records, int page){
        Query query = em.createQuery("SELECT t FROM Tours t WHERE t.status = 1 ORDER BY t.unitPrice ASC");
        query.setMaxResults(records);
        query.setFirstResult((page-1)*records);
        return query.getResultList();
    }
    
    @Override
    public List<Tours> findToursByEmployee(int employeeID){
        Query query = em.createQuery("SELECT t FROM Tours t WHERE t.employeeId.employeeId = ?1");
        query.setParameter(1, employeeID);
        return query.getResultList();
    }
        
    public ToursFacade() {
        super(Tours.class);
    }
    
}
