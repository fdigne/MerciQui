package com.merciqui.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.util.DateTime;
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
	public String saisiePeriodeFiltre(Model model, String nomPeriodeFiltre, String dateDebutPeriodeFiltre, String dateFinPeriodeFiltre) {
		
		//Mise au format mysql des dates
		Date dateDebutPeriodeFiltreRFC = this.formatDateToRFC3339(dateDebutPeriodeFiltre+" 0:00:00") ;
		Date dateFinPeriodeFiltreRFC = this.formatDateToRFC3339(dateFinPeriodeFiltre+" 23:59:59");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateFinPeriodeFiltreRFC);
		cal.set(Calendar.HOUR_OF_DAY, 23) ;
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		
		
		merciquimetier.creerPeriodeFiltre(new PeriodeFiltre(nomPeriodeFiltre,dateDebutPeriodeFiltreRFC, cal.getTime()));
		return "redirect:/consulterPeriodes";

	}

	@PostMapping("/supprimerPeriodeFiltre") 
	public String supprimerPeriodeFiltre(Model model, Long idPeriodeFiltre) {
		merciquimetier.supprimerPeriodeFiltre(idPeriodeFiltre);
	return "redirect:/consulterPeriodes";
	}
	
	private Date formatDateToRFC3339(String datePeriode) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		java.util.Date utilDate;
		try {
			utilDate = sdf.parse(datePeriode);
			return utilDate ;
		} catch (ParseException e) {
			e.printStackTrace();
			return null ;
		}
	}
}
