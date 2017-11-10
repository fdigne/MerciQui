package com.merciqui;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.merciqui.dao.ComedienRepository;
import com.merciqui.dao.DistributionRepository;
import com.merciqui.dao.RoleRepository;
import com.merciqui.dao.SpectacleRepository;
import com.merciqui.entities.Comedien;
import com.merciqui.entities.Distribution;
import com.merciqui.entities.Role;
import com.merciqui.entities.Spectacle;

@SpringBootApplication
public class MerciQuiApplication implements CommandLineRunner{
	
	HashMap<Role, Comedien> listeTitulaire = new HashMap<Role, Comedien>() ;
	
	@Autowired
	private ComedienRepository comedienRepository ;
	
	@Autowired
	private DistributionRepository distributionRepository ;
	
	@Autowired
	private SpectacleRepository spectacleRepository ;
	
	@Autowired
	private RoleRepository roleRepository ;

	public static void main(String[] args) {
		SpringApplication.run(MerciQuiApplication.class, args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		
		Comedien com1 = new Comedien() ;
		com1.setAdresseEmail("fdigne@me.com");
		com1.setId3T("FLDI");
		com1.setAdressePostale("5A chemin des Moundinats 31470 FONSORBES");
		com1.setNomPersonne("Digne");
		com1.setPrenomPersonne("Florian");
		com1.setNumSecu("1810931555494");
		com1.setNumTel("0676947525");
		com1.setSexe("Masculin");
		
		Comedien com2 = new Comedien() ;
		com2.setAdresseEmail("fdigne@wanadoo.com");
		com2.setId3T("FLSA");
		com2.setAdressePostale("5A chemin des Moundinats 31470 FONSORBES");
		com2.setNomPersonne("SAHAFI");
		com2.setPrenomPersonne("Sarah");
		com2.setNumSecu("2840931555494");
		com2.setNumTel("0676947523");
		com2.setSexe("Feminin");
		
		comedienRepository.save(com1);
		comedienRepository.save(com2);
		
		Spectacle spec = new Spectacle("PPN");
		Role role1 = new Role("stagiaire");
		Role role2 = new Role("Clothilde1");
		Role role3 = new Role("Clothilde2");
		
		spectacleRepository .save(spec);
		roleRepository.save(role1) ;
		roleRepository.save(role2);
		roleRepository.save(role3);
		
		Distribution distrib = new Distribution() ;
		listeTitulaire.put(role1, com1);
		listeTitulaire.put(role2, com2);
		listeTitulaire.put(role3, com1);
		distrib.setDistribTitulaires(listeTitulaire);
		
		//distributionRepository.save(distrib);
		
	}
}
