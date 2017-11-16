package com.merciqui.metier;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merciqui.dao.ComedienRepository;
import com.merciqui.dao.EvenementRepository;
import com.merciqui.dao.RoleRepository;
import com.merciqui.dao.SpectacleRepository;
import com.merciqui.entities.Comedien;
import com.merciqui.entities.Evenement;
import com.merciqui.entities.Role;
import com.merciqui.entities.Spectacle;

@Service
@Transactional
public class MerciQuiMetierImpl implements IMerciQuiMetier{
	
	@Autowired
	private ComedienRepository comedienRepository ;
	
	@Autowired
	private SpectacleRepository spectacleRepository ;
	
	@Autowired
	private EvenementRepository evenementRepository ;
	
	@Autowired
	private RoleRepository roleRepository ;

	@Override
	public void creerComedien(Comedien comedien) {
		comedienRepository.save(comedien);
	}

	@Override
	public void supprimerComedien(String id3T) {
		
		comedienRepository.delete(id3T);
	}

	@Override
	public Comedien consulterComedien(String id3T) {
		Comedien com = comedienRepository.findOne(id3T) ;
		
		if (com == null ) throw new RuntimeException("Comédien introuvable") ; 
		return com;
	}

	@Override
	public Collection<Comedien> listeComediens() {
		Collection<Comedien> listeComediens = comedienRepository.findAll(new Sort(Sort.Direction.ASC, "id3T"));
		
		return listeComediens;
	}

	@Override
	public Spectacle consulterSpectacle(String nomSpectacle) {
		Spectacle spec =spectacleRepository.findOne(nomSpectacle);
		return spec;
	}

	@Override
	public Collection<Spectacle> listeSpectacles() {
		Collection<Spectacle> listeSpectacles = spectacleRepository.findAll(new Sort(Sort.Direction.ASC, "nomSpectacle"));
		return listeSpectacles;
	}

	@Override
	public void supprimerSpectacle(String nomSpectacle) {
		Spectacle spec = consulterSpectacle(nomSpectacle);
		Collection<Role> listeRoles = listeRolesParSpectacle(spec.getIdSpectacle());
		for(Role r : listeRoles) {
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
		Collection<Role> listeRoles = roleRepository.findAll() ;
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
	public int getNombreDatesTotal(String id3T) {
			
		return evenementRepository.getNbreDatesByComedien(id3T);
	}

	@Override
	public int getNombreDatesParSpectacleParComedien(Long idSpectacle, String id3t) {
		int nbreDates = 0 ;
		Collection<Evenement> listeEvenements = evenementRepository.getListEvenementsParSpectacle(idSpectacle);
		for(Evenement ev : listeEvenements) {
			Collection<Comedien> listeComediens = ev.getListeComediens();
			for (Comedien com : listeComediens) {
				if(com.getId3T().equals(id3t)) {
					nbreDates++ ;
				}
			}
		}
		return nbreDates;
	}

	

}
