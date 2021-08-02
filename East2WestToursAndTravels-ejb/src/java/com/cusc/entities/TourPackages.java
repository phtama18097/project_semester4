/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "TourPackages")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TourPackages.findAll", query = "SELECT t FROM TourPackages t")
    , @NamedQuery(name = "TourPackages.findByPackageId", query = "SELECT t FROM TourPackages t WHERE t.packageId = :packageId")
    , @NamedQuery(name = "TourPackages.findByPackageName", query = "SELECT t FROM TourPackages t WHERE t.packageName = :packageName")
    , @NamedQuery(name = "TourPackages.findByStatus", query = "SELECT t FROM TourPackages t WHERE t.status = :status")})
public class TourPackages implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PackageId")
    private Integer packageId;
    @Column(name = "PackageName")
    private String packageName;
    @Lob
    @Column(name = "Description")
    private String description;
    @Column(name = "Status")
    private Short status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tourPackages")
    private Collection<DestinationSchedules> destinationSchedulesCollection;
    @OneToMany(mappedBy = "packageId")
    private Collection<Tours> toursCollection;

    public TourPackages() {
    }

    public Integer getPackageId() {
        return packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<DestinationSchedules> getDestinationSchedulesCollection() {
        return destinationSchedulesCollection;
    }

    public void setDestinationSchedulesCollection(Collection<DestinationSchedules> destinationSchedulesCollection) {
        this.destinationSchedulesCollection = destinationSchedulesCollection;
    }

    @XmlTransient
    public Collection<Tours> getToursCollection() {
        return toursCollection;
    }

    public void setToursCollection(Collection<Tours> toursCollection) {
        this.toursCollection = toursCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (packageId != null ? packageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TourPackages)) {
            return false;
        }
        TourPackages other = (TourPackages) object;
        if ((this.packageId == null && other.packageId != null) || (this.packageId != null && !this.packageId.equals(other.packageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.TourPackages[ packageId=" + packageId + " ]";
    }
    
}
