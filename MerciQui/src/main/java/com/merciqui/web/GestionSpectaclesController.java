package com.merciqui.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.merciqui.entities.Comedien;
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
		model.addAttribute("nomSpectacle", nomSpectacle);
		if(nomSpectacle != null) {
			Spectacle spec = merciquimetier.consulterSpectacle(nomSpectacle);
			model.addAttribute("spectacle", spec);
		}
		Collection<Spectacle> listeSpectacles = merciquimetier.listeSpectacles();
		model.addAttribute("listeSpectacles", listeSpectacles);
			
	return "SpectacleView";
	}

	@PostMapping("/saisieSpectacle")
	public String saisieComedien(Model model, String nomSpectacle) {
		Spectacle spectacle = new Spectacle();
		spectacle.setNomSpectacle(nomSpectacle);
		try {
		merciquimetier.creerSpectacle(spectacle);
		}
		catch (Exception e) {
			model.addAttribute("error", e);
			return "redirect:/consulterSpectacle?nomSpectacle="+nomSpectacle+"&error="+e.getMessage() ;
		}
	
	return "redirect:/consulterSpectacle?nomSpectacle="+nomSpectacle ;
	}
	
	@PostMapping("/supprimerSpectacle")
	public String supprimerComedien(Model model, String nomSpectacle) {
		
		merciquimetier.supprimerSpectacle(nomSpectacle);	
		return "redirect:/consulterSpectacle" ;	
	}
}
