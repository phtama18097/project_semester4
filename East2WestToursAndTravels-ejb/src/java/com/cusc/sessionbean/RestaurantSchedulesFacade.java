/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.RestaurantSchedules;
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
public class RestaurantSchedulesFacade extends AbstractFacade<RestaurantSchedules> implements RestaurantSchedulesFacadeLocal {

    @PersistenceContext(unitName = "East2WestToursAndTravels-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public List<RestaurantSchedules> findRestaurantsOfTour(int tourID){
        Query query = em.createQuery("SELECT d FROM RestaurantSchedules d WHERE d.tours.tourId = ?1");
        query.setParameter(1, tourID);
        return query.getResultList();
    }

    public RestaurantSchedulesFacade() {
        super(RestaurantSchedules.class);
    }
    
}
