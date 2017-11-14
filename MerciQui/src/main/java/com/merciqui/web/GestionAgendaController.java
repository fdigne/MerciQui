package com.merciqui.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.merciqui.metier.IMerciQuiMetier;

@Controller
public class GestionAgendaController {
	
	
	
	@Autowired
	IMerciQuiMetier merciquimetier ;
	
	@RequestMapping("/")
	public String index(Model model, String id3T) {
		return "redirect:/consulterCalendrier";
	}
	
	@RequestMapping("/consulterCalendrier")
	public String consulter(Model model) {
	
		return "AgendaView";
	}
}