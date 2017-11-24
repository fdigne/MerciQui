package com.merciqui.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Periode {
	
	@Id @GeneratedValue
	private long idPeriode;
	
	private Date dateDebut ;
	private Date dateFin;
	private boolean isVacances ;
	
	@ManyToMany(mappedBy="listeIndispos", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Collection<Comedien> listeComediens ;
	
	@OneToMany(mappedBy="periode")
	private Collection<Evenement> listeEvenements ;
	
	public Periode() {
		super();
	}
	public Periode(Date dateDebut, Date dateFin) {
		super();
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	public long getIdPeriode() {
		return idPeriode;
	}
	public void setIdPeriode(long idPeriode) {
		this.idPeriode = idPeriode;
	}
	public Collection<Comedien> getListeComediens() {
		return listeComediens;
	}
	public void setListeComediens(Collection<Comedien> listeComediens) {
		this.listeComediens = listeComediens;
	}
	public Collection<Evenement> getListeEvenements() {
		return listeEvenements;
	}
	public void setListeEvenements(Collection<Evenement> listeEvenements) {
		this.listeEvenements = listeEvenements;
	}
	public boolean isVacances() {
		return isVacances;
	}
	public void setVacances(boolean isVacances) {
		this.isVacances = isVacances;
	}
	
	

}
