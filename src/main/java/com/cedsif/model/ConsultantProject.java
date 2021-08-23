/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cedsif.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity
@Table(name = "consultant_project")
@NamedQueries({
    @NamedQuery(name = "ConsultantProject.findAll", query = "SELECT c FROM ConsultantProject c")})
public class ConsultantProject implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConsultantProjectPK consultantProjectPK;

    public ConsultantProject() {
    }

    public ConsultantProject(ConsultantProjectPK consultantProjectPK) {
        this.consultantProjectPK = consultantProjectPK;
    }

    public ConsultantProject(int idConsultant, int idProject) {
        this.consultantProjectPK = new ConsultantProjectPK(idConsultant, idProject);
    }

    public ConsultantProjectPK getConsultantProjectPK() {
        return consultantProjectPK;
    }

    public void setConsultantProjectPK(ConsultantProjectPK consultantProjectPK) {
        this.consultantProjectPK = consultantProjectPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consultantProjectPK != null ? consultantProjectPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConsultantProject)) {
            return false;
        }
        ConsultantProject other = (ConsultantProject) object;
        if ((this.consultantProjectPK == null && other.consultantProjectPK != null) || (this.consultantProjectPK != null && !this.consultantProjectPK.equals(other.consultantProjectPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cedsif.model.ConsultantProject[ consultantProjectPK=" + consultantProjectPK + " ]";
    }
    
}
