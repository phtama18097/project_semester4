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
@Table(name = "Airlines")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Airlines.findAll", query = "SELECT a FROM Airlines a")
    , @NamedQuery(name = "Airlines.findByAirlineId", query = "SELECT a FROM Airlines a WHERE a.airlineId = :airlineId")
    , @NamedQuery(name = "Airlines.findByAirlineName", query = "SELECT a FROM Airlines a WHERE a.airlineName = :airlineName")})
public class Airlines implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AirlineId")
    private Integer airlineId;
    @Size(max = 50)
    @Column(name = "AirlineName")
    private String airlineName;
    @OneToMany(mappedBy = "airlineId")
    private Collection<Flights> flightsCollection;

    public Airlines() {
    }

    public Integer getAirlineId() {
        return airlineId;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    @XmlTransient
    public Collection<Flights> getFlightsCollection() {
        return flightsCollection;
    }

    public void setFlightsCollection(Collection<Flights> flightsCollection) {
        this.flightsCollection = flightsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (airlineId != null ? airlineId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Airlines)) {
            return false;
        }
        Airlines other = (Airlines) object;
        if ((this.airlineId == null && other.airlineId != null) || (this.airlineId != null && !this.airlineId.equals(other.airlineId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.Airlines[ airlineId=" + airlineId + " ]";
    }
    
}
