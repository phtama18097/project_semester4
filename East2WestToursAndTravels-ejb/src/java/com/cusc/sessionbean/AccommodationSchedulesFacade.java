/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.AccommodationSchedules;
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
public class AccommodationSchedulesFacade extends AbstractFacade<AccommodationSchedules> implements AccommodationSchedulesFacadeLocal {

    @PersistenceContext(unitName = "East2WestToursAndTravels-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public List<AccommodationSchedules> findAccommodationsOfTour(int tourID){
        Query query = em.createQuery("SELECT d FROM AccommodationSchedules d WHERE d.tours.tourId = ?1");
        query.setParameter(1, tourID);
        return query.getResultList();
    }

    public AccommodationSchedulesFacade() {
        super(AccommodationSchedules.class);
    }
    
}
