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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "Tours")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tours.findAll", query = "SELECT t FROM Tours t")
    , @NamedQuery(name = "Tours.findByTourId", query = "SELECT t FROM Tours t WHERE t.tourId = :tourId")
    , @NamedQuery(name = "Tours.findByTourName", query = "SELECT t FROM Tours t WHERE t.tourName = :tourName")
    , @NamedQuery(name = "Tours.findByUnitPrice", query = "SELECT t FROM Tours t WHERE t.unitPrice = :unitPrice")
    , @NamedQuery(name = "Tours.findByShortDescription", query = "SELECT t FROM Tours t WHERE t.shortDescription = :shortDescription")
    , @NamedQuery(name = "Tours.findByDepartureDate", query = "SELECT t FROM Tours t WHERE t.departureDate = :departureDate")
    , @NamedQuery(name = "Tours.findByReturnDate", query = "SELECT t FROM Tours t WHERE t.returnDate = :returnDate")
    , @NamedQuery(name = "Tours.findByMinQuantity", query = "SELECT t FROM Tours t WHERE t.minQuantity = :minQuantity")
    , @NamedQuery(name = "Tours.findByMaxQuantity", query = "SELECT t FROM Tours t WHERE t.maxQuantity = :maxQuantity")
    , @NamedQuery(name = "Tours.findByStatus", query = "SELECT t FROM Tours t WHERE t.status = :status")})
public class Tours implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TourId")
    private Integer tourId;
    @Size(max = 120)
    @Column(name = "TourName")
    private String tourName;
    @Column(name = "UnitPrice")
    private BigInteger unitPrice;
    @Size(max = 200)
    @Column(name = "ShortDescription")
    private String shortDescription;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "Description")
    private String description;
    @Column(name = "DepartureDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date departureDate;
    @Column(name = "ReturnDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;
    @Column(name = "MinQuantity")
    private Integer minQuantity;
    @Column(name = "MaxQuantity")
    private Integer maxQuantity;
    @Column(name = "Status")
    private Short status;
    @ManyToMany(mappedBy = "toursCollection")
    private Collection<Flights> flightsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tours")
    private Collection<RestaurantSchedules> restaurantSchedulesCollection;
    @OneToMany(mappedBy = "tourId")
    private Collection<TourRegistration> tourRegistrationCollection;
    @JoinColumn(name = "EmployeeId", referencedColumnName = "EmployeeId")
    @ManyToOne
    private Employees employeeId;
    @JoinColumn(name = "PackageId", referencedColumnName = "PackageId")
    @ManyToOne
    private TourPackages packageId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tours")
    private Collection<AccommodationSchedules> accommodationSchedulesCollection;

    public Tours() {
    }

    public Integer getTourId() {
        return tourId;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public BigInteger getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigInteger unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Integer maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<Flights> getFlightsCollection() {
        return flightsCollection;
    }

    public void setFlightsCollection(Collection<Flights> flightsCollection) {
        this.flightsCollection = flightsCollection;
    }

    @XmlTransient
    public Collection<RestaurantSchedules> getRestaurantSchedulesCollection() {
        return restaurantSchedulesCollection;
    }

    public void setRestaurantSchedulesCollection(Collection<RestaurantSchedules> restaurantSchedulesCollection) {
        this.restaurantSchedulesCollection = restaurantSchedulesCollection;
    }

    @XmlTransient
    public Collection<TourRegistration> getTourRegistrationCollection() {
        return tourRegistrationCollection;
    }

    public void setTourRegistrationCollection(Collection<TourRegistration> tourRegistrationCollection) {
        this.tourRegistrationCollection = tourRegistrationCollection;
    }

    public Employees getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employees employeeId) {
        this.employeeId = employeeId;
    }

    public TourPackages getPackageId() {
        return packageId;
    }

    public void setPackageId(TourPackages packageId) {
        this.packageId = packageId;
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
        hash += (tourId != null ? tourId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tours)) {
            return false;
        }
        Tours other = (Tours) object;
        if ((this.tourId == null && other.tourId != null) || (this.tourId != null && !this.tourId.equals(other.tourId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.Tours[ tourId=" + tourId + " ]";
    }
    
}
