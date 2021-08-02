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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CarTypes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CarTypes.findAll", query = "SELECT c FROM CarTypes c")
    , @NamedQuery(name = "CarTypes.findByTypeId", query = "SELECT c FROM CarTypes c WHERE c.typeId = :typeId")
    , @NamedQuery(name = "CarTypes.findByDescription", query = "SELECT c FROM CarTypes c WHERE c.description = :description")
    , @NamedQuery(name = "CarTypes.findBySeat", query = "SELECT c FROM CarTypes c WHERE c.seat = :seat")})
public class CarTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TypeId")
    private Integer typeId;
    @Column(name = "Description")
    private String description;
    @Column(name = "Seat")
    private Short seat;
    @OneToMany(mappedBy = "typeId")
    private Collection<Cars> carsCollection;

    public CarTypes() {
    }

    public Integer getTypeId() {
        return typeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getSeat() {
        return seat;
    }

    public void setSeat(Short seat) {
        this.seat = seat;
    }

    @XmlTransient
    public Collection<Cars> getCarsCollection() {
        return carsCollection;
    }

    public void setCarsCollection(Collection<Cars> carsCollection) {
        this.carsCollection = carsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (typeId != null ? typeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CarTypes)) {
            return false;
        }
        CarTypes other = (CarTypes) object;
        if ((this.typeId == null && other.typeId != null) || (this.typeId != null && !this.typeId.equals(other.typeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.CarTypes[ typeId=" + typeId + " ]";
    }
    
}
