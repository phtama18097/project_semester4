/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "Flights")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Flights.findAll", query = "SELECT f FROM Flights f")
    , @NamedQuery(name = "Flights.findByFlightId", query = "SELECT f FROM Flights f WHERE f.flightId = :flightId")
    , @NamedQuery(name = "Flights.findByFlightName", query = "SELECT f FROM Flights f WHERE f.flightName = :flightName")
    , @NamedQuery(name = "Flights.findByDepartureTime", query = "SELECT f FROM Flights f WHERE f.departureTime = :departureTime")
    , @NamedQuery(name = "Flights.findByStatus", query = "SELECT f FROM Flights f WHERE f.status = :status")})
public class Flights implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FlightId")
    private Integer flightId;
    @Size(max = 50)
    @Column(name = "FlightName")
    private String flightName;
    @Column(name = "DepartureTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date departureTime;
    @Column(name = "Status")
    private Short status;
    @JoinTable(name = "FlightSchedules", joinColumns = {
        @JoinColumn(name = "FlightId", referencedColumnName = "FlightId")}, inverseJoinColumns = {
        @JoinColumn(name = "TourId", referencedColumnName = "TourId")})
    @ManyToMany
    private Collection<Tours> toursCollection;
    @JoinColumn(name = "AirlineId", referencedColumnName = "AirlineId")
    @ManyToOne
    private Airlines airlineId;
    @JoinColumn(name = "TownFrom", referencedColumnName = "TownId")
    @ManyToOne
    private Towns townFrom;
    @JoinColumn(name = "TownTo", referencedColumnName = "TownId")
    @ManyToOne
    private Towns townTo;

    public Flights() {
    }

    public Integer getFlightId() {
        return flightId;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<Tours> getToursCollection() {
        return toursCollection;
    }

    public void setToursCollection(Collection<Tours> toursCollection) {
        this.toursCollection = toursCollection;
    }

    public Airlines getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(Airlines airlineId) {
        this.airlineId = airlineId;
    }

    public Towns getTownFrom() {
        return townFrom;
    }

    public void setTownFrom(Towns townFrom) {
        this.townFrom = townFrom;
    }

    public Towns getTownTo() {
        return townTo;
    }

    public void setTownTo(Towns townTo) {
        this.townTo = townTo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (flightId != null ? flightId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Flights)) {
            return false;
        }
        Flights other = (Flights) object;
        if ((this.flightId == null && other.flightId != null) || (this.flightId != null && !this.flightId.equals(other.flightId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.Flights[ flightId=" + flightId + " ]";
    }
    
}
