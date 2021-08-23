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
public class ConsultantProjectPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_consultant")
    private int idConsultant;
    @Basic(optional = false)
    @Column(name = "id_project")
    private int idProject;

    public ConsultantProjectPK() {
    }

    public ConsultantProjectPK(int idConsultant, int idProject) {
        this.idConsultant = idConsultant;
        this.idProject = idProject;
    }

    public int getIdConsultant() {
        return idConsultant;
    }

    public void setIdConsultant(int idConsultant) {
        this.idConsultant = idConsultant;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idConsultant;
        hash += (int) idProject;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConsultantProjectPK)) {
            return false;
        }
        ConsultantProjectPK other = (ConsultantProjectPK) object;
        if (this.idConsultant != other.idConsultant) {
            return false;
        }
        if (this.idProject != other.idProject) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cedsif.model.ConsultantProjectPK[ idConsultant=" + idConsultant + ", idProject=" + idProject + " ]";
    }
    
}
