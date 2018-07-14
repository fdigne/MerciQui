package com.merciqui.web;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.merciqui.entities.Comedien;
import com.merciqui.entities.Evenement;
import com.merciqui.entities.Periode;
import com.merciqui.entities.PeriodeFiltre;
import com.merciqui.entities.Spectacle;
import com.merciqui.metier.IMerciQuiMetier;


@Controller
public class GestionComediensController {

	private static final Map<String, Integer>seasons = new HashMap<String, Integer>() ;
	static {
		seasons.put("AutomneDebut" , Calendar.SEPTEMBER);
		seasons.put("AutomneFin" , Calendar.JANUARY);
		seasons.put("HiverDebut" , Calendar.FEBRUARY);
		seasons.put("HiverFin" , Calendar.APRIL);
		seasons.put("PrintempsDebut" , Calendar.MAY);
		seasons.put("PrintempsFin" , Calendar.JULY);
		seasons.put("EteDebut" , Calendar.AUGUST);
		seasons.put("EteFin" , Calendar.AUGUST);


	};


	@Autowired
	IMerciQuiMetier merciquimetier ;

	@RequestMapping("/comedienIndex")
	public String index(Model model, String id3T) {
		return "redirect:/consulterComedien";
	}

	@RequestMapping("/consulterComedien")
	public String consulter(Model model, String id3T, Long idPeriodeFiltre) {
		Collection<PeriodeFiltre> listePeriodesFiltres = merciquimetier.listePeriodeFiltre();
		model.addAttribute("listePeriodesFiltres", listePeriodesFiltres);
		model.addAttribute("idPeriodeFiltre",idPeriodeFiltre);
		model.addAttribute("id3T", id3T);
		Map<String, Integer> mapTotalDateParSpectacle37 = new HashMap<String, Integer>();
		Map<String, Integer> mapTotalDateParSpectacle333 = new HashMap<String, Integer>();

		List<Comedien> listeComediens = (List<Comedien>) merciquimetier.listeComediens();
		model.addAttribute("listeComediens", listeComediens);
		try {
			if(id3T != null) {
				Comedien com = merciquimetier.consulterComedien(id3T);
				model.addAttribute("comedien", com);

				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
				cal.set(Calendar.MONTH, Calendar.JANUARY);
				cal.set(Calendar.DAY_OF_MONTH,1);
				cal.set(Calendar.HOUR_OF_DAY,  0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);

				Date dateDebutFiltre = cal.getTime();
				cal.set(Calendar.MONTH, Calendar.DECEMBER);
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);

				Date dateFinFiltre = cal.getTime();

				if (idPeriodeFiltre != null) {

					PeriodeFiltre periodeFiltre = merciquimetier.consulterPeriodeFiltre(idPeriodeFiltre);
					dateDebutFiltre = periodeFiltre.getDateDebut();
					dateFinFiltre = periodeFiltre.getDateFin();
				}
				
				Collection<BigInteger> listeSpectacles37 = merciquimetier.listeSpectacleParComedienParPeriodeParCompagnie(id3T, dateDebutFiltre, dateFinFiltre, "Compagnie 37");
				Collection<BigInteger> listeSpectacles333 = merciquimetier.listeSpectacleParComedienParPeriodeParCompagnie(id3T, dateDebutFiltre, dateFinFiltre, "Compagnie 333+1");
				int nbreDatesTotal = 0 ;
				for (BigInteger idSpectacle : listeSpectacles37) {
					int totalDates37 = merciquimetier.getNombreDatesparComedienParSpectacleParPeriodeParCompagnie(id3T, idSpectacle.longValue(), dateDebutFiltre, dateFinFiltre, "Compagnie 37");
					mapTotalDateParSpectacle37.put(merciquimetier.consulterSpectacle(idSpectacle.longValue()).getNomSpectacle(), totalDates37);
					nbreDatesTotal += totalDates37;
				}
				
				for (BigInteger idSpectacle : listeSpectacles333) {
					int totalDates333 = merciquimetier.getNombreDatesparComedienParSpectacleParPeriodeParCompagnie(id3T, idSpectacle.longValue(), dateDebutFiltre, dateFinFiltre, "Compagnie 333+1");
					mapTotalDateParSpectacle333.put(merciquimetier.consulterSpectacle(idSpectacle.longValue()).getNomSpectacle(), totalDates333);
					nbreDatesTotal += totalDates333;
				}
				
				model.addAttribute("mapTotalDatesSpectacle37", mapTotalDateParSpectacle37);
				model.addAttribute("mapTotalDatesSpectacle333", mapTotalDateParSpectacle333);
				model.addAttribute("nbreDates", nbreDatesTotal);

				Collection<Periode> listePeriodeIndispos = com.getListeIndispos();
				Collection<Periode> listeVacances = new ArrayList<Periode>();
				for (Periode periode : listePeriodeIndispos) {
					if (periode.isVacances()) {
						listeVacances.add(periode);
					}
				}
				model.addAttribute("listeIndispos", listeVacances);


			}	
		} catch (Exception e) {

			model.addAttribute("exception",e);
		}

		return "ComedienView";

	}

	@PostMapping("/saisieComedien")
	public String saisieComedien(Model model, String nomComedien, String prenomComedien,
			String dateNaissanceComedien, String numSecuComedien, String sexeComedien, String adressePostaleComedien,
			String adresseEmailComedien, String numTelComedien) {
		String id3T = numSecuComedien;
		try {
			Comedien com = new Comedien();
			com.setAdresseEmail(adresseEmailComedien);
			com.setAdressePostale(adressePostaleComedien);
			com.setId3T(numSecuComedien);
			com.setNomPersonne(nomComedien);
			com.setPrenomPersonne(prenomComedien);
			com.setNumTel(numTelComedien);
			com.setSexe(sexeComedien);
			com.setDateNaissance(mefDateNaissance(dateNaissanceComedien));

			merciquimetier.creerComedien(com);
		}
		catch (Exception e) {
			model.addAttribute("error", e);
			return "redirect:/consulterComedien?id3T="+id3T+"&error="+e.getMessage() ;
		}

		return "redirect:/consulterComedien?id3T="+id3T ;
	}

	@PostMapping("/modifierComedien")
	public String modifierComedien(Model model, String nomComedien, String prenomComedien,
			String dateNaissanceComedien, String numSecuComedien, String sexeComedien, String adressePostaleComedien,
			String adresseEmailComedien, String numTelComedien) {
		String id3T = numSecuComedien;
		Comedien comedien = merciquimetier.consulterComedien(numSecuComedien);
		comedien.setAdresseEmail(adresseEmailComedien);
		comedien.setAdressePostale(adressePostaleComedien);
		comedien.setDateNaissance(mefDateNaissance(dateNaissanceComedien));
		comedien.setId3T(numSecuComedien);
		comedien.setNomPersonne(nomComedien);
		comedien.setPrenomPersonne(prenomComedien);
		comedien.setNumTel(numTelComedien);
		comedien.setSexe(sexeComedien);

		merciquimetier.creerComedien(comedien);

		return "redirect:/consulterComedien?id3T="+id3T ;
	}

	@PostMapping("/supprimerComedien")
	public String supprimerComedien(Model model, String id3T) {
		merciquimetier.supprimerComedien(id3T) ;	
		return "redirect:/consulterComedien" ;	
	}

	@PostMapping("/ajouterIndispo")
	public String ajouterIndispo(Model model, String id3T, String dateDebutIndispo, String dateFinIndispo) {
		Comedien comedien = merciquimetier.consulterComedien(id3T);
		Date dateDebut = mefDateNaissance(dateDebutIndispo) ;
		Date dateFin = mefDateNaissance(dateFinIndispo) ;
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateFin);
		cal.set(Calendar.HOUR_OF_DAY,  23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59); 
		dateFin = cal.getTime();
		System.out.println(dateFin);
		Periode indispo = new Periode(dateDebut, dateFin);
		indispo.setVacances(true);
		merciquimetier.creerPeriode(indispo);
		Collection<Periode> listeIndispos = comedien.getListeIndispos();
		listeIndispos.add(indispo);
		merciquimetier.creerComedien(comedien);

		return "redirect:/consulterComedien?id3T="+id3T ;	
	}

	@PostMapping("/supprimerIndispo")
	public String supprimerIndispo(Model model, String id3T, String idPeriode) {
		Comedien comedien = merciquimetier.consulterComedien(id3T);
		Set<Periode> periodes = comedien.getListeIndispos();
		periodes.remove(merciquimetier.consulterPeriode(Long.valueOf(idPeriode)));
		comedien.setListeIndispos(periodes);
		merciquimetier.creerComedien(comedien);
		merciquimetier.supprimerPeriode(Long.valueOf(idPeriode));

		return "redirect:/consulterComedien?id3T="+id3T ;		
	}



	private Date mefDateNaissance(String dateNaissance) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date utilDate;
		try {
			utilDate = sdf.parse(dateNaissance);
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());	
			return sqlDate;	
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}




