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
@Table(name = "Cars")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cars.findAll", query = "SELECT c FROM Cars c")
    , @NamedQuery(name = "Cars.findByCarId", query = "SELECT c FROM Cars c WHERE c.carId = :carId")
    , @NamedQuery(name = "Cars.findByUnitPrice", query = "SELECT c FROM Cars c WHERE c.unitPrice = :unitPrice")
    , @NamedQuery(name = "Cars.findByUnitInStock", query = "SELECT c FROM Cars c WHERE c.unitInStock = :unitInStock")
    , @NamedQuery(name = "Cars.findByUnitOnOrder", query = "SELECT c FROM Cars c WHERE c.unitOnOrder = :unitOnOrder")
    , @NamedQuery(name = "Cars.findByShortDescripiton", query = "SELECT c FROM Cars c WHERE c.shortDescripiton = :shortDescripiton")
    , @NamedQuery(name = "Cars.findByThumbnail", query = "SELECT c FROM Cars c WHERE c.thumbnail = :thumbnail")
    , @NamedQuery(name = "Cars.findByStatus", query = "SELECT c FROM Cars c WHERE c.status = :status")})
public class Cars implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CarId")
    private Integer carId;
    @Column(name = "UnitPrice")
    private BigInteger unitPrice;
    @Column(name = "UnitInStock")
    private Integer unitInStock;
    @Column(name = "UnitOnOrder")
    private Integer unitOnOrder;
    @Size(max = 300)
    @Column(name = "ShortDescripiton")
    private String shortDescripiton;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "Description")
    private String description;
    @Size(max = 100)
    @Column(name = "Thumbnail")
    private String thumbnail;
    @Column(name = "Status")
    private Short status;
    @OneToMany(mappedBy = "carId")
    private Collection<CarImages> carImagesCollection;
    @JoinColumn(name = "ModelId", referencedColumnName = "ModelId")
    @ManyToOne
    private CarModels modelId;
    @JoinColumn(name = "TypeId", referencedColumnName = "TypeId")
    @ManyToOne
    private CarTypes typeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cars")
    private Collection<CarRegistrationDetails> carRegistrationDetailsCollection;

    public Cars() {
    }

    public Integer getCarId() {
        return carId;
    }

    public BigInteger getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigInteger unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getUnitInStock() {
        return unitInStock;
    }

    public void setUnitInStock(Integer unitInStock) {
        this.unitInStock = unitInStock;
    }

    public Integer getUnitOnOrder() {
        return unitOnOrder;
    }

    public void setUnitOnOrder(Integer unitOnOrder) {
        this.unitOnOrder = unitOnOrder;
    }

    public String getShortDescripiton() {
        return shortDescripiton;
    }

    public void setShortDescripiton(String shortDescripiton) {
        this.shortDescripiton = shortDescripiton;
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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<CarImages> getCarImagesCollection() {
        return carImagesCollection;
    }

    public void setCarImagesCollection(Collection<CarImages> carImagesCollection) {
        this.carImagesCollection = carImagesCollection;
    }

    public CarModels getModelId() {
        return modelId;
    }

    public void setModelId(CarModels modelId) {
        this.modelId = modelId;
    }

    public CarTypes getTypeId() {
        return typeId;
    }

    public void setTypeId(CarTypes typeId) {
        this.typeId = typeId;
    }

    @XmlTransient
    public Collection<CarRegistrationDetails> getCarRegistrationDetailsCollection() {
        return carRegistrationDetailsCollection;
    }

    public void setCarRegistrationDetailsCollection(Collection<CarRegistrationDetails> carRegistrationDetailsCollection) {
        this.carRegistrationDetailsCollection = carRegistrationDetailsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (carId != null ? carId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cars)) {
            return false;
        }
        Cars other = (Cars) object;
        if ((this.carId == null && other.carId != null) || (this.carId != null && !this.carId.equals(other.carId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.Cars[ carId=" + carId + " ]";
    }
    
}
