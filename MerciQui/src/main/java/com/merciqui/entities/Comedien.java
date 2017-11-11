package com.merciqui.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Comedien extends Personne implements Serializable{
	
	
	@ElementCollection
	Collection<Calendar> listeDatesIndisponibles ;
	

	public Comedien() {
		super();
	}

	public Comedien(String id3t, String nomClient, String prenomClient, Date dateNaissance, String numSecu,
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
	
	
	
	
	
	

}
