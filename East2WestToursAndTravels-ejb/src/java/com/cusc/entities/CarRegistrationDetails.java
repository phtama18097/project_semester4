/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.entities;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "CarRegistrationDetails")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CarRegistrationDetails.findAll", query = "SELECT c FROM CarRegistrationDetails c")
    , @NamedQuery(name = "CarRegistrationDetails.findByRegistrationId", query = "SELECT c FROM CarRegistrationDetails c WHERE c.carRegistrationDetailsPK.registrationId = :registrationId")
    , @NamedQuery(name = "CarRegistrationDetails.findByCarId", query = "SELECT c FROM CarRegistrationDetails c WHERE c.carRegistrationDetailsPK.carId = :carId")
    , @NamedQuery(name = "CarRegistrationDetails.findByQuantity", query = "SELECT c FROM CarRegistrationDetails c WHERE c.quantity = :quantity")
    , @NamedQuery(name = "CarRegistrationDetails.findByUnitPrice", query = "SELECT c FROM CarRegistrationDetails c WHERE c.unitPrice = :unitPrice")})
public class CarRegistrationDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CarRegistrationDetailsPK carRegistrationDetailsPK;
    @Column(name = "Quantity")
    private Integer quantity;
    @Column(name = "UnitPrice")
    private BigInteger unitPrice;
    @JoinColumn(name = "RegistrationId", referencedColumnName = "RegistrationId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CarRegistration carRegistration;
    @JoinColumn(name = "CarId", referencedColumnName = "CarId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cars cars;

    public CarRegistrationDetails() {
    }

    public CarRegistrationDetails(CarRegistrationDetailsPK carRegistrationDetailsPK) {
        this.carRegistrationDetailsPK = carRegistrationDetailsPK;
    }

    public CarRegistrationDetails(int registrationId, int carId) {
        this.carRegistrationDetailsPK = new CarRegistrationDetailsPK(registrationId, carId);
    }

    public CarRegistrationDetailsPK getCarRegistrationDetailsPK() {
        return carRegistrationDetailsPK;
    }

    public void setCarRegistrationDetailsPK(CarRegistrationDetailsPK carRegistrationDetailsPK) {
        this.carRegistrationDetailsPK = carRegistrationDetailsPK;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigInteger getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigInteger unitPrice) {
        this.unitPrice = unitPrice;
    }

    public CarRegistration getCarRegistration() {
        return carRegistration;
    }

    public void setCarRegistration(CarRegistration carRegistration) {
        this.carRegistration = carRegistration;
    }

    public Cars getCars() {
        return cars;
    }

    public void setCars(Cars cars) {
        this.cars = cars;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (carRegistrationDetailsPK != null ? carRegistrationDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CarRegistrationDetails)) {
            return false;
        }
        CarRegistrationDetails other = (CarRegistrationDetails) object;
        if ((this.carRegistrationDetailsPK == null && other.carRegistrationDetailsPK != null) || (this.carRegistrationDetailsPK != null && !this.carRegistrationDetailsPK.equals(other.carRegistrationDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.CarRegistrationDetails[ carRegistrationDetailsPK=" + carRegistrationDetailsPK + " ]";
    }
    
}
