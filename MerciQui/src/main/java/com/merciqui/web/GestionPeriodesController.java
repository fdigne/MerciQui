package com.merciqui.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GestionPeriodesController {
	
	
	@RequestMapping("/gestionPeriodesIndex")
	public String index(Model model) {
		return "redirect:/consulterPeriodes";
	}
	
	@RequestMapping("/consulterPeriodes")
	public String consulterPeriodes(Model model) {
		
	return "GestionPeriodeView";
	}

}
