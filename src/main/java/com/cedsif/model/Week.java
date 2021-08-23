/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cedsif.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author user
 */
@Entity
@Table(name = "week")
@NamedQueries({
    @NamedQuery(name = "Week.findAll", query = "SELECT w FROM Week w")})
public class Week implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "end")
    @Temporal(TemporalType.DATE)
    private Date end;
    @Basic(optional = false)
    @Column(name = "start")
    @Temporal(TemporalType.DATE)
    private Date start;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "week", fetch = FetchType.LAZY)
    private List<ManagerWorkingHours> managerWorkingHoursList;

    public Week() {
    }

    public Week(Integer id) {
        this.id = id;
    }

    public Week(Integer id, String description, Date end, Date start) {
        this.id = id;
        this.description = description;
        this.end = end;
        this.start = start;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public List<ManagerWorkingHours> getManagerWorkingHoursList() {
        return managerWorkingHoursList;
    }

    public void setManagerWorkingHoursList(List<ManagerWorkingHours> managerWorkingHoursList) {
        this.managerWorkingHoursList = managerWorkingHoursList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Week)) {
            return false;
        }
        Week other = (Week) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cedsif.model.Week[ id=" + id + " ]";
    }
    
}
