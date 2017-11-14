package com.merciqui.web;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.merciqui.entities.Comedien;
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
		if(nomSpectacle != null) {
			Spectacle spec = merciquimetier.consulterSpectacle(nomSpectacle);
			model.addAttribute("spectacle", spec);
			Collection<Role> listeRoles = merciquimetier.listeRolesParSpectacle(spec.getIdSpectacle());
					model.addAttribute("listeRoles", listeRoles);
		}
		
		model.addAttribute("nomSpectacle", nomSpectacle);
		Collection<Comedien> listeComediens = merciquimetier.listeComediens();
		model.addAttribute("listeComediens", listeComediens);		
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
