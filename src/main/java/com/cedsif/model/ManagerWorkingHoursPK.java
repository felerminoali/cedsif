/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cedsif.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author user
 */
@Embeddable
public class ManagerWorkingHoursPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_manager")
    private int idManager;
    @Basic(optional = false)
    @Column(name = "id_project")
    private int idProject;
    @Basic(optional = false)
    @Column(name = "id_week")
    private int idWeek;

    public ManagerWorkingHoursPK() {
    }

    public ManagerWorkingHoursPK(int idManager, int idProject, int idWeek) {
        this.idManager = idManager;
        this.idProject = idProject;
        this.idWeek = idWeek;
    }

    public int getIdManager() {
        return idManager;
    }

    public void setIdManager(int idManager) {
        this.idManager = idManager;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public int getIdWeek() {
        return idWeek;
    }

    public void setIdWeek(int idWeek) {
        this.idWeek = idWeek;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idManager;
        hash += (int) idProject;
        hash += (int) idWeek;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ManagerWorkingHoursPK)) {
            return false;
        }
        ManagerWorkingHoursPK other = (ManagerWorkingHoursPK) object;
        if (this.idManager != other.idManager) {
            return false;
        }
        if (this.idProject != other.idProject) {
            return false;
        }
        if (this.idWeek != other.idWeek) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cedsif.model.ManagerWorkingHoursPK[ idManager=" + idManager + ", idProject=" + idProject + ", idWeek=" + idWeek + " ]";
    }
    
}
