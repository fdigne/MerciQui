package com.merciqui.web;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.merciqui.entities.Comedien;
import com.merciqui.entities.Evenement;
import com.merciqui.entities.Periode;
import com.merciqui.entities.PeriodeFiltre;
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
	public String consulter(Model model, Long id3T, Long idPeriodeFiltre, String error) {
		Collection<PeriodeFiltre> listePeriodesFiltres = merciquimetier.listePeriodeFiltre();
		model.addAttribute("listePeriodesFiltres", listePeriodesFiltres);
		model.addAttribute("idPeriodeFiltre",idPeriodeFiltre);
		model.addAttribute("id3T", id3T);
		model.addAttribute("error", error);
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
				Collection<Evenement> evenementsFiltres37 = merciquimetier.listeEvenementsParComedienParPeriodeParCompagnie(id3T, dateDebutFiltre, dateFinFiltre, "Compagnie 37");
				Collection<Evenement> evenementsFiltres333 = merciquimetier.listeEvenementsParComedienParPeriodeParCompagnie(id3T, dateDebutFiltre, dateFinFiltre, "Compagnie 333+1");
				model.addAttribute("listeEvenements37", evenementsFiltres37);
				model.addAttribute("listeEvenements333", evenementsFiltres333);
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
		//String id3T = numSecuComedien;
		try {
			Comedien com = new Comedien();
			com.setAdresseEmail(adresseEmailComedien);
			com.setAdressePostale(adressePostaleComedien);
			//com.setId3T(numSecuComedien.trim());
			com.setNomPersonne(nomComedien);
			com.setPrenomPersonne(prenomComedien);
			com.setNumTel(numTelComedien);
			com.setSexe(sexeComedien);
			com.setDateNaissance(mefDateNaissance(dateNaissanceComedien));

			Comedien comSaved = merciquimetier.creerComedien(com);
			return "redirect:/consulterComedien?id3T="+comSaved.getId3T() ;
		}
		catch (Exception e) {
			model.addAttribute("error", e);
			return "redirect:/consulterComedien?error="+e.getMessage() ;
		}		
	}

	@PostMapping("/modifierComedien")
	public String modifierComedien(Model model, String nomComedien, String prenomComedien,
			String dateNaissanceComedien, String numSecuComedien, String sexeComedien, String adressePostaleComedien,
			String adresseEmailComedien, String numTelComedien, Long id3T) {
		Comedien comedien = merciquimetier.consulterComedien(id3T);
		comedien.setAdresseEmail(adresseEmailComedien);
		comedien.setAdressePostale(adressePostaleComedien);
		comedien.setDateNaissance(mefDateNaissance(dateNaissanceComedien));
		//comedien.setId3T(numSecuComedien);
		comedien.setNomPersonne(nomComedien);
		comedien.setPrenomPersonne(prenomComedien);
		comedien.setNumTel(numTelComedien);
		comedien.setSexe(sexeComedien);

		merciquimetier.creerComedien(comedien);

		return "redirect:/consulterComedien?id3T="+comedien.getId3T() ;
	}

	@PostMapping("/supprimerComedien")
	public String supprimerComedien(Model model, Long id3T) {
		int evenementFuturs = merciquimetier.existeEvenementFuturParComedien(id3T);
		if (evenementFuturs > 0) {
			return "redirect:/consulterComedien?id3T="+id3T+"&error=Comedien programme sur des evenements futurs" ;	
		}
		else {
			Collection<Evenement> listeEvenement = merciquimetier.listeEvenementsParComedien(id3T);
			
			for (Evenement ev : listeEvenement) {
				merciquimetier.supprimerEvenement(ev);
				
			}
			merciquimetier.supprimerComedien(id3T) ;	
			return "redirect:/consulterComedien" ;	
		}
		
	}

	@PostMapping("/ajouterIndispo")
	public String ajouterIndispo(Model model, Long id3T, String dateDebutIndispo, String dateFinIndispo) {
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

		return "redirect:/consulterComedien?id3T="+comedien.getId3T() ;	
	}

	@PostMapping("/supprimerIndispo")
	public String supprimerIndispo(Model model, Long id3T, String idPeriode) {
		Comedien comedien = merciquimetier.consulterComedien(id3T);
		Collection<Periode> periodes = comedien.getListeIndispos();
		periodes.remove(merciquimetier.consulterPeriode(Long.valueOf(idPeriode)));
		comedien.setListeIndispos(periodes);
		merciquimetier.creerComedien(comedien);
		merciquimetier.supprimerPeriode(Long.valueOf(idPeriode));

		return "redirect:/consulterComedien?id3T="+comedien.getId3T();		
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




