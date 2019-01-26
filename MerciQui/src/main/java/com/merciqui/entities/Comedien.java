package com.merciqui.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Comedien extends Personne implements Serializable{
	
	
	@ManyToMany(mappedBy="listeComediens", fetch = FetchType.LAZY)
	private Collection<Evenement> listeEvenements ;
	
	@OneToMany(mappedBy="comedienTitulaire")
	private Collection<Role> listeRoles ;
	
	@ManyToMany(mappedBy="listeRemplas", fetch = FetchType.LAZY)
	private Set<Role> listeRolesRempl ;
	
	@ManyToMany
	@OrderBy(value="date_debut")
	private Collection<Periode> listeIndispos = new HashSet<Periode>(0); ;

	public Comedien() {
		super();
	}

	public Comedien(String id3t, String nomClient, String prenomClient, Date dateNaissance,
			String sexe, String adressePostale, String adresseEmail, String numTel) {
		super(id3t, nomClient, prenomClient, dateNaissance, sexe, adressePostale, adresseEmail, numTel);
	}

	
	public Collection<Evenement> getListeEvenements() {
		return listeEvenements;
	}

	public void setListeEvenements(Collection<Evenement> listeEvenements) {
		this.listeEvenements = listeEvenements;
	}

	

	
	public Collection<Role> getListeRoles() {
		return listeRoles;
	}

	public void setListeRoles(Collection<Role> listeRoles) {
		this.listeRoles = listeRoles;
	}

	
	public Collection<Periode> getListeIndispos() {
		return listeIndispos;
	}

	public void setListeIndispos(Collection<Periode> listeIndispos2) {
		this.listeIndispos = listeIndispos2;
	}

	public Set<Role> getListeRolesRempl() {
		return listeRolesRempl;
	}

	public void setListeRolesRempl(Set<Role> listeRolesRempl) {
		this.listeRolesRempl = listeRolesRempl;
	}
	
	

}
