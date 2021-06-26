/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Admin
 */
@Embeddable
public class RestaurantSchedulesPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "TourId")
    private int tourId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RestaurantId")
    private int restaurantId;

    public RestaurantSchedulesPK() {
    }

    public RestaurantSchedulesPK(int tourId, int restaurantId) {
        this.tourId = tourId;
        this.restaurantId = restaurantId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) tourId;
        hash += (int) restaurantId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RestaurantSchedulesPK)) {
            return false;
        }
        RestaurantSchedulesPK other = (RestaurantSchedulesPK) object;
        if (this.tourId != other.tourId) {
            return false;
        }
        if (this.restaurantId != other.restaurantId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.RestaurantSchedulesPK[ tourId=" + tourId + ", restaurantId=" + restaurantId + " ]";
    }
    
}
