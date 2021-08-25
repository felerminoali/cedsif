/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cedsif.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 *
 * @author user
 */
@Entity
@Table(name = "employee")
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")})
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @Column(name = "nuit")
    private String nuit;
    
    @Basic(optional = false)
    @Column(name = "photo")
    private String photo =  "../../dist/img/user4-128x128.jpg";
    
    @Basic(optional = false)
    @Column(name = "sex")
    private short sex;
    
    
    @Basic(optional = false)
    @Enumerated(value = EnumType.STRING) 
    @Column(name = "category", length = 1)
    private Category category;
    
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "employee")
    private Manager manager;
    
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "employee")
    private Adminstrator adminstrator;
    
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "employee")
    private Consultant consultant;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private List<Relative> relativeList;
    

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "employee_project",   joinColumns = @JoinColumn(name = "employee_id"), uniqueConstraints = {@UniqueConstraint(columnNames = {"employee_id","project_id"})}, inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projects;
    
    public Employee() {
    }

    public Employee(Long id) {
        this.id = id;
    }

    public Employee(Long id, String name, String nuit, short sex) {
        this.id = id;
        this.name = name;
        this.nuit = nuit;
        this.sex = sex;
    }

    
    
    public Employee(String name, String nuit, String photo, short sex, Category category, Manager manager,
			List<Relative> relativeList) {
		super();
		this.name = name;
		this.nuit = nuit;
		this.photo = photo;
		this.sex = sex;
		this.category = category;
		this.manager = manager;
		this.relativeList = relativeList;
	}
    
	public Employee(String name, String nuit, String photo, short sex, Category category, Adminstrator adminstrator,
			List<Relative> relativeList) {
		super();
		this.name = name;
		this.nuit = nuit;
		this.photo = photo;
		this.sex = sex;
		this.category = category;
		this.adminstrator = adminstrator;
		this.relativeList = relativeList;
	}
	
	
	public Employee(String name, String nuit, String photo, short sex, Category category, Consultant consultant,
			List<Relative> relativeList) {
		super();
		this.name = name;
		this.nuit = nuit;
		this.photo = photo;
		this.sex = sex;
		this.category = category;
		this.consultant = consultant;
		this.relativeList = relativeList;
	}
	
	


	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNuit() {
        return nuit;
    }

    public void setNuit(String nuit) {
        this.nuit = nuit;
    }

    public short getSex() {
        return sex;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Adminstrator getAdminstrator() {
        return adminstrator;
    }

    public void setAdminstrator(Adminstrator adminstrator) {
        this.adminstrator = adminstrator;
    }

 
    public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

    public Consultant getConsultant() {
		return consultant;
	}

	public void setConsultant(Consultant consultant) {
		this.consultant = consultant;
	}

	public List<Relative> getRelativeList() {
        return relativeList;
    }

    public void setRelativeList(List<Relative> relativeList) {
        this.relativeList = relativeList;
    }
    
    

    public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
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
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cedsif.model.Employee[ id=" + id + " ]";
    }
    
}
