/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.entities;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "Accommodations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accommodations.findAll", query = "SELECT a FROM Accommodations a")
    , @NamedQuery(name = "Accommodations.findByAccommodationId", query = "SELECT a FROM Accommodations a WHERE a.accommodationId = :accommodationId")
    , @NamedQuery(name = "Accommodations.findByAccommodationName", query = "SELECT a FROM Accommodations a WHERE a.accommodationName = :accommodationName")
    , @NamedQuery(name = "Accommodations.findByMinPrice", query = "SELECT a FROM Accommodations a WHERE a.minPrice = :minPrice")
    , @NamedQuery(name = "Accommodations.findByMaxPrice", query = "SELECT a FROM Accommodations a WHERE a.maxPrice = :maxPrice")
    , @NamedQuery(name = "Accommodations.findByThumbnail", query = "SELECT a FROM Accommodations a WHERE a.thumbnail = :thumbnail")})
public class Accommodations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccommodationId")
    private Integer accommodationId;
    @Size(max = 50)
    @Column(name = "AccommodationName")
    private String accommodationName;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "Description")
    private String description;
    @Column(name = "MinPrice")
    private BigInteger minPrice;
    @Column(name = "MaxPrice")
    private BigInteger maxPrice;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "Location")
    private String location;
    @Size(max = 100)
    @Column(name = "Thumbnail")
    private String thumbnail;
    @JoinColumn(name = "TownId", referencedColumnName = "TownId")
    @ManyToOne
    private Towns townId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accommodations")
    private Collection<AccommodationSchedules> accommodationSchedulesCollection;

    public Accommodations() {
    }

    public Integer getAccommodationId() {
        return accommodationId;
    }

    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigInteger getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigInteger minPrice) {
        this.minPrice = minPrice;
    }

    public BigInteger getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigInteger maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Towns getTownId() {
        return townId;
    }

    public void setTownId(Towns townId) {
        this.townId = townId;
    }

    @XmlTransient
    public Collection<AccommodationSchedules> getAccommodationSchedulesCollection() {
        return accommodationSchedulesCollection;
    }

    public void setAccommodationSchedulesCollection(Collection<AccommodationSchedules> accommodationSchedulesCollection) {
        this.accommodationSchedulesCollection = accommodationSchedulesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accommodationId != null ? accommodationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accommodations)) {
            return false;
        }
        Accommodations other = (Accommodations) object;
        if ((this.accommodationId == null && other.accommodationId != null) || (this.accommodationId != null && !this.accommodationId.equals(other.accommodationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.Accommodations[ accommodationId=" + accommodationId + " ]";
    }
    
}
