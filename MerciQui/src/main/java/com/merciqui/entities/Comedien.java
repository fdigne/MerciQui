package com.merciqui.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.Entity;

@Entity
public class Comedien extends Personne implements Serializable{
	
	Collection<Calendar> listeDatesIndisponibles ;
	Collection<Spectacle> listeSpectacles ;

	public Comedien() {
		super();
	}

	public Comedien(String id3t, String nomClient, String prenomClient, Calendar dateNaissance, String numSecu,
			String sexe, String adressePostale, String adresseEmail, String numTel) {
		super(id3t, nomClient, prenomClient, dateNaissance, numSecu, sexe, adressePostale, adresseEmail, numTel);
	}

	public Collection<Calendar> getDatesIndisponibles() {
		return listeDatesIndisponibles;
	}

	public void setDatesIndisponibles(Collection<Calendar> datesIndisponibles) {
		this.listeDatesIndisponibles = datesIndisponibles;
	}

	public Collection<Calendar> getListeDatesIndisponibles() {
		return listeDatesIndisponibles;
	}

	public void setListeDatesIndisponibles(Collection<Calendar> listeDatesIndisponibles) {
		this.listeDatesIndisponibles = listeDatesIndisponibles;
	}

	public Collection<Spectacle> getListeSpectacles() {
		return listeSpectacles;
	}

	public void setListeSpectacles(Collection<Spectacle> listeSpectacles) {
		this.listeSpectacles = listeSpectacles;
	}
	
	
	
	
	
	

}
