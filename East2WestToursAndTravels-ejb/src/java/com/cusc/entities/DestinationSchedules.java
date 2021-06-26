/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "DestinationSchedules")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DestinationSchedules.findAll", query = "SELECT d FROM DestinationSchedules d")
    , @NamedQuery(name = "DestinationSchedules.findByPackageId", query = "SELECT d FROM DestinationSchedules d WHERE d.destinationSchedulesPK.packageId = :packageId")
    , @NamedQuery(name = "DestinationSchedules.findByDestinationId", query = "SELECT d FROM DestinationSchedules d WHERE d.destinationSchedulesPK.destinationId = :destinationId")
    , @NamedQuery(name = "DestinationSchedules.findByDayQuantity", query = "SELECT d FROM DestinationSchedules d WHERE d.dayQuantity = :dayQuantity")})
public class DestinationSchedules implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DestinationSchedulesPK destinationSchedulesPK;
    @Column(name = "DayQuantity")
    private Short dayQuantity;
    @JoinColumn(name = "DestinationId", referencedColumnName = "DestinationId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Destinations destinations;
    @JoinColumn(name = "PackageId", referencedColumnName = "PackageId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TourPackages tourPackages;

    public DestinationSchedules() {
    }

    public DestinationSchedules(DestinationSchedulesPK destinationSchedulesPK) {
        this.destinationSchedulesPK = destinationSchedulesPK;
    }

    public DestinationSchedules(int packageId, int destinationId) {
        this.destinationSchedulesPK = new DestinationSchedulesPK(packageId, destinationId);
    }

    public DestinationSchedulesPK getDestinationSchedulesPK() {
        return destinationSchedulesPK;
    }

    public void setDestinationSchedulesPK(DestinationSchedulesPK destinationSchedulesPK) {
        this.destinationSchedulesPK = destinationSchedulesPK;
    }

    public Short getDayQuantity() {
        return dayQuantity;
    }

    public void setDayQuantity(Short dayQuantity) {
        this.dayQuantity = dayQuantity;
    }

    public Destinations getDestinations() {
        return destinations;
    }

    public void setDestinations(Destinations destinations) {
        this.destinations = destinations;
    }

    public TourPackages getTourPackages() {
        return tourPackages;
    }

    public void setTourPackages(TourPackages tourPackages) {
        this.tourPackages = tourPackages;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (destinationSchedulesPK != null ? destinationSchedulesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DestinationSchedules)) {
            return false;
        }
        DestinationSchedules other = (DestinationSchedules) object;
        if ((this.destinationSchedulesPK == null && other.destinationSchedulesPK != null) || (this.destinationSchedulesPK != null && !this.destinationSchedulesPK.equals(other.destinationSchedulesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.DestinationSchedules[ destinationSchedulesPK=" + destinationSchedulesPK + " ]";
    }
    
}
