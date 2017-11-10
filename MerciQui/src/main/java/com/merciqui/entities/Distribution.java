package com.merciqui.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

@Entity
public class Distribution implements Serializable{
	
	@Id @GeneratedValue
	private Long idDistribution;
	
	
	@MapKeyColumn(name = "distrib_titulaires")
	private HashMap<Role, Comedien> distribTitulaires ;
	@MapKeyColumn(name = "distrib_remplacante")
	private HashMap<Role, Collection<Comedien>> distribRemplacante ;
	
	@OneToMany(mappedBy="nomRole")
	private Collection<Role> listeRoles ;

	public Distribution() {
		super();
	}

	public Distribution(HashMap<Role, Comedien> distribTitulaires) {
		super();
		this.distribTitulaires = distribTitulaires;
	}

	public Long getIdDistribution() {
		return idDistribution;
	}

	public void setIdDistribution(Long idDistribution) {
		this.idDistribution = idDistribution;
	}

	public HashMap<Role, Comedien> getDistribTitulaires() {
		return distribTitulaires;
	}

	public void setDistribTitulaires(HashMap<Role, Comedien> distribTitulaires) {
		this.distribTitulaires = distribTitulaires;
	}

	public HashMap<Role, Collection<Comedien>> getDistribRemplacante() {
		return distribRemplacante;
	}

	public void setDistribRemplacante(HashMap<Role, Collection<Comedien>> distribRemplacante) {
		this.distribRemplacante = distribRemplacante;
	}

	public Collection<Role> getListeRoles() {
		return listeRoles;
	}

	public void setListeRoles(Collection<Role> listeRoles) {
		this.listeRoles = listeRoles;
	}
	
	
	
}
