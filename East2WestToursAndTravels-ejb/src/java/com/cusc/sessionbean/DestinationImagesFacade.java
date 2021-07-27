/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.DestinationImages;
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
public class DestinationImagesFacade extends AbstractFacade<DestinationImages> implements DestinationImagesFacadeLocal {

    @PersistenceContext(unitName = "East2WestToursAndTravels-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<DestinationImages> findImagesByDestination(int destinationID){
        Query query = em.createQuery("SELECT d FROM DestinationImages d WHERE d.destinationId.destinationId = ?1", DestinationImages.class);
        query.setParameter(1, destinationID);
        return query.getResultList();
    }
    
    @Override
    public List<DestinationImages> findImages(int destinationID, int records, int page){
        Query query = em.createQuery("SELECT d FROM DestinationImages d WHERE d.destinationId.destinationId = ?1", DestinationImages.class);
        query.setParameter(1, destinationID);
        query.setMaxResults(records);
        query.setFirstResult((page-1)*records);
        return query.getResultList();
    }
    
    public DestinationImagesFacade() {
        super(DestinationImages.class);
    }
    
}
