/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.Cars;
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
public class CarsFacade extends AbstractFacade<Cars> implements CarsFacadeLocal {

    @PersistenceContext(unitName = "East2WestToursAndTravels-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    
    @Override
    public List<Cars> findNewCars(int records, int page){
        Query query = em.createQuery("SELECT c FROM Cars c WHERE c.status = 1 ORDER BY c.carId DESC");
        query.setMaxResults(records);
        query.setFirstResult((page-1)*records);
        return query.getResultList();
    }
    
    @Override
    public int countNewCars(){
        Query query = em.createQuery("SELECT c FROM Cars c WHERE c.status = 1");
        return query.getResultList().size();
    }
    
    @Override
    public List<Cars> findLowPriceCars(int records, int page){
        Query query = em.createQuery("SELECT c FROM Cars c WHERE c.status = 1 ORDER BY c.unitPrice ASC");
        query.setMaxResults(records);
        query.setFirstResult((page-1)*records);
        return query.getResultList();
    }
    
    @Override
    public List<Cars> findNewCarsByType(int typeId, int records, int page){
        Query query = em.createQuery("SELECT c FROM Cars c WHERE c.typeId.typeId = ?1 AND c.status = 1 ORDER BY c.carId DESC");
        query.setParameter(1, typeId);
        query.setMaxResults(records);
        query.setFirstResult((page-1)*records);
        return query.getResultList();
    }
    
    @Override
    public int countNewCarsByType(int typeId){
        Query query = em.createQuery("SELECT c FROM Cars c WHERE c.typeId.typeId = ?1 AND c.status = 1");
        query.setParameter(1, typeId);
        return query.getResultList().size();
    }
     
    @Override
    public List<Cars> findNewCarsByModel(int modelId, int records, int page){
        Query query = em.createQuery("SELECT c FROM Cars c WHERE c.modelId.modelId = ?1 AND c.status = 1 ORDER BY c.carId DESC");
        query.setParameter(1, modelId);
        query.setMaxResults(records);
        query.setFirstResult((page-1)*records);
        return query.getResultList();
    }
    
    @Override
    public int countNewCarsByModel(int modelId){
        Query query = em.createQuery("SELECT c FROM Cars c WHERE c.modelId.modelId = ?1 AND c.status = 1");
        query.setParameter(1, modelId);
        return query.getResultList().size();
    }
    
    public CarsFacade() {
        super(Cars.class);
    }
    
}
