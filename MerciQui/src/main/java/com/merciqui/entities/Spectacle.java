package com.merciqui.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Spectacle implements Serializable{
	
	@Id @GeneratedValue
	private Long idSpectacle;
	
	
	private String nomSpectacle ;
	
	
	@OneToMany(mappedBy="spectacle", fetch=FetchType.EAGER)
	private Collection<Role> listeRoles ;

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

	

}
