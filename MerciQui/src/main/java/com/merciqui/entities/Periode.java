package com.merciqui.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Periode implements Serializable{
	
	@Id @GeneratedValue
	private Long idPeriode;
	
	private String nomPeriode ;
	private Calendar dateDebut ;
	private Calendar dateFin ;
	
	
	public Periode() {
		super();
	}


	public Long getIdPeriode() {
		return idPeriode;
	}


	public void setIdPeriode(Long idPeriode) {
		this.idPeriode = idPeriode;
	}


	public String getNomPeriode() {
		return nomPeriode;
	}


	public void setNomPeriode(String nomPeriode) {
		this.nomPeriode = nomPeriode;
	}


	public Calendar getDateDebut() {
		return dateDebut;
	}


	public void setDateDebut(Calendar dateDebut) {
		this.dateDebut = dateDebut;
	}


	public Calendar getDateFin() {
		return dateFin;
	}


	public void setDateFin(Calendar dateFin) {
		this.dateFin = dateFin;
	}
	
	

}
