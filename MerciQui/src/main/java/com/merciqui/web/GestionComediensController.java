package com.merciqui.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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
import com.merciqui.metier.IMerciQuiMetier;


@Controller
public class GestionComediensController {
	
	private static final Map<String, Integer>seasons = new HashMap<String, Integer>() ;
	static {
    seasons.put("AutomneDebut" , Calendar.SEPTEMBER);
    seasons.put("AutomneFin" , Calendar.DECEMBER);
    seasons.put("HiverDebut" , Calendar.JANUARY);
    seasons.put("HiverFin" , Calendar.MARCH);
    seasons.put("PrintempsDebut" , Calendar.APRIL);
    seasons.put("PrintempsFin" , Calendar.JUNE);
    seasons.put("EteDebut" , Calendar.JULY);
    seasons.put("EteFin" , Calendar.AUGUST);

    
};


	@Autowired
	IMerciQuiMetier merciquimetier ;
	
	@RequestMapping("/comedienIndex")
	public String index(Model model, String id3T) {
		return "redirect:/consulterComedien";
	}
	
	@RequestMapping("/consulterComedien")
	public String consulter(Model model, String id3T, String yearFilter, String monthFilter, String periodFilter) {
		model.addAttribute("id3T", id3T);
		Collection<Evenement> listeEvenements37 = new ArrayList<Evenement>();
		Collection<Evenement> listeEvenements333 = new ArrayList<Evenement>();
		Map<String, Integer> mapTotalDateParSpectacle37 = new HashMap<String, Integer>();
		Map<String, Integer> mapTotalDateParSpectacle333 = new HashMap<String, Integer>();

		model.addAttribute("yearFilter", yearFilter);
		model.addAttribute("monthFilter", monthFilter);
		model.addAttribute("periodFilter", periodFilter);
		List<Comedien> listeComediens = (List<Comedien>) merciquimetier.listeComediens();
		model.addAttribute("listeComediens", listeComediens);
		try {
			if(id3T != null) {
				Comedien com = merciquimetier.consulterComedien(id3T);
				model.addAttribute("comedien", com);
				Collection<Evenement> listeEvenementParComedien = merciquimetier.listeEvenementsParComedien(id3T);
				if(yearFilter == null) {
					yearFilter = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
					
				}
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, Integer.valueOf(yearFilter));
				cal.set(Calendar.MONTH, Calendar.JANUARY);
				cal.set(Calendar.DAY_OF_MONTH,1);
				cal.set(Calendar.HOUR_OF_DAY,  0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				if(monthFilter != null) {
					cal.set(Calendar.MONTH, Integer.valueOf(monthFilter));
				}
				if(periodFilter != null) {
					cal.set(Calendar.MONTH, seasons.get(periodFilter+"Debut"));
				}
				
				Date dateDebutFiltre = cal.getTime();
				cal.set(Calendar.MONTH, Calendar.DECEMBER);
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				if(monthFilter != null) {
					cal.set(Calendar.MONTH, Integer.valueOf(monthFilter));
				}
				if(periodFilter != null) {
					cal.set(Calendar.MONTH, seasons.get(periodFilter+"Fin"));
				}
				
				
				Date dateFinFiltre = cal.getTime();
				Collection<Evenement> listeEvenementsFiltres = new ArrayList<Evenement>();
				
				for (Evenement evenementFiltre : listeEvenementParComedien) {
					Date dateEvenement = evenementFiltre.getDateEvenement();
						if(dateEvenement.compareTo(dateDebutFiltre)>0 && dateFinFiltre.compareTo(dateEvenement)> 0){	
							listeEvenementsFiltres.add(evenementFiltre);
						}
						
				for (Evenement ev : listeEvenementsFiltres) {
					if(ev.getCompagnie().equals("Compagnie 37")) {
						
						listeEvenements37.add(ev);
					}
					else {
						listeEvenements333.add(ev);
					}
				}
				model.addAttribute("listeEvenements37", listeEvenements37);
				model.addAttribute("listeEvenements333", listeEvenements333);
			}
			
			for(Evenement ev : listeEvenements37) {
				int totalDates37 = merciquimetier.getNombreDatesParComedienParEvenement(ev.getSpectacle().getIdSpectacle(), id3T, ev.getNomSalle(), dateDebutFiltre, dateFinFiltre);
				mapTotalDateParSpectacle37.put(ev.getSpectacle().getNomSpectacle(), totalDates37);	
			}
			for(Evenement ev : listeEvenements333) {
				int totalDates333 = merciquimetier.getNombreDatesParComedienParEvenement(ev.getSpectacle().getIdSpectacle(), id3T, ev.getNomSalle(), dateDebutFiltre, dateFinFiltre);
				mapTotalDateParSpectacle333.put(ev.getSpectacle().getNomSpectacle(), totalDates333);
				
			}
			
			
			model.addAttribute("mapTotalDatesSpectacle37", mapTotalDateParSpectacle37);
			model.addAttribute("mapTotalDatesSpectacle333", mapTotalDateParSpectacle333);
			model.addAttribute("nbreDates", String.valueOf(listeEvenementsFiltres.size()));
			
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
