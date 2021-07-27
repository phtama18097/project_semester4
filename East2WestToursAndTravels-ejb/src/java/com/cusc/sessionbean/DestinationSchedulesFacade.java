/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.DestinationSchedules;
import com.cusc.entities.Destinations;
import com.cusc.entities.Tours;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
@Stateless
public class DestinationSchedulesFacade extends AbstractFacade<DestinationSchedules> implements DestinationSchedulesFacadeLocal {

    @PersistenceContext(unitName = "East2WestToursAndTravels-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public int countToursByDestinationType(int typeId){
        Query query = em.createQuery("SELECT k FROM DestinationSchedules s, TourPackages p, Tours k, Destinations d, TourTypes t WHERE"
                + " p.packageId = s.tourPackages.packageId AND "
                + "p.packageId = k.packageId.packageId AND "
                + "s.destinations.destinationId = d.destinationId AND "
                + "d.typeId.typeId = t.typeId "
                + "AND t.typeId = ?1 AND "
                + "k.status = 1", Tours.class);
        query.setParameter(1, typeId);
        return query.getResultList() == null ? 0 : query.getResultList().size();
    }
    
    @Override
    public List<Tours> findToursByDestinationType(int typeId, int record, int page){
        Query query = em.createQuery("SELECT k FROM DestinationSchedules s, TourPackages p, Tours k, Destinations d, TourTypes t WHERE"
                + " p.packageId = s.tourPackages.packageId AND "
                + "p.packageId = k.packageId.packageId AND "
                + "s.destinations.destinationId = d.destinationId AND "
                + "d.typeId.typeId = t.typeId "
                + "AND t.typeId = ?1 AND "
                + "k.status = 1 ORDER BY k.tourId DESC", Tours.class);
        query.setParameter(1, typeId);
        query.setMaxResults(record);
        query.setFirstResult((page-1)*record);
        return query.getResultList();
    }
     
    @Override
    public List<DestinationSchedules> find4Schedules(int packageID){
        Query query = em.createQuery("SELECT d FROM DestinationSchedules d WHERE d.tourPackages.packageId = ?1");
        query.setParameter(1, packageID);
        query.setMaxResults(4);
        return query.getResultList();
    }
    
    @Override
    public List<DestinationSchedules> findDestinations(int packageID){
        Query query = em.createQuery("SELECT d FROM DestinationSchedules d WHERE d.tourPackages.packageId = ?1");
        query.setParameter(1, packageID);
        return query.getResultList();
    }

    public DestinationSchedulesFacade() {
        super(DestinationSchedules.class);
    }
    
}
