/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.Restaurants;
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
public class RestaurantsFacade extends AbstractFacade<Restaurants> implements RestaurantsFacadeLocal {

    @PersistenceContext(unitName = "East2WestToursAndTravels-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public List<Restaurants> findRestaurantsForTour(int id){
        Query query = em.createQuery("SELECT r FROM Restaurants r WHERE r.restaurantId NOT IN (SELECT s.restaurantSchedulesPK.restaurantId FROM RestaurantSchedules s WHERE s.tours.tourId = ?1)");
        query.setParameter(1, id);
        return query.getResultList();
    }
        
    @Override
    public int countSchedule(int id){
        Query query = em.createQuery("SELECT r FROM RestaurantSchedules r WHERE r.restaurantSchedulesPK.tourId = ?1");
        query.setParameter(1, id);
        return query.getResultList().size();  
    }

    public RestaurantsFacade() {
        super(Restaurants.class);
    }
    
}
