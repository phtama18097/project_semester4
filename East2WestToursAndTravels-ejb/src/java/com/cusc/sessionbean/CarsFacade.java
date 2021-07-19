/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.sessionbean;

import com.cusc.entities.CarImages;
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

    public CarsFacade() {
        super(Cars.class);
    }

    @Override
    public int count() {
        Query query = em.createQuery("SELECT COUNT(c.carId) FROM Cars c WHERE c.status = 1");
        return query.getSingleResult() != null ? Integer.parseInt(query.getSingleResult().toString()) : 0;
    }

    @Override
    public List<Cars> findAllAvai(int record, int page) {
        Query query = em.createQuery("SELECT c FROM Cars c WHERE c.status = 1 order by c.carId desc");
        query.setMaxResults(record);
        query.setFirstResult((page - 1) * record);
        return query.getResultList();
    }
    
    @Override
    public int countAllCars() {
        Query query = em.createQuery("SELECT c FROM Cars c WHERE c.status = 1");
        return query.getResultList().size();
    }

    @Override
    public List<Cars> findByModel(int modelId, int record, int page) {
        Query query = em.createQuery("SELECT c FROM Cars c WHERE c.status = 1 and c.modelId.modelId = ?1 order by c.carId desc");
        query.setParameter(1, modelId);
        query.setMaxResults(record);
        query.setFirstResult((page - 1) * record);
        return query.getResultList();
    }

    @Override
    public List<Cars> findByType(int typelId, int record, int page) {
        Query query = em.createQuery("SELECT c FROM Cars c WHERE c.status = 1 and c.typeId.typeId = ?1 order by c.carId desc");
        query.setParameter(1, typelId);
        query.setMaxResults(record);
        query.setFirstResult((page - 1) * record);
        return query.getResultList();
    }

    @Override
    public List<Cars> carOther(int typeId, int modelId, int carId) {
        Query query = em.createQuery("SELECT c FROM Cars c WHERE (c.typeId.typeId = ?1 or c.modelId.modelId = ?2) and c.carId !=?3 order by c.carId desc");
        query.setParameter(1, typeId);
        query.setParameter(2, modelId);
        query.setParameter(3, carId);
        return query.getResultList();
    }

    @Override
    public List<CarImages> showImage(int carId, String thumbnail) {
        Query query = em.createQuery("SELECT c FROM CarImages c WHERE c.carId.carId = ?1 and c.fileName != ?2");
        query.setParameter(1, carId);
        query.setParameter(2, thumbnail);
        query.setMaxResults(3);
        return query.getResultList();
    }
}
