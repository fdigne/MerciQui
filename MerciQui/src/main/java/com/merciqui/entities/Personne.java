package com.merciqui.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Personne implements Serializable {
	
	@Id @GeneratedValue
	private Long idPersonne;
	
	private String id3T ;
	private String nomClient;
	private String prenomClient;
	private Calendar dateNaissance ;
	private String numSecu ;
	private String sexe ;
	private String adressePostale ; 
	private String adresseEmail ;
	private String numTel ;
	
	
	
	
	public Personne() {
		super();
	}
	
	
	
	
	public Personne(String id3t, String nomClient, String prenomClient, Calendar dateNaissance, String numSecu,
			String sexe, String adressePostale, String adresseEmail, String numTel) {
		super();
		id3T = id3t;
		this.nomClient = nomClient;
		this.prenomClient = prenomClient;
		this.dateNaissance = dateNaissance;
		this.numSecu = numSecu;
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


	public Long getIdPersonne() {
		return idPersonne;
	}
	public void setIdPersonne(Long idPersonne) {
		this.idPersonne = idPersonne;
	}
	public String getNomClient() {
		return nomClient;
	}
	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}
	public String getPrenomClient() {
		return prenomClient;
	}
	public void setPrenomClient(String prenomClient) {
		this.prenomClient = prenomClient;
	}
	public Calendar getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(Calendar dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	public String getNumSecu() {
		return numSecu;
	}
	public void setNumSecu(String numSecu) {
		this.numSecu = numSecu;
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
