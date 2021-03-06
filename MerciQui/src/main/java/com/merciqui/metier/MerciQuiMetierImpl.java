package com.merciqui.metier;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merciqui.dao.ComedienRepository;
import com.merciqui.dao.EvenementRepository;
import com.merciqui.dao.PeriodeFiltreRepository;
import com.merciqui.dao.PeriodeRepository;
import com.merciqui.dao.RoleRepository;
import com.merciqui.dao.SpectacleRepository;
import com.merciqui.entities.Comedien;
import com.merciqui.entities.Evenement;
import com.merciqui.entities.Periode;
import com.merciqui.entities.PeriodeFiltre;
import com.merciqui.entities.Role;
import com.merciqui.entities.Spectacle;

@Service
@Transactional
public class MerciQuiMetierImpl implements IMerciQuiMetier {

	@Autowired
	private ComedienRepository comedienRepository;

	@Autowired
	private SpectacleRepository spectacleRepository;

	@Autowired
	private EvenementRepository evenementRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PeriodeRepository periodeRepository;

	@Autowired
	private PeriodeFiltreRepository periodeFiltreRepository ;

	@Override
	public Comedien creerComedien(Comedien comedien) {

		return comedienRepository.save(comedien);
	}

	@Override
	public void supprimerComedien(Long id3T) {
		Comedien comedien = comedienRepository.findOne(id3T);
		for (Role r : comedien.getListeRoles()) {
			r.setComedienTitulaire(null);
			roleRepository.save(r);
		}
		comedienRepository.delete(comedien);

	}

	@Override
	public Comedien consulterComedien(Long id3T) {
		Comedien com = comedienRepository.findOne(id3T);

		if (com == null)
			throw new RuntimeException("Comédien introuvable");
		return com;
	}

	@Override
	public Collection<Comedien> listeComediens() {
		Collection<Comedien> listeComediens = comedienRepository.findAll(new Sort(Sort.Direction.ASC, "nomPersonne"));

		return listeComediens;
	}

	@Override
	public Spectacle consulterSpectacle(String nomSpectacle) {
		Spectacle spec = spectacleRepository.findOne(nomSpectacle);
		return spec;
	}

	@Override
	public Collection<Spectacle> listeSpectacles() {
		Collection<Spectacle> listeSpectacles = spectacleRepository
				.findAll(new Sort(Sort.Direction.ASC, "nomSpectacle"));
		return listeSpectacles;
	}

	@Override
	public void supprimerSpectacle(String nomSpectacle) {
		Spectacle spec = consulterSpectacle(nomSpectacle);
		Collection<Role> listeRoles = listeRolesParSpectacle(spec.getIdSpectacle());
		for (Role r : listeRoles) {
			supprimerRole(r);
		}
		spectacleRepository.delete(spec);

	}

	@Override
	public void creerSpectacle(Spectacle spectacle) {
		spectacleRepository.save(spectacle);
	}

	@Override
	public void creerRole(Role role) {
		roleRepository.save(role);

	}

	@Override
	public Collection<Role> listeRoles() {
		Collection<Role> listeRoles = roleRepository.findAll();
		return listeRoles;
	}

	@Override
	public Collection<Role> listeRolesParSpectacle(Long idSpectacle) {
		Collection<Role> listeRoles = roleRepository.getListeRolesParSpectacle(idSpectacle);
		return listeRoles;
	}

	@Override
	public void supprimerRole(Role role) {
		roleRepository.delete(role);

	}

	@Override
	public Evenement creerEvenement(Evenement evenement) {
		evenementRepository.save(evenement);
		return evenement;

	}


	@Override
	public void supprimerEvenement(Evenement evenement) {
		for (Comedien comedien : evenement.getDistribution().values()) {
			comedien.getListeIndispos().remove(evenement.getPeriode());
			comedienRepository.save(comedien);
		}
		for (Comedien comedien : evenement.getListeComediens()) {
			if (comedien.getListeIndispos().contains(evenement.getPeriode())) {
				comedien.getListeIndispos().remove(evenement.getPeriode());
				comedienRepository.save(comedien);
			}
		}
		evenement.setDistribution(null);
		evenementRepository.save(evenement);
		evenementRepository.delete(evenement);
		periodeRepository.delete(evenement.getPeriode());

	}
	
	@Override
	public void modifierEvenement(Evenement evenement) {
		for (Comedien comedien : evenement.getDistribution().values()) {
			comedien.getListeIndispos().remove(evenement.getPeriode());
			comedienRepository.save(comedien);

		}
		for (Comedien comedien : evenement.getListeComediens()) {
			if (comedien.getListeIndispos().contains(evenement.getPeriode())) {
				comedien.getListeIndispos().remove(evenement.getPeriode());
				comedienRepository.save(comedien);
			}
		}
		evenement.setDistribution(null);
		evenementRepository.save(evenement);
		evenementRepository.delete(evenement);

	}

	@Override
	public Collection<Evenement> listeEvenements() {
		return evenementRepository.findAll(new Sort(Sort.Direction.ASC, "dateEvenement"));
	}

	@Override
	public Collection<Evenement> listeEvenementsParSpectacle(Long idSpectacle) {

		return evenementRepository.getListEvenementsParSpectacle(idSpectacle);
	}

	@Override
	public Evenement consulterEvenement(String idEvenement) {
		Evenement evenement = evenementRepository.findOne(idEvenement);
		return evenement;
	}

	@Override
	public int getNombreDatesTotal(Long id3T) {

		return evenementRepository.getNbreDatesByComedien(id3T);
	}

	@Override
	public int getNombreDatesParSpectacleParComedien(Long idSpectacle, Long id3t) {
		int nbreDates = 0;
		Collection<Evenement> listeEvenements = evenementRepository.getListEvenementsParSpectacle(idSpectacle);
		for (Evenement ev : listeEvenements) {
			Collection<Comedien> listeComediens = ev.getListeComediens();
			for (Comedien com : listeComediens) {
				if (com.getId3T().equals(id3t)) {
					nbreDates++;
				}
			}
		}
		return nbreDates;
	}

	@Override
	public int getNombreDatesParComedienParEvenement(Long idSpectacle, Long id3T, String nomSalle, Date dateDebutFiltre, Date dateFinFiltre) {
		int nbreDates = 0;
		Collection<Evenement> listeEvenements = evenementRepository.getListEvenementsParSpectacle(idSpectacle);
		for (Evenement ev : listeEvenements) {
			if(ev.getDateEvenement().compareTo(dateDebutFiltre)>0 && dateFinFiltre.compareTo(ev.getDateEvenement())> 0) {
				if (ev.getNomSalle().equals(nomSalle)) {
					for (Comedien com : ev.getDistribution().values()) {
						if (com.getId3T().equals(id3T)) {
							nbreDates++;
						}
					}
				}
			}
		}
		return nbreDates;
	}


	@Override
	public Collection<Evenement> listeEvenementsParComedien(Long id3T) {
		Collection<Evenement> listeEvenements = listeEvenements();
		Collection<Evenement> listeEvenementsParComedien = new ArrayList<Evenement>();
		for (Evenement ev : listeEvenements) {
			for (Comedien com : ev.getDistribution().values()) {
				if (com.getId3T().equals(id3T)) {
					listeEvenementsParComedien.add(ev);
				}
			}
		}

		return listeEvenementsParComedien;
	}

	@Override
	public Collection<Evenement> listeEvenementsParComedienParPeriodeParCompagnie(Long id3T, Date dateDebut, Date dateFin, String compagnie) {
		Collection<Evenement> listeEvenements = evenementRepository.getListEvenementsParComedienParPeriode(id3T, dateDebut, dateFin, compagnie);
		return listeEvenements;
	}

	@Override
	public Collection<Evenement> listeEvenementsParComedienParPeriode(Long id3t, Date dateDebut, Date dateFin) {
		return evenementRepository.getListEvenementsParComedienParPeriode(id3t, dateDebut, dateFin);
	}

	@Override
	public Collection<Evenement> listeEvenementParSalle(String nomSalle) {
		return evenementRepository.getListEvenementsParSalle(nomSalle);
	}

	@Override
	public Collection<Comedien> getListeRemplacants(Long idRole) {
		Collection<Comedien> listeRemplas = new ArrayList<Comedien>();
		for (Comedien com : roleRepository.getListeRemplacants(idRole)) {
			listeRemplas.add(consulterComedien(com.getId3T()));
		}
		return listeRemplas;
	}

	@Override
	public Periode creerPeriode(Periode periode) {
		periodeRepository.save(periode);
		return periode;
	}

	@Override
	public Collection<Comedien> getListeComediensParSpectacles(Long idSpectacle) {
		Collection<Role> listeRoles = roleRepository.getListeRolesParSpectacle(idSpectacle);
		Collection<Comedien> listeComediensparSpectacle = new ArrayList<Comedien>();
		for (Role role : listeRoles) {
			if (role.getComedienTitulaire() != null) {
				if (!listeComediensparSpectacle.contains(role.getComedienTitulaire())) {
					listeComediensparSpectacle.add(role.getComedienTitulaire());
				}
			}
			for (Comedien com : role.getListeRemplas()) {
				if (!listeComediensparSpectacle.contains(com)) {
					listeComediensparSpectacle.add(com);

				}
			}
		}

		return listeComediensparSpectacle;
	}

	@Override
	public Collection<Comedien> getListeComediensParPeriode(Date dateDebutFiltre, Date dateFinFiltre) {
		return comedienRepository.getListeComediensParPeriode(dateDebutFiltre, dateFinFiltre);
	}

	@Override
	public Periode consulterPeriode(Periode periode) {
		return periodeRepository.findOne(periode.getIdPeriode());
	}

	@Override
	public Spectacle consulterSpectacle(Spectacle spectacle) {
		return spectacleRepository.findOne(spectacle.getIdSpectacle());
	}

	@Override
	public Role consulterRole(Long idRole) {
		return roleRepository.findOne(idRole);
	}

	@Override
	public void supprimerPeriode(Long idPeriode) {
		periodeRepository.delete(idPeriode);
	}

	@Override
	public Periode consulterPeriode(Long idPeriode) {
		return periodeRepository.findOne(idPeriode);
	}

	@Override
	public int getNombreDatesparComedienParSpectacleParPeriodeParCompagnie(Long id3t, Long idSpectacle,
			Date dateDebut, Date dateFin, String compagnie) {

		return evenementRepository.getNbreDatesParComedienParSpectacleParPeriodeParCompagnie(id3t, idSpectacle, dateDebut, dateFin, compagnie);
	}

	@Override
	public Collection<BigInteger> listeSpectacleParComedienParPeriodeParCompagnie(Long id3t, Date dateDebut, Date dateFin,
			String compagnie) {
		return evenementRepository.getListSpectacleParComedienParPeriodeParCompagnie(id3t, dateDebut, dateFin, compagnie);

	}

//Traitement des périodes filtres

	@Override
	public Spectacle consulterSpectacle(Long idSpectacle) {
		return spectacleRepository.findOne(idSpectacle);
	}

	@Override
	public PeriodeFiltre creerPeriodeFiltre(PeriodeFiltre periodeFiltre) {
		
		return periodeFiltreRepository.save(periodeFiltre);
	}
	
	@Override
	public Collection<PeriodeFiltre> listePeriodeFiltre() {
		return periodeFiltreRepository.findAll(new Sort(Sort.Direction.ASC, "dateDebut"));
	}

	@Override
	public PeriodeFiltre consulterPeriodeFiltre(Long idPeriodeFiltre) {
		return periodeFiltreRepository.findOne(idPeriodeFiltre);
	}

	@Override
	public PeriodeFiltre modifierPeriodeFiltre(PeriodeFiltre periodeFiltre) {
		return periodeFiltreRepository.save(periodeFiltre);
	}

	@Override
	public void supprimerPeriodeFiltre(Long idPeriodeFiltre) {
		periodeFiltreRepository.delete(idPeriodeFiltre);
	}

	@Override
	public Collection<Evenement> listeEvenementsParPeriode(Date dateDebut, Date dateFin) {
		return evenementRepository.getListEvenementsParPeriode(dateDebut, dateFin);
	}

	@Override
	public Collection<Object[]> getNombreDatesparComedienParPeriode(Date dateDebutFiltre, Date dateFinFiltre) {
		return evenementRepository.getNbreDatesParComedienParPeriode(dateDebutFiltre, dateFinFiltre);
	}

	@Override
	public int getNombreDatesparComedienParSpectacleParPeriode(Long id3t, Long idSpectacle, Date dateDebutFiltre,
			Date dateFinFiltre) {
		return evenementRepository.getNbreDatesParComedienParSpectacleParPeriode(id3t,idSpectacle, dateDebutFiltre, dateFinFiltre);
	}

	@Override
	public Collection<BigInteger> listeSpectacleParComedienParPeriode(Long id3t, Date dateDebutFiltre,
			Date dateFinFiltre) {
		return evenementRepository.getListSpectacleParComedienParPeriode(id3t, dateDebutFiltre, dateFinFiltre);
	}

	@Override
	public int existeEvenementFuturParComedien(Long id3t) {
		
		return evenementRepository.existEvenementFuturParComedien(id3t);
	}

	@Override
	public void cleanPeriode() {
		periodeRepository.cleanPeriode();
	}

	@Override
	public void cleanIndisposComediens() {
		evenementRepository.cleanIndisposComediens();
	}

	@Override
	public Collection<Evenement> listeEvenementsFuturs() {
		Date today = new Date();
		return evenementRepository.getListEvenementsFuturs(today);
	}

	@Override
	public void repairIndispos(Long idPeriode, Long id3T) {
		periodeRepository.repairIndispos(idPeriode, id3T);
	}
}
