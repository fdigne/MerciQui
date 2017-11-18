package com.merciqui.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.merciqui.entities.Spectacle;
import com.merciqui.metier.IMerciQuiMetier;

@Controller
public class GestionComediensController {

	@Autowired
	IMerciQuiMetier merciquimetier ;
	
	@RequestMapping("/comedienIndex")
	public String index(Model model, String id3T) {
		return "redirect:/consulterComedien";
	}
	
	@RequestMapping("/consulterComedien")
	public String consulter(Model model, String id3T) {
		model.addAttribute("id3T", id3T);
		Collection<Evenement> listeEvenements37 = new ArrayList<Evenement>();
		Collection<Evenement> listeEvenements333 = new ArrayList<Evenement>();
		Map<String, Integer> mapTotalDateParSpectacle37 = new HashMap<String, Integer>();
		Map<String, Integer> mapTotalDateParSpectacle333 = new HashMap<String, Integer>();

		try {
			if(id3T != null) {
				Comedien com = merciquimetier.consulterComedien(id3T);
				model.addAttribute("comedien", com);
				int nbrDates = merciquimetier.getNombreDatesTotal(id3T);
				model.addAttribute("nbreDates", String.valueOf(nbrDates));
				Collection<Evenement> listeEvenementParComedien = merciquimetier.listeEvenementsParComedien(id3T);
				
				for (Evenement ev : listeEvenementParComedien) {
					if(ev.getNomSalle().equals("3T") || ev.getNomSalle().equals("PRIVÉ")) {
						
						listeEvenements37.add(ev);
					}
					else {
						listeEvenements333.add(ev);
					}
				}
				model.addAttribute("listeEvenements37", listeEvenements37);
				model.addAttribute("listeEvenements333", listeEvenements333);
			}
			
			List<Comedien> listeComediens = (List<Comedien>) merciquimetier.listeComediens();
			model.addAttribute("listeComediens", listeComediens);
			
			for(Evenement ev : listeEvenements37) {
				int totalDates = merciquimetier.getNombreDatesParComedienParEvenement(ev.getSpectacle().getIdSpectacle(), id3T, ev.getNomSalle());
				mapTotalDateParSpectacle37.put(ev.getSpectacle().getNomSpectacle(), totalDates);

				
			}
			for(Evenement ev : listeEvenements333) {
				int totalDates = merciquimetier.getNombreDatesParComedienParEvenement(ev.getSpectacle().getIdSpectacle(), id3T, ev.getNomSalle());
				mapTotalDateParSpectacle333.put(ev.getSpectacle().getNomSpectacle(), totalDates);
			}
			model.addAttribute("mapTotalDatesSpectacle37", mapTotalDateParSpectacle37);
			model.addAttribute("mapTotalDatesSpectacle333", mapTotalDateParSpectacle333);
			
			
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
	
	@PostMapping("/supprimerComedien")
	public String supprimerComedien(Model model, String id3T) {
		merciquimetier.supprimerComedien(id3T);	
		return "redirect:/consulterComedien" ;	
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
