/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "Towns")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Towns.findAll", query = "SELECT t FROM Towns t")
    , @NamedQuery(name = "Towns.findByTownId", query = "SELECT t FROM Towns t WHERE t.townId = :townId")
    , @NamedQuery(name = "Towns.findByTownName", query = "SELECT t FROM Towns t WHERE t.townName = :townName")})
public class Towns implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TownId")
    private Integer townId;
    @Column(name = "TownName")
    private String townName;
    @OneToMany(mappedBy = "townId")
    private Collection<Destinations> destinationsCollection;
    @OneToMany(mappedBy = "townFrom")
    private Collection<Flights> flightsCollection;
    @OneToMany(mappedBy = "townTo")
    private Collection<Flights> flightsCollection1;
    @OneToMany(mappedBy = "townId")
    private Collection<Accommodations> accommodationsCollection;
    @OneToMany(mappedBy = "townId")
    private Collection<Restaurants> restaurantsCollection;

    public Towns() {
    }

    public Integer getTownId() {
        return townId;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    @XmlTransient
    public Collection<Destinations> getDestinationsCollection() {
        return destinationsCollection;
    }

    public void setDestinationsCollection(Collection<Destinations> destinationsCollection) {
        this.destinationsCollection = destinationsCollection;
    }

    @XmlTransient
    public Collection<Flights> getFlightsCollection() {
        return flightsCollection;
    }

    public void setFlightsCollection(Collection<Flights> flightsCollection) {
        this.flightsCollection = flightsCollection;
    }

    @XmlTransient
    public Collection<Flights> getFlightsCollection1() {
        return flightsCollection1;
    }

    public void setFlightsCollection1(Collection<Flights> flightsCollection1) {
        this.flightsCollection1 = flightsCollection1;
    }

    @XmlTransient
    public Collection<Accommodations> getAccommodationsCollection() {
        return accommodationsCollection;
    }

    public void setAccommodationsCollection(Collection<Accommodations> accommodationsCollection) {
        this.accommodationsCollection = accommodationsCollection;
    }

    @XmlTransient
    public Collection<Restaurants> getRestaurantsCollection() {
        return restaurantsCollection;
    }

    public void setRestaurantsCollection(Collection<Restaurants> restaurantsCollection) {
        this.restaurantsCollection = restaurantsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (townId != null ? townId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Towns)) {
            return false;
        }
        Towns other = (Towns) object;
        if ((this.townId == null && other.townId != null) || (this.townId != null && !this.townId.equals(other.townId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.Towns[ townId=" + townId + " ]";
    }
    
}
