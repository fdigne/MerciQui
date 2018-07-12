package com.merciqui.web;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.merciqui.entities.PeriodeFiltre;
import com.merciqui.metier.IMerciQuiMetier;

@Controller
public class GestionPeriodesController {

	@Autowired
	IMerciQuiMetier merciquimetier ;

	@RequestMapping("/gestionPeriodesIndex")
	public String index(Model model) {
		return "redirect:/consulterPeriodes";
	}

	@RequestMapping("/consulterPeriodes")
	public String consulterPeriodes(Model model) {
		Collection<PeriodeFiltre> listePeriodesFiltres = merciquimetier.listePeriodeFiltre();

		model.addAttribute("listePeriodesFiltres", listePeriodesFiltres);
		return "GestionPeriodeView";
	}

	@PostMapping("/saisiePeriodeFiltre")
	public String saisiePeriodeFiltre(Model model, String nomPeriodeFiltre, Date dateDebutPeriodeFiltre, Date dateFinPeriodeFiltre) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateFinPeriodeFiltre);
		cal.set(Calendar.HOUR_OF_DAY, 23) ;
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		merciquimetier.creerPeriodeFiltre(new PeriodeFiltre(nomPeriodeFiltre,dateDebutPeriodeFiltre, cal.getTime()));
		return "redirect:/consulterPeriodes";

	}

	@PostMapping("/supprimerPeriodeFiltre") 
	public String supprimerPeriodeFiltre(Model model, Long idPeriodeFiltre) {
		merciquimetier.supprimerPeriodeFiltre(idPeriodeFiltre);
	return "redirect:/consulterPeriodes";
	}
}
