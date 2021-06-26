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
@Table(name = "AccommodationSchedules")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccommodationSchedules.findAll", query = "SELECT a FROM AccommodationSchedules a")
    , @NamedQuery(name = "AccommodationSchedules.findByTourId", query = "SELECT a FROM AccommodationSchedules a WHERE a.accommodationSchedulesPK.tourId = :tourId")
    , @NamedQuery(name = "AccommodationSchedules.findByAccommodationId", query = "SELECT a FROM AccommodationSchedules a WHERE a.accommodationSchedulesPK.accommodationId = :accommodationId")
    , @NamedQuery(name = "AccommodationSchedules.findByVisitDate", query = "SELECT a FROM AccommodationSchedules a WHERE a.visitDate = :visitDate")})
public class AccommodationSchedules implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AccommodationSchedulesPK accommodationSchedulesPK;
    @Column(name = "VisitDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date visitDate;
    @JoinColumn(name = "AccommodationId", referencedColumnName = "AccommodationId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Accommodations accommodations;
    @JoinColumn(name = "TourId", referencedColumnName = "TourId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tours tours;

    public AccommodationSchedules() {
    }

    public AccommodationSchedules(AccommodationSchedulesPK accommodationSchedulesPK) {
        this.accommodationSchedulesPK = accommodationSchedulesPK;
    }

    public AccommodationSchedules(int tourId, int accommodationId) {
        this.accommodationSchedulesPK = new AccommodationSchedulesPK(tourId, accommodationId);
    }

    public AccommodationSchedulesPK getAccommodationSchedulesPK() {
        return accommodationSchedulesPK;
    }

    public void setAccommodationSchedulesPK(AccommodationSchedulesPK accommodationSchedulesPK) {
        this.accommodationSchedulesPK = accommodationSchedulesPK;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public Accommodations getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(Accommodations accommodations) {
        this.accommodations = accommodations;
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
        hash += (accommodationSchedulesPK != null ? accommodationSchedulesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccommodationSchedules)) {
            return false;
        }
        AccommodationSchedules other = (AccommodationSchedules) object;
        if ((this.accommodationSchedulesPK == null && other.accommodationSchedulesPK != null) || (this.accommodationSchedulesPK != null && !this.accommodationSchedulesPK.equals(other.accommodationSchedulesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.AccommodationSchedules[ accommodationSchedulesPK=" + accommodationSchedulesPK + " ]";
    }
    
}
