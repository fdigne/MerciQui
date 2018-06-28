package com.merciqui.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Evenement implements Serializable{
	
@Id
private String idEvenement;

private Date dateEvenement;

private String nomSalle ;

private String compagnie ;

@ManyToOne
@JoinColumn(name="CODE_SPECTACLE")
private Spectacle spectacle;

@ManyToOne
@JoinColumn(name="ID_PERIODE")
private Periode periode ;

@ManyToMany(fetch = FetchType.LAZY)
private Map<Long, Comedien> distribution ;

@ManyToMany
private Set<Comedien> listeComediens = new HashSet<Comedien>(0);

public Evenement() {
	super();
}

public Evenement(String idEvenement, Date dateEvenement, Spectacle spectacle, String nomSalle, Set<Comedien> listeComediens) {
	super();
	this.idEvenement = idEvenement ;
	this.nomSalle = nomSalle ;
	this.dateEvenement = dateEvenement;
	this.spectacle = spectacle;
	this.listeComediens = listeComediens ;
}

public String getIdEvenement() {
	return idEvenement;
}

public void setIdEvenement(String idEvenement) {
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


public Set<Comedien> getListeComediens() {
	return listeComediens;
}

public void setListeComediens(Set<Comedien> listeComediens) {
	this.listeComediens = listeComediens;
}

public Periode getPeriode() {
	return periode;
}

public void setPeriode(Periode periode) {
	this.periode = periode;
}

public Map<Long, Comedien> getDistribution() {
	return distribution;
}

public void setDistribution(Map<Long, Comedien> distribution) {
	this.distribution = distribution;
}

public String getCompagnie() {
	return compagnie;
}

public void setCompagnie(String compagnie) {
	this.compagnie = compagnie;
}





}
