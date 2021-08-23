/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cedsif.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author user
 */
@Entity
@Table(name = "manager_working_hours")
@NamedQueries({
    @NamedQuery(name = "ManagerWorkingHours.findAll", query = "SELECT m FROM ManagerWorkingHours m")})
public class ManagerWorkingHours implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ManagerWorkingHoursPK managerWorkingHoursPK;
    @Basic(optional = false)
    @Column(name = "hours")
    @Temporal(TemporalType.TIME)
    private Date hours;
    @JoinColumn(name = "id_week", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Week week;
    @JoinColumn(name = "id_manager", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Manager manager;
    @JoinColumn(name = "id_project", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Project project;

    public ManagerWorkingHours() {
    }

    public ManagerWorkingHours(ManagerWorkingHoursPK managerWorkingHoursPK) {
        this.managerWorkingHoursPK = managerWorkingHoursPK;
    }

    public ManagerWorkingHours(ManagerWorkingHoursPK managerWorkingHoursPK, Date hours) {
        this.managerWorkingHoursPK = managerWorkingHoursPK;
        this.hours = hours;
    }

    public ManagerWorkingHours(int idManager, int idProject, int idWeek) {
        this.managerWorkingHoursPK = new ManagerWorkingHoursPK(idManager, idProject, idWeek);
    }

    public ManagerWorkingHoursPK getManagerWorkingHoursPK() {
        return managerWorkingHoursPK;
    }

    public void setManagerWorkingHoursPK(ManagerWorkingHoursPK managerWorkingHoursPK) {
        this.managerWorkingHoursPK = managerWorkingHoursPK;
    }

    public Date getHours() {
        return hours;
    }

    public void setHours(Date hours) {
        this.hours = hours;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (managerWorkingHoursPK != null ? managerWorkingHoursPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ManagerWorkingHours)) {
            return false;
        }
        ManagerWorkingHours other = (ManagerWorkingHours) object;
        if ((this.managerWorkingHoursPK == null && other.managerWorkingHoursPK != null) || (this.managerWorkingHoursPK != null && !this.managerWorkingHoursPK.equals(other.managerWorkingHoursPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cedsif.model.ManagerWorkingHours[ managerWorkingHoursPK=" + managerWorkingHoursPK + " ]";
    }
    
}
