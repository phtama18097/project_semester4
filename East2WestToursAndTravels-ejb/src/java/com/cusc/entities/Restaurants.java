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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "Restaurants")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Restaurants.findAll", query = "SELECT r FROM Restaurants r")
    , @NamedQuery(name = "Restaurants.findByRestaurantId", query = "SELECT r FROM Restaurants r WHERE r.restaurantId = :restaurantId")
    , @NamedQuery(name = "Restaurants.findByRestaurantName", query = "SELECT r FROM Restaurants r WHERE r.restaurantName = :restaurantName")
    , @NamedQuery(name = "Restaurants.findByMinPrice", query = "SELECT r FROM Restaurants r WHERE r.minPrice = :minPrice")
    , @NamedQuery(name = "Restaurants.findByMaxPrice", query = "SELECT r FROM Restaurants r WHERE r.maxPrice = :maxPrice")
    , @NamedQuery(name = "Restaurants.findByThumbnail", query = "SELECT r FROM Restaurants r WHERE r.thumbnail = :thumbnail")})
public class Restaurants implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RestaurantId")
    private Integer restaurantId;
    @Column(name = "RestaurantName")
    private String restaurantName;
    @Lob
    @Column(name = "Description")
    private String description;
    @Column(name = "MinPrice")
    private BigInteger minPrice;
    @Column(name = "MaxPrice")
    private BigInteger maxPrice;
    @Lob
    @Column(name = "Location")
    private String location;
    @Size(max = 100)
    @Column(name = "Thumbnail")
    private String thumbnail;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurants")
    private Collection<RestaurantSchedules> restaurantSchedulesCollection;
    @JoinColumn(name = "TownId", referencedColumnName = "TownId")
    @ManyToOne
    private Towns townId;

    public Restaurants() {
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
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

    @XmlTransient
    public Collection<RestaurantSchedules> getRestaurantSchedulesCollection() {
        return restaurantSchedulesCollection;
    }

    public void setRestaurantSchedulesCollection(Collection<RestaurantSchedules> restaurantSchedulesCollection) {
        this.restaurantSchedulesCollection = restaurantSchedulesCollection;
    }

    public Towns getTownId() {
        return townId;
    }

    public void setTownId(Towns townId) {
        this.townId = townId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (restaurantId != null ? restaurantId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Restaurants)) {
            return false;
        }
        Restaurants other = (Restaurants) object;
        if ((this.restaurantId == null && other.restaurantId != null) || (this.restaurantId != null && !this.restaurantId.equals(other.restaurantId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.Restaurants[ restaurantId=" + restaurantId + " ]";
    }
    
}
