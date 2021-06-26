/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CarImages")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CarImages.findAll", query = "SELECT c FROM CarImages c")
    , @NamedQuery(name = "CarImages.findByImageId", query = "SELECT c FROM CarImages c WHERE c.imageId = :imageId")
    , @NamedQuery(name = "CarImages.findByFileName", query = "SELECT c FROM CarImages c WHERE c.fileName = :fileName")})
public class CarImages implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ImageId")
    private Integer imageId;
    @Size(max = 100)
    @Column(name = "FileName")
    private String fileName;
    @JoinColumn(name = "CarId", referencedColumnName = "CarId")
    @ManyToOne
    private Cars carId;

    public CarImages() {
    }

    public CarImages(Integer imageId) {
        this.imageId = imageId;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Cars getCarId() {
        return carId;
    }

    public void setCarId(Cars carId) {
        this.carId = carId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (imageId != null ? imageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CarImages)) {
            return false;
        }
        CarImages other = (CarImages) object;
        if ((this.imageId == null && other.imageId != null) || (this.imageId != null && !this.imageId.equals(other.imageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.CarImages[ imageId=" + imageId + " ]";
    }
    
}
