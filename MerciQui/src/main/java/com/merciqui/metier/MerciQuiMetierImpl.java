package com.merciqui.metier;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merciqui.dao.ComedienRepository;
import com.merciqui.dao.RoleRepository;
import com.merciqui.dao.SpectacleRepository;
import com.merciqui.entities.Comedien;
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

}
