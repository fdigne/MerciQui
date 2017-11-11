package com.merciqui.metier;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merciqui.dao.ComedienRepository;
import com.merciqui.entities.Comedien;

@Service
@Transactional
public class MerciQuiMetierImpl implements IMerciQuiMetier{
	
	@Autowired
	private ComedienRepository comedienRepository ;

	@Override
	public void creerComedien(Comedien comedien) {
		comedienRepository.save(comedien);
	}

	@Override
	public void supprimerComedien(Comedien comedien) {
		comedienRepository.delete(comedien);
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

}
