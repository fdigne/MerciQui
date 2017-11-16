package com.merciqui.metier;

import java.util.Collection;
import java.util.Date;

import com.merciqui.entities.Comedien;
import com.merciqui.entities.Evenement;
import com.merciqui.entities.Role;
import com.merciqui.entities.Spectacle;

public interface IMerciQuiMetier {
	
	//Gestion des Comédiens
	public void creerComedien(Comedien comedien);
	public void supprimerComedien(String id3T);
	public Comedien consulterComedien(String id3T);
	public Collection<Comedien> listeComediens();
	public int getNombreDatesTotal(String id3T);
	public int getNombreDatesParSpectacleParComedien(Long idSpectacle, String id3T);
	
	//Gestion des Spectacles
	public Spectacle consulterSpectacle(String nomSpectacle);
	public Collection<Spectacle> listeSpectacles();
	public void supprimerSpectacle(String nomSpectacle);
	public void creerSpectacle(Spectacle spectacle);
	
	
	//Gestion des Roles
	public void creerRole(Role role);
	public void supprimerRole(Role role) ;
	public Collection<Role> listeRoles();
	public Collection<Role> listeRolesParSpectacle(Long idSpectacle) ;
	
	//Gestion des Evenements
	public Evenement consulterEvenement(String idEvenement);
	public Evenement creerEvenement(Evenement evenement);
	public void supprimerEvenement(Evenement evenement);
	public Collection<Evenement> listeEvenements();
	public Collection<Evenement> listeEvenementsParSpectacle(Long idSpectacle);
	
}
