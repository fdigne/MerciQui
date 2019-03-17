package com.merciqui.web;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.merciqui.entities.Evenement;
import com.merciqui.metier.IMerciQuiMetier;

@Controller
public class GestionAnalyseController {

	@Autowired
	IMerciQuiMetier merciquimetier ;

	@RequestMapping("/gestionAnalyseIndex")
	public String index(Model model) {
		return "redirect:/consulterAnalyses";
	}

	@RequestMapping("/consulterAnalyses")
	public String consulterPeriodes(Model model) {
		Collection<Evenement> listeEvenements = merciquimetier.listeEvenements();
		model.addAttribute("listeEvenements",listeEvenements);
		return "AnalyseView";
	}
	
	@PostMapping("/lancerAnalyse")
	public String lancerAnalyse(Model model) {
		
		Collection<Evenement> listeEvenements = merciquimetier.listeEvenements();
		
		model.addAttribute("listeEvenements",listeEvenements);
		return "redirect:/consulterAnalyses";

	}
}
