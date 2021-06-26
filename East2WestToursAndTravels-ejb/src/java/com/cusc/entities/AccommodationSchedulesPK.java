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
public class AccommodationSchedulesPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "TourId")
    private int tourId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AccommodationId")
    private int accommodationId;

    public AccommodationSchedulesPK() {
    }

    public AccommodationSchedulesPK(int tourId, int accommodationId) {
        this.tourId = tourId;
        this.accommodationId = accommodationId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(int accommodationId) {
        this.accommodationId = accommodationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) tourId;
        hash += (int) accommodationId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccommodationSchedulesPK)) {
            return false;
        }
        AccommodationSchedulesPK other = (AccommodationSchedulesPK) object;
        if (this.tourId != other.tourId) {
            return false;
        }
        if (this.accommodationId != other.accommodationId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.AccommodationSchedulesPK[ tourId=" + tourId + ", accommodationId=" + accommodationId + " ]";
    }
    
}
