/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.Accommodations;
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
public class AccommodationsFacade extends AbstractFacade<Accommodations> implements AccommodationsFacadeLocal {

    @PersistenceContext(unitName = "East2WestToursAndTravels-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public List<Accommodations> findAccommodationsForTour(int id){
        Query query = em.createQuery("SELECT r FROM Accommodations r WHERE r.accommodationId NOT IN (SELECT s.accommodationSchedulesPK.accommodationId FROM AccommodationSchedules s WHERE s.tours.tourId = ?1)");
        query.setParameter(1, id);
        return query.getResultList();
    }
    
    @Override
    public int countSchedule(int id){
        Query query = em.createQuery("SELECT r FROM AccommodationSchedules r WHERE r.accommodationSchedulesPK.tourId = ?1");
        query.setParameter(1, id);
        return query.getResultList().size();  
    }
    
    public AccommodationsFacade() {
        super(Accommodations.class);
    }
    
}
