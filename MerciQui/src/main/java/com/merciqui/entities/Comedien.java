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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Comedien extends Personne implements Serializable{
	
	
	/*@OneToMany(mappedBy="comedien", fetch=FetchType.EAGER)
	private Collection<Role> listeRoles ;
	*/

	public Comedien() {
		super();
	}

	public Comedien(String id3t, String nomClient, String prenomClient, Date dateNaissance, String numSecu,
			String sexe, String adressePostale, String adresseEmail, String numTel) {
		super(id3t, nomClient, prenomClient, dateNaissance, numSecu, sexe, adressePostale, adresseEmail, numTel);
	}

	/*public Collection<Role> getListeRoles() {
		return listeRoles;
	}

	public void setListeRoles(Collection<Role> listeRoles) {
		this.listeRoles = listeRoles;
	}
*/
	

}
