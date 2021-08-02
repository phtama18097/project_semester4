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
@Table(name = "TourTypes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TourTypes.findAll", query = "SELECT t FROM TourTypes t")
    , @NamedQuery(name = "TourTypes.findByTypeId", query = "SELECT t FROM TourTypes t WHERE t.typeId = :typeId")
    , @NamedQuery(name = "TourTypes.findByTypeName", query = "SELECT t FROM TourTypes t WHERE t.typeName = :typeName")})
public class TourTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TypeId")
    private Integer typeId;
    @Column(name = "TypeName")
    private String typeName;
    @OneToMany(mappedBy = "typeId")
    private Collection<Destinations> destinationsCollection;

    public TourTypes() {
    }

    public Integer getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @XmlTransient
    public Collection<Destinations> getDestinationsCollection() {
        return destinationsCollection;
    }

    public void setDestinationsCollection(Collection<Destinations> destinationsCollection) {
        this.destinationsCollection = destinationsCollection;
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
        if (!(object instanceof TourTypes)) {
            return false;
        }
        TourTypes other = (TourTypes) object;
        if ((this.typeId == null && other.typeId != null) || (this.typeId != null && !this.typeId.equals(other.typeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.TourTypes[ typeId=" + typeId + " ]";
    }
    
}
