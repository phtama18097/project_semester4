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
@Table(name = "PaymentMethods")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaymentMethods.findAll", query = "SELECT p FROM PaymentMethods p")
    , @NamedQuery(name = "PaymentMethods.findByMethodId", query = "SELECT p FROM PaymentMethods p WHERE p.methodId = :methodId")
    , @NamedQuery(name = "PaymentMethods.findByMethodName", query = "SELECT p FROM PaymentMethods p WHERE p.methodName = :methodName")
    , @NamedQuery(name = "PaymentMethods.findByStatus", query = "SELECT p FROM PaymentMethods p WHERE p.status = :status")})
public class PaymentMethods implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MethodId")
    private Integer methodId;
    @Size(max = 50)
    @Column(name = "MethodName")
    private String methodName;
    @Column(name = "Status")
    private Boolean status;
    @OneToMany(mappedBy = "methodId")
    private Collection<CarRegistration> carRegistrationCollection;
    @OneToMany(mappedBy = "methodId")
    private Collection<TourRegistration> tourRegistrationCollection;

    public PaymentMethods() {
    }

    public Integer getMethodId() {
        return methodId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<CarRegistration> getCarRegistrationCollection() {
        return carRegistrationCollection;
    }

    public void setCarRegistrationCollection(Collection<CarRegistration> carRegistrationCollection) {
        this.carRegistrationCollection = carRegistrationCollection;
    }

    @XmlTransient
    public Collection<TourRegistration> getTourRegistrationCollection() {
        return tourRegistrationCollection;
    }

    public void setTourRegistrationCollection(Collection<TourRegistration> tourRegistrationCollection) {
        this.tourRegistrationCollection = tourRegistrationCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (methodId != null ? methodId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentMethods)) {
            return false;
        }
        PaymentMethods other = (PaymentMethods) object;
        if ((this.methodId == null && other.methodId != null) || (this.methodId != null && !this.methodId.equals(other.methodId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.PaymentMethods[ methodId=" + methodId + " ]";
    }
    
}
