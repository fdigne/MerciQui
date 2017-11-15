package com.merciqui.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Evenement implements Serializable{
	
@Id @GeneratedValue
private Long idEvenement;

private Date dateEvenement;

private String nomSalle ;

@ManyToOne
@JoinColumn(name="CODE_SPECTACLE")
private Spectacle spectacle;

public Evenement() {
	super();
}

public Evenement(Date dateEvenement, Spectacle spectacle, String nomSalle) {
	super();
	this.nomSalle = nomSalle ;
	this.dateEvenement = dateEvenement;
	this.spectacle = spectacle;
}

public Long getIdEvenement() {
	return idEvenement;
}

public void setIdEvenement(Long idEvenement) {
	this.idEvenement = idEvenement;
}

public Date getDateEvenement() {
	return dateEvenement;
}

public void setDateEvenement(Date dateEvenement) {
	this.dateEvenement = dateEvenement;
}

public Spectacle getSpectacle() {
	return spectacle;
}

public void setSpectacle(Spectacle spectacle) {
	this.spectacle = spectacle;
}

public String getNomSalle() {
	return nomSalle;
}

public void setNomSalle(String nomSalle) {
	this.nomSalle = nomSalle;
}




}
