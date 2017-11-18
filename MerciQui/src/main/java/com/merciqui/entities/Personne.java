package com.merciqui.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Personne implements Serializable {
	
	@Id 
	@Column(unique=true)
	private String id3T ; //numSecu
	
	private String nomPersonne;
	private String prenomPersonne;
	private Date dateNaissance ;
	private String sexe ;
	private String adressePostale ; 
	private String adresseEmail ;
	private String numTel ;
	
	
	
	
	public Personne() {
		super();
	}
	
	
	
	
	public Personne(String id3t, String nomPersonne, String prenomPersonne, Date dateNaissance,
			String sexe, String adressePostale, String adresseEmail, String numTel) {
		super();
		id3T = id3t;
		this.nomPersonne = nomPersonne;
		this.prenomPersonne = prenomPersonne;
		this.dateNaissance = dateNaissance;
		this.sexe = sexe;
		this.adressePostale = adressePostale;
		this.adresseEmail = adresseEmail;
		this.numTel = numTel;
	}




	public String getId3T() {
		return id3T;
	}


	public void setId3T(String id3t) {
		id3T = id3t;
	}


	public String getNomPersonne() {
		return nomPersonne;
	}
	public void setNomPersonne(String nomPersonne) {
		this.nomPersonne = nomPersonne;
	}
	public String getPrenomPersonne() {
		return prenomPersonne;
	}
	public void setPrenomPersonne(String prenomPersonne) {
		this.prenomPersonne = prenomPersonne;
	}
	public Date getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	public String getAdressePostale() {
		return adressePostale;
	}
	public void setAdressePostale(String adressePostale) {
		this.adressePostale = adressePostale;
	}
	public String getAdresseEmail() {
		return adresseEmail;
	}
	public void setAdresseEmail(String adresseEmail) {
		this.adresseEmail = adresseEmail;
	}
	public String getNumTel() {
		return numTel;
	}
	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}
	
	
	
	

}
