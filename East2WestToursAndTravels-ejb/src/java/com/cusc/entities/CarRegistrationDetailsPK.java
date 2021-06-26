/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Admin
 */
@Embeddable
public class CarRegistrationDetailsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "RegistrationId")
    private int registrationId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CarId")
    private int carId;

    public CarRegistrationDetailsPK() {
    }

    public CarRegistrationDetailsPK(int registrationId, int carId) {
        this.registrationId = registrationId;
        this.carId = carId;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) registrationId;
        hash += (int) carId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CarRegistrationDetailsPK)) {
            return false;
        }
        CarRegistrationDetailsPK other = (CarRegistrationDetailsPK) object;
        if (this.registrationId != other.registrationId) {
            return false;
        }
        if (this.carId != other.carId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.CarRegistrationDetailsPK[ registrationId=" + registrationId + ", carId=" + carId + " ]";
    }
    
}
