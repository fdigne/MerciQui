package com.merciqui.metier;

import java.util.Collection;

import com.merciqui.entities.Comedien;

public interface IMerciQuiMetier {
	
	public void creerComedien(Comedien comedien);
	public void supprimerComedien(Comedien comedien);
	public Comedien consulterComedien(String id3T);
	public Collection<Comedien> listeComediens();
	
	

}
