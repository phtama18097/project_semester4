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
@Table(name = "FeedbackTypes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FeedbackTypes.findAll", query = "SELECT f FROM FeedbackTypes f")
    , @NamedQuery(name = "FeedbackTypes.findByTypeId", query = "SELECT f FROM FeedbackTypes f WHERE f.typeId = :typeId")
    , @NamedQuery(name = "FeedbackTypes.findByTypeName", query = "SELECT f FROM FeedbackTypes f WHERE f.typeName = :typeName")})
public class FeedbackTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TypeId")
    private Integer typeId;
    @Size(max = 50)
    @Column(name = "TypeName")
    private String typeName;
    @OneToMany(mappedBy = "typeId")
    private Collection<Feedbacks> feedbacksCollection;

    public FeedbackTypes() {
    }

    public Integer getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @XmlTransient
    public Collection<Feedbacks> getFeedbacksCollection() {
        return feedbacksCollection;
    }

    public void setFeedbacksCollection(Collection<Feedbacks> feedbacksCollection) {
        this.feedbacksCollection = feedbacksCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (typeId != null ? typeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FeedbackTypes)) {
            return false;
        }
        FeedbackTypes other = (FeedbackTypes) object;
        if ((this.typeId == null && other.typeId != null) || (this.typeId != null && !this.typeId.equals(other.typeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cusc.entities.FeedbackTypes[ typeId=" + typeId + " ]";
    }
    
}
