package com.merciqui.metier;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merciqui.dao.ComedienRepository;
import com.merciqui.dao.SpectacleRepository;
import com.merciqui.entities.Comedien;
import com.merciqui.entities.Spectacle;

@Service
@Transactional
public class MerciQuiMetierImpl implements IMerciQuiMetier{
	
	@Autowired
	private ComedienRepository comedienRepository ;
	
	@Autowired
	private SpectacleRepository spectacleRepository ;

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
		Collection<Comedien> listeComediens = comedienRepository.findAll();
		return listeComediens;
	}

	@Override
	public Spectacle consulterSpectacle(String nomSpectacle) {
		Spectacle spec =spectacleRepository.findOne(nomSpectacle);
		return spec;
	}

	@Override
	public Collection<Spectacle> listeSpectacles() {
		Collection<Spectacle> listeSpectacles = spectacleRepository.findAll();
		return listeSpectacles;
	}

	@Override
	public void supprimerSpectacle(String nomSpectacle) {
		Spectacle spec = consulterSpectacle(nomSpectacle);
		spectacleRepository.delete(spec);
		
	}

	@Override
	public void creerSpectacle(Spectacle spectacle) {
		spectacleRepository.save(spectacle);
	}

}
