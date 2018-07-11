package com.merciqui.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PeriodeFiltre {
	
	@Id @GeneratedValue
	private long idPeriode;
	
	private String nomPeriode ;
	private Date dateDebut ;
	private Date dateFin;
	
	
	public PeriodeFiltre() {
		
	}
	public PeriodeFiltre(String nomPeriode, Date dateDebut, Date dateFin) {
		this.nomPeriode = nomPeriode ;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}
	
	
	public String getNomPeriode() {
		return nomPeriode;
	}
	public void setNomPeriode(String nomPeriode) {
		this.nomPeriode = nomPeriode;
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
		

}
