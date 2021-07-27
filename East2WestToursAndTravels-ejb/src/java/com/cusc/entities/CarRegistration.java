/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CarRegistration")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CarRegistration.findAll", query = "SELECT c FROM CarRegistration c")
    , @NamedQuery(name = "CarRegistration.findByRegistrationId", query = "SELECT c FROM CarRegistration c WHERE c.registrationId = :registrationId")
    , @NamedQuery(name = "CarRegistration.findByRegistrationDate", query = "SELECT c FROM CarRegistration c WHERE c.registrationDate = :registrationDate")
    , @NamedQuery(name = "CarRegistration.findByReturnDate", query = "SELECT c FROM CarRegistration c WHERE c.returnDate = :returnDate")
    , @NamedQuery(name = "CarRegistration.findByTotal", query = "SELECT c FROM CarRegistration c WHERE c.total = :total")
    , @NamedQuery(name = "CarRegistration.findByNote", query = "SELECT c FROM CarRegistration c WHERE c.note = :note")
    , @NamedQuery(name = "CarRegistration.findByStatus", query = "SELECT c FROM CarRegistration c WHERE c.status = :status")})
public class CarRegistration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RegistrationId")
    private Integer registrationId;
    @Column(name = "RegistrationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;
    @Column(name = "ReturnDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;
    @Column(name = "Total")
    private BigInteger total;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "carRegistration")
    private Collection<CarRegistrationDetails> carRegistrationDetailsCollection;

    public CarRegistration() {
    }

    public Integer getRegistrationId() {
        return registrationId;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public BigInteger getTotal() {
        return total;
    }

    public void setTotal(BigInteger total) {
        this.total = total;
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
        hash += (registrationId != null ? registrationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CarRegistration)) {
            return false;
        }
        CarRegistration other = (CarRegistration) object;
        if ((this.registrationId == null && other.registrationId != null) || (this.registrationId != null && !this.registrationId.equals(other.registrationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.CarRegistration[ registrationId=" + registrationId + " ]";
    }
    
}
