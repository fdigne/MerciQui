package com.merciqui.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Spectacle implements Serializable{
	
	@Id @GeneratedValue
	private Long idSpectacle;
	
	private String nomSpectacle ;
	private Collection<Calendar> listeDatesSpectacles ;
	private Collection<Distribution> listeDistributions ;
	
	@ManyToMany(mappedBy="nomSalle", fetch=FetchType.EAGER)
	private Collection<Salle> listeSalles ;
	
	@ManyToMany
	private Collection<Comedien> listeComediens ;
	
	
	public Spectacle() {
		super();
	}


	public Spectacle(String nomSpectacle) {
		super();
		this.nomSpectacle = nomSpectacle;
	}


	public Long getIdSpectacle() {
		return idSpectacle;
	}


	public void setIdSpectacle(Long idSpectacle) {
		this.idSpectacle = idSpectacle;
	}


	public String getNomSpectacle() {
		return nomSpectacle;
	}


	public void setNomSpectacle(String nomSpectacle) {
		this.nomSpectacle = nomSpectacle;
	}


	public Collection<Calendar> getListeDatesSpectacles() {
		return listeDatesSpectacles;
	}


	public void setListeDatesSpectacles(Collection<Calendar> listeDatesSpectacles) {
		this.listeDatesSpectacles = listeDatesSpectacles;
	}


	public Collection<Distribution> getListeDistributions() {
		return listeDistributions;
	}


	public void setListeDistributions(Collection<Distribution> listeDistributions) {
		this.listeDistributions = listeDistributions;
	}
	
	

}
