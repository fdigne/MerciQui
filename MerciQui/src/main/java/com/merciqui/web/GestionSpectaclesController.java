package com.merciqui.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.merciqui.entities.Comedien;
import com.merciqui.entities.Evenement;
import com.merciqui.entities.Role;
import com.merciqui.entities.Spectacle;
import com.merciqui.metier.IMerciQuiMetier;

@Controller
public class GestionSpectaclesController {
	
	@Autowired
	IMerciQuiMetier merciquimetier ;
	
	
	
	@RequestMapping("/spectacleIndex")
	public String index(Model model) {
		return "redirect:/consulterSpectacle";
	}
	
	@RequestMapping("/consulterSpectacle")
	public String consulterSpectacle(Model model, String nomSpectacle) {
		
		Collection<String[]> itemsComediens = new ArrayList<String[]>() ;
		Collection<Evenement> listeEvenements37 = new ArrayList<Evenement>();
		Collection<Evenement> listeEvenements333 = new ArrayList<Evenement>();
		Map<String, Integer> mapTotalDateParSpectacle = new HashMap<String, Integer>(); ;
		
		if(nomSpectacle != null) {
			Spectacle spec = merciquimetier.consulterSpectacle(nomSpectacle);
			model.addAttribute("spectacle", spec);
			Collection<Role> listeRoles = merciquimetier.listeRolesParSpectacle(spec.getIdSpectacle());
			model.addAttribute("listeRoles", listeRoles);
			for(Role role : listeRoles) {
				int totalDates = merciquimetier.getNombreDatesParSpectacleParComedien(spec.getIdSpectacle(), role.getComedien().getId3T());
				mapTotalDateParSpectacle.put(role.getComedien().getId3T(), totalDates);
			}
			model.addAttribute("mapTotalDate", mapTotalDateParSpectacle);
			Collection<Evenement> listeEvenements = merciquimetier.listeEvenementsParSpectacle(spec.getIdSpectacle());
			for(Evenement ev : listeEvenements) {
				if(ev.getNomSalle().equals("3T")) {
					listeEvenements37.add(ev);
				}
				else {
					listeEvenements333.add(ev);
				}
			}
			model.addAttribute("listeEvenements37", listeEvenements37);
			model.addAttribute("listeEvenements333", listeEvenements333);

			
		}
		
		model.addAttribute("nomSpectacle", nomSpectacle);
		Collection<Comedien> listeComediens = merciquimetier.listeComediens();
		for(Comedien c : listeComediens) {
			itemsComediens.add(new String[] {c.getId3T(), c.getNomPersonne(), c.getPrenomPersonne()});
		}
		model.addAttribute("listeComediens", listeComediens);		
		model.addAttribute("itemsComediens", itemsComediens) ;
		
		
		Collection<Spectacle> listeSpectacles = merciquimetier.listeSpectacles();
		model.addAttribute("listeSpectacles", listeSpectacles);
		
		
			
	return "SpectacleView";
	}
			
	@PostMapping("/saisieSpectacle")
	public String saisieSpectacle(Model model, String nomSpectacle, String[] nomRole, String[] id3T) {
		try {
		Spectacle spectacle = new Spectacle();
		spectacle.setNomSpectacle(nomSpectacle);
		merciquimetier.creerSpectacle(spectacle);
		int indexRole = 0 ;
		
		for(String s : nomRole) {
			Role role = new Role() ;
			Comedien comedien = merciquimetier.consulterComedien(id3T[indexRole]) ;
			
			role.setNomRole(s);
			role.setSpectacle(merciquimetier.consulterSpectacle(nomSpectacle));
			role.setComedien(comedien);
			indexRole++ ;
			merciquimetier.creerRole(role);
			
		}
		
		}
		catch (Exception e) {
			model.addAttribute("error", e);
			return "redirect:/consulterSpectacle?nomSpectacle="+nomSpectacle+"&error="+e.getMessage() ;
		}
	
	return "redirect:/consulterSpectacle?nomSpectacle="+nomSpectacle ;
	}
	
	@PostMapping("/supprimerSpectacle")
	public String supprimerSpectacle(Model model, String nomSpectacle) {
		System.out.println(nomSpectacle);
		merciquimetier.supprimerSpectacle(nomSpectacle);	
		return "redirect:/consulterSpectacle" ;	
	}
}
