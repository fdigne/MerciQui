package com.merciqui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	private ArrayList<Comedien> listeComediens = new ArrayList<Comedien>();
	private ArrayList<Comedien> listeComediens1 = new ArrayList<Comedien>();
	private ArrayList<Comedien> listeComediens2 = new ArrayList<Comedien>();
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
		
		Comedien com3 = new Comedien() ;
		com3.setAdresseEmail("caca@wanadoo.com");
		com3.setId3T("FLSA");
		com3.setAdressePostale("5A chemin des Moundinats 31470 FONSORBES");
		com3.setNomPersonne("CACA");
		com3.setPrenomPersonne("Robert");
		com3.setNumSecu("2840831555494");
		com3.setNumTel("0676947523");
		com3.setSexe("Feminin");
		
		Comedien c1 = comedienRepository.save(com1);
		Comedien c2 = comedienRepository.save(com2);
		Comedien c3 = comedienRepository.save(com3);
		
		Spectacle spec1 = spectacleRepository.save(new Spectacle("PPN"));
		Spectacle spec2 = spectacleRepository.save(new Spectacle("FLORIMONT"));
		
		Role r1 = roleRepository.save(new Role("Clothilde", spec1));
		Role r2 = roleRepository.save(new Role("stagiaire", spec1));
		Role r3 = roleRepository.save(new Role("Florimont", spec2));
		
		Map<Role, Comedien> distribMap1 = new HashMap<Role, Comedien>();
		distribMap1.put(r1, c1);
		distribMap1.put(r2, c2);
		
		Map<Role, Comedien> distribMap2 = new HashMap<Role, Comedien>();
		distribMap2.put(r3, c3);
		Distribution distrib1 = distributionRepository.save(new Distribution(spec1, distribMap1));
		Distribution distrib2 = distributionRepository.save(new Distribution(spec2, distribMap2));
		
		
		
		
	}
}
