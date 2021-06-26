/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "RestaurantSchedules")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RestaurantSchedules.findAll", query = "SELECT r FROM RestaurantSchedules r")
    , @NamedQuery(name = "RestaurantSchedules.findByTourId", query = "SELECT r FROM RestaurantSchedules r WHERE r.restaurantSchedulesPK.tourId = :tourId")
    , @NamedQuery(name = "RestaurantSchedules.findByRestaurantId", query = "SELECT r FROM RestaurantSchedules r WHERE r.restaurantSchedulesPK.restaurantId = :restaurantId")
    , @NamedQuery(name = "RestaurantSchedules.findByVisitDate", query = "SELECT r FROM RestaurantSchedules r WHERE r.visitDate = :visitDate")})
public class RestaurantSchedules implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RestaurantSchedulesPK restaurantSchedulesPK;
    @Column(name = "VisitDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date visitDate;
    @JoinColumn(name = "RestaurantId", referencedColumnName = "RestaurantId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Restaurants restaurants;
    @JoinColumn(name = "TourId", referencedColumnName = "TourId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tours tours;

    public RestaurantSchedules() {
    }

    public RestaurantSchedules(RestaurantSchedulesPK restaurantSchedulesPK) {
        this.restaurantSchedulesPK = restaurantSchedulesPK;
    }

    public RestaurantSchedules(int tourId, int restaurantId) {
        this.restaurantSchedulesPK = new RestaurantSchedulesPK(tourId, restaurantId);
    }

    public RestaurantSchedulesPK getRestaurantSchedulesPK() {
        return restaurantSchedulesPK;
    }

    public void setRestaurantSchedulesPK(RestaurantSchedulesPK restaurantSchedulesPK) {
        this.restaurantSchedulesPK = restaurantSchedulesPK;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public Restaurants getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Restaurants restaurants) {
        this.restaurants = restaurants;
    }

    public Tours getTours() {
        return tours;
    }

    public void setTours(Tours tours) {
        this.tours = tours;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (restaurantSchedulesPK != null ? restaurantSchedulesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RestaurantSchedules)) {
            return false;
        }
        RestaurantSchedules other = (RestaurantSchedules) object;
        if ((this.restaurantSchedulesPK == null && other.restaurantSchedulesPK != null) || (this.restaurantSchedulesPK != null && !this.restaurantSchedulesPK.equals(other.restaurantSchedulesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.RestaurantSchedules[ restaurantSchedulesPK=" + restaurantSchedulesPK + " ]";
    }
    
}
