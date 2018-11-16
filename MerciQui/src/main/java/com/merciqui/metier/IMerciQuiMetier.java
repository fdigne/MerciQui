package com.merciqui.metier;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

import com.merciqui.entities.Comedien;
import com.merciqui.entities.Evenement;
import com.merciqui.entities.Periode;
import com.merciqui.entities.PeriodeFiltre;
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
	public int getNombreDatesParComedienParEvenement(Long idSpectacle, String id3t, String nomSalle, Date dateDebutFiltre, Date dateFinFiltre);
	public Collection<Comedien> getListeComediensParSpectacles(Long idSpectacle);
	
	
	//Gestion des Spectacles
	public Spectacle consulterSpectacle(String nomSpectacle);
	public Spectacle consulterSpectacle(Spectacle spectacle) ;
	public Spectacle consulterSpectacle(Long idSpectacle);
	public Collection<Spectacle> listeSpectacles();
	public void supprimerSpectacle(String nomSpectacle);
	public void creerSpectacle(Spectacle spectacle);
	public Collection<BigInteger> listeSpectacleParComedienParPeriode(String id3t, Date dateDebutFiltre,
			Date dateFinFiltre);
	
	
	//Gestion des Roles
	public void creerRole(Role role);
	public void supprimerRole(Role role) ;
	public Role consulterRole(Long idRole);
	public Collection<Role> listeRoles();
	public Collection<Role> listeRolesParSpectacle(Long idSpectacle) ;
	public Collection<Comedien> getListeRemplacants(Long idRole);
	
	//Gestion des Evenements
	public Evenement consulterEvenement(String idEvenement);
	public Evenement creerEvenement(Evenement evenement);
	public void supprimerEvenement(Evenement evenement);
	public void modifierEvenement(Evenement evenement);
	public Collection<Evenement> listeEvenements();
	public Collection<Evenement> listeEvenementsParPeriode(Date dateDebut, Date dateFin);
	public Collection<Evenement> listeEvenementsParSpectacle(Long idSpectacle);
	public Collection<Evenement> listeEvenementsParComedien(String id3T);
	public Collection<Evenement> listeEvenementParSalle(String nomSalle);
	public Collection<Evenement> listeEvenementsParComedienParPeriodeParCompagnie(String id3t, Date dateDebut, Date dateFin, String compagnie);
	public int getNombreDatesparComedienParSpectacleParPeriodeParCompagnie(String id3T, Long idSpectacle,Date dateDebut, Date dateFin, String compagnie );
	public Collection<BigInteger> listeSpectacleParComedienParPeriodeParCompagnie(String id3t, Date dateDebut, Date dateFin, String compagnie);
	public Collection<Object[]> getNombreDatesparComedienParPeriode(Date dateDebutFiltre, Date dateFinFiltre);
	public int getNombreDatesparComedienParSpectacleParPeriode(String id3t, Long idSpectacle, Date dateDebutFiltre,
			Date dateFinFiltre);
	public int existeEvenementFuturParComedien(String id3t);
	//Gestion des Periodes
	public Periode creerPeriode(Periode periode);
	public Periode consulterPeriode(Periode periode);
	public Periode consulterPeriode(Long idPeriode);
	public void supprimerPeriode(Long idPeriode);
	
	//Gestion des Periodes Filtres
	public PeriodeFiltre creerPeriodeFiltre(PeriodeFiltre periodeFiltre);
	public Collection<PeriodeFiltre> listePeriodeFiltre();
	public PeriodeFiltre consulterPeriodeFiltre(Long idPeriodeFiltre);
	public PeriodeFiltre modifierPeriodeFiltre(PeriodeFiltre periodeFiltre);
	public void supprimerPeriodeFiltre(Long idPeriodeFiltre) ;
	
	

	
	
}
