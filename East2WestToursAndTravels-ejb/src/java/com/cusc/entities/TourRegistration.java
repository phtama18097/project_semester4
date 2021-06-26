/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "TourRegistration")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TourRegistration.findAll", query = "SELECT t FROM TourRegistration t")
    , @NamedQuery(name = "TourRegistration.findByRegistrationId", query = "SELECT t FROM TourRegistration t WHERE t.registrationId = :registrationId")
    , @NamedQuery(name = "TourRegistration.findByRegistrationDate", query = "SELECT t FROM TourRegistration t WHERE t.registrationDate = :registrationDate")
    , @NamedQuery(name = "TourRegistration.findByTotal", query = "SELECT t FROM TourRegistration t WHERE t.total = :total")
    , @NamedQuery(name = "TourRegistration.findByQuantity", query = "SELECT t FROM TourRegistration t WHERE t.quantity = :quantity")
    , @NamedQuery(name = "TourRegistration.findByNote", query = "SELECT t FROM TourRegistration t WHERE t.note = :note")
    , @NamedQuery(name = "TourRegistration.findByStatus", query = "SELECT t FROM TourRegistration t WHERE t.status = :status")})
public class TourRegistration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RegistrationId")
    private Integer registrationId;
    @Column(name = "RegistrationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;
    @Column(name = "Total")
    private BigInteger total;
    @Column(name = "Quantity")
    private Integer quantity;
    @Size(max = 200)
    @Column(name = "Note")
    private String note;
    @Column(name = "Status")
    private Short status;
    @JoinColumn(name = "CustomerId", referencedColumnName = "CustomerId")
    @ManyToOne
    private Customers customerId;
    @JoinColumn(name = "MethodId", referencedColumnName = "MethodId")
    @ManyToOne
    private PaymentMethods methodId;
    @JoinColumn(name = "TourId", referencedColumnName = "TourId")
    @ManyToOne
    private Tours tourId;

    public TourRegistration() {
    }

    public TourRegistration(Integer registrationId) {
        this.registrationId = registrationId;
    }

    public Integer getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Integer registrationId) {
        this.registrationId = registrationId;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public BigInteger getTotal() {
        return total;
    }

    public void setTotal(BigInteger total) {
        this.total = total;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Customers getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customers customerId) {
        this.customerId = customerId;
    }

    public PaymentMethods getMethodId() {
        return methodId;
    }

    public void setMethodId(PaymentMethods methodId) {
        this.methodId = methodId;
    }

    public Tours getTourId() {
        return tourId;
    }

    public void setTourId(Tours tourId) {
        this.tourId = tourId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (registrationId != null ? registrationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TourRegistration)) {
            return false;
        }
        TourRegistration other = (TourRegistration) object;
        if ((this.registrationId == null && other.registrationId != null) || (this.registrationId != null && !this.registrationId.equals(other.registrationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.TourRegistration[ registrationId=" + registrationId + " ]";
    }
    
}
