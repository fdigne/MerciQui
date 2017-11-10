package com.merciqui.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Role implements Serializable{
	
	@Id @GeneratedValue
	private Long idRole;
	
	private String nomRole ;
	
	@ManyToMany(mappedBy="nomPersonne")
	private Collection<Comedien> listeComediens ;
	
	@ManyToOne
	@JoinColumn(name="CODE_DISTRIB")
	private Distribution distribution ;

	public Role() {
		super();
	}

	public Role(String nomRole) {
		super();
		this.nomRole = nomRole;
	}
	
	

	public Distribution getDistribution() {
		return distribution;
	}

	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;
	}

	public Long getIdRole() {
		return idRole;
	}

	public void setIdRole(Long idRole) {
		this.idRole = idRole;
	}

	public String getNomRole() {
		return nomRole;
	}

	public void setNomRole(String nomRole) {
		this.nomRole = nomRole;
	}

	public Collection<Comedien> getListeComediens() {
		return listeComediens;
	}

	public void setListeComediens(Collection<Comedien> listeComediens) {
		this.listeComediens = listeComediens;
	}
	
	

}
