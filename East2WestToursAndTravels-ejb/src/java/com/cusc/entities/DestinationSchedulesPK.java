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
public class DestinationSchedulesPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "PackageId")
    private int packageId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DestinationId")
    private int destinationId;

    public DestinationSchedulesPK() {
    }

    public DestinationSchedulesPK(int packageId, int destinationId) {
        this.packageId = packageId;
        this.destinationId = destinationId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) packageId;
        hash += (int) destinationId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DestinationSchedulesPK)) {
            return false;
        }
        DestinationSchedulesPK other = (DestinationSchedulesPK) object;
        if (this.packageId != other.packageId) {
            return false;
        }
        if (this.destinationId != other.destinationId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.DestinationSchedulesPK[ packageId=" + packageId + ", destinationId=" + destinationId + " ]";
    }
    
}
