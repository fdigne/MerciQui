package com.merciqui.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@Entity
public class Comedien extends Personne implements Serializable{
	
	
	@ManyToMany(mappedBy="listeComediens", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Collection<Evenement> listeEvenements ;
	

	public Comedien() {
		super();
	}

	public Comedien(String id3t, String nomClient, String prenomClient, Date dateNaissance, String numSecu,
			String sexe, String adressePostale, String adresseEmail, String numTel) {
		super(id3t, nomClient, prenomClient, dateNaissance, numSecu, sexe, adressePostale, adresseEmail, numTel);
	}

	
	public Collection<Evenement> getListeEvenements() {
		return listeEvenements;
	}

	public void setListeEvenements(Collection<Evenement> listeEvenements) {
		this.listeEvenements = listeEvenements;
	}

}
