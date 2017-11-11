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
	
	@OneToMany
	private Collection<Comedien> listeComediens ;
	
	@ManyToOne
	@JoinColumn(name="CODE_SPECTACLE")
	private Spectacle spectacle ;
	
	
	
	public Role() {
		super();
	}

	public Role(String nomRole, Spectacle spectacle) {
		super();
		this.nomRole = nomRole;
		this.spectacle = spectacle ;
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
