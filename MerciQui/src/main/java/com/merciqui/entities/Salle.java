package com.merciqui.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Salle implements Serializable {
	
	@Id @GeneratedValue
	private Long idSalle;
	
	private String nomSalle ;
	
	private Collection<Spectacle> listeSpectacles ;

	public Salle() {
		super();
	}

	public Salle(String nomSalle) {
		super();
		this.nomSalle = nomSalle;
	}

	public Long getIdSalle() {
		return idSalle;
	}

	public void setIdSalle(Long idSalle) {
		this.idSalle = idSalle;
	}

	public String getNomSalle() {
		return nomSalle;
	}

	public void setNomSalle(String nomSalle) {
		this.nomSalle = nomSalle;
	}

	public Collection<Spectacle> getListeSpectacles() {
		return listeSpectacles;
	}

	public void setListeSpectacles(Collection<Spectacle> listeSpectacles) {
		this.listeSpectacles = listeSpectacles;
	}
	
	

}
