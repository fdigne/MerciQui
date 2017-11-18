package com.merciqui.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
	@ManyToOne
	@JoinColumn(name="CODE_COMEDIEN")
	private Comedien comedien ;
	
	@ManyToOne
	@JoinColumn(name="CODE_SPECTACLE")
	private Spectacle spectacle ;
	
	@ElementCollection
	@CollectionTable(
	        name="liste_remplacants",
	        joinColumns=@JoinColumn(name="ROLE_ID")
	  )
	private Set<Comedien> listeRemplas ;
	
	
	
	public Role() {
		super();
	}

	public Role(String nomRole, Spectacle spectacle) {
		super();
		this.nomRole = nomRole;
		this.spectacle = spectacle ;
	}
	

	public Spectacle getSpectacle() {
		return spectacle;
	}

	public void setSpectacle(Spectacle spectacle) {
		this.spectacle = spectacle;
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

	public Comedien getComedien() {
		return comedien;
	}

	public void setComedien(Comedien comedien) {
		this.comedien = comedien;
	}

	public Set<Comedien> getListeRemplas() {
		return listeRemplas;
	}

	public void setListeRemplas(Set<Comedien> listeRemplas) {
		this.listeRemplas = listeRemplas;
	}

	

	

}
