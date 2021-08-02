/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "Destinations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Destinations.findAll", query = "SELECT d FROM Destinations d")
    , @NamedQuery(name = "Destinations.findByDestinationId", query = "SELECT d FROM Destinations d WHERE d.destinationId = :destinationId")
    , @NamedQuery(name = "Destinations.findByDestinationName", query = "SELECT d FROM Destinations d WHERE d.destinationName = :destinationName")
    , @NamedQuery(name = "Destinations.findByThumbnail", query = "SELECT d FROM Destinations d WHERE d.thumbnail = :thumbnail")})
public class Destinations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DestinationId")
    private Integer destinationId;
    @Column(name = "DestinationName")
    private String destinationName;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "Description")
    private String description;
    @Column(name = "Thumbnail")
    private String thumbnail;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "destinations")
    private Collection<DestinationSchedules> destinationSchedulesCollection;
    @JoinColumn(name = "TypeId", referencedColumnName = "TypeId")
    @ManyToOne
    private TourTypes typeId;
    @JoinColumn(name = "TownId", referencedColumnName = "TownId")
    @ManyToOne
    private Towns townId;
    @OneToMany(mappedBy = "destinationId")
    private Collection<DestinationImages> destinationImagesCollection;

    public Destinations() {
    }

    public Integer getDestinationId() {
        return destinationId;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @XmlTransient
    public Collection<DestinationSchedules> getDestinationSchedulesCollection() {
        return destinationSchedulesCollection;
    }

    public void setDestinationSchedulesCollection(Collection<DestinationSchedules> destinationSchedulesCollection) {
        this.destinationSchedulesCollection = destinationSchedulesCollection;
    }

    public TourTypes getTypeId() {
        return typeId;
    }

    public void setTypeId(TourTypes typeId) {
        this.typeId = typeId;
    }

    public Towns getTownId() {
        return townId;
    }

    public void setTownId(Towns townId) {
        this.townId = townId;
    }

    @XmlTransient
    public Collection<DestinationImages> getDestinationImagesCollection() {
        return destinationImagesCollection;
    }

    public void setDestinationImagesCollection(Collection<DestinationImages> destinationImagesCollection) {
        this.destinationImagesCollection = destinationImagesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (destinationId != null ? destinationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Destinations)) {
            return false;
        }
        Destinations other = (Destinations) object;
        if ((this.destinationId == null && other.destinationId != null) || (this.destinationId != null && !this.destinationId.equals(other.destinationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.Destinations[ destinationId=" + destinationId + " ]";
    }
    
}
