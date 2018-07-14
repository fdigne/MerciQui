package com.merciqui.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.merciqui.entities.Comedien;
import com.merciqui.entities.Evenement;
import com.merciqui.entities.PeriodeFiltre;
import com.merciqui.entities.Role;
import com.merciqui.entities.Spectacle;
import com.merciqui.metier.IMerciQuiMetier;

@Controller
public class RecapitulatifController {

	
	
	private static final Map <String, Collection<String>> mapListeMoisParPeriode = new HashMap<String, Collection<String>>() ;
	private static final Map<String, String> mapMoisCalendarInt = new HashMap<String, String>();
	static {
		Collection<String> automne = new ArrayList<String>();
		automne.add("Septembre");
		mapMoisCalendarInt.put("Septembre", String.valueOf(Calendar.SEPTEMBER));
		automne.add("Octobre");
		mapMoisCalendarInt.put("Octobre", String.valueOf(Calendar.OCTOBER));
		automne.add("Novembre");
		mapMoisCalendarInt.put("Novembre", String.valueOf(Calendar.NOVEMBER));
		automne.add("Décembre");
		mapMoisCalendarInt.put("Décembre", String.valueOf(Calendar.DECEMBER));
		automne.add("Janvier");
		mapMoisCalendarInt.put("Janvier", String.valueOf(Calendar.JANUARY));
		Collection<String> hiver = new ArrayList<String>();
		hiver.add("Février");
		mapMoisCalendarInt.put("Février", String.valueOf(Calendar.FEBRUARY));
		hiver.add("Mars");
		mapMoisCalendarInt.put("Mars", String.valueOf(Calendar.MARCH));
		hiver.add("Avril");
		mapMoisCalendarInt.put("Avril", String.valueOf(Calendar.APRIL));
		Collection<String> printemps = new ArrayList<String>();
		printemps.add("Mai");
		mapMoisCalendarInt.put("Mai", String.valueOf(Calendar.MAY));
		printemps.add("Juin");
		mapMoisCalendarInt.put("Juin", String.valueOf(Calendar.JUNE));
		printemps.add("Juillet");
		mapMoisCalendarInt.put("Juillet", String.valueOf(Calendar.JULY));
		Collection<String> ete = new ArrayList<String>();
		ete.add("Aout");
		mapMoisCalendarInt.put("Aout", String.valueOf(Calendar.AUGUST));

		Collection<String> allYear = new ArrayList<String>();
		allYear.addAll(hiver);
		allYear.addAll(printemps);
		allYear.addAll(automne);
		allYear.addAll(ete);
		mapListeMoisParPeriode.put("AllYear", allYear);
		mapListeMoisParPeriode.put("Automne", automne);
		mapListeMoisParPeriode.put("Hiver", hiver);
		mapListeMoisParPeriode.put("Printemps", printemps);
		mapListeMoisParPeriode.put("Ete", ete);

	};

	@Autowired
	IMerciQuiMetier merciquimetier ;

	@RequestMapping("/recapitulatifIndex")
	public String index(Model model, Long idPeriodeFiltre) {
		Collection<Comedien> listeComediens = merciquimetier.listeComediens();
		model.addAttribute("listeComediens", listeComediens);
		model.addAttribute("mapMoisCalendarInt", mapMoisCalendarInt);
		
		Collection<PeriodeFiltre> listePeriodesFiltres = merciquimetier.listePeriodeFiltre();
		model.addAttribute("listePeriodesFiltres", listePeriodesFiltres);
		model.addAttribute("idPeriodeFiltre",idPeriodeFiltre);

		Map<String, Integer> mapTotalDateParSpectacleParMois = new HashMap<String, Integer>();
		Map<String, Integer> mapTotalDateParSpectacleParComedien = new HashMap<String, Integer>();
		Map<String, Integer> mapTotalDateParComedienParMois = new HashMap<String, Integer>();
		Map<String, Integer> mapTotalDateParComedien = new HashMap<String, Integer>();

		ArrayList<String> listeMois = new ArrayList<String>();

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
			
			//Creation liste des mois dans l'intervalle 
			DateFormat formater = new SimpleDateFormat("MMM-yyyy");

	        Calendar beginCalendar = Calendar.getInstance();
	        Calendar finishCalendar = Calendar.getInstance();

	        beginCalendar.setTime(dateDebutFiltre);
			finishCalendar.setTime(dateFinFiltre);
			 while (beginCalendar.before(finishCalendar)) {
		            // add one month to date per loop
		            String date =     formater.format(beginCalendar.getTime()).toUpperCase();
		            System.out.println(date);
		            listeMois.add(date);
		            beginCalendar.add(Calendar.MONTH, 1);
		        }

		}
		model.addAttribute("listeMois", listeMois);

		Collection<Evenement> listeEvenementsFiltres = merciquimetier.listeEvenementsParPeriode(dateDebutFiltre, dateFinFiltre);
		
		//Calcul des spectacles pour chaque comédien
		Map<Comedien, Collection<Spectacle>> mapSpectaclesParComedien = new HashMap<Comedien, Collection<Spectacle>>();
		for (Spectacle spectacle : merciquimetier.listeSpectacles()) {
			for (Role role : spectacle.getListeRoles()) {
				if (mapSpectaclesParComedien.containsKey(role.getComedienTitulaire())) {
					if (! mapSpectaclesParComedien.get(role.getComedienTitulaire()).contains(role.getSpectacle())) {
						Collection<Spectacle> listeSpec = mapSpectaclesParComedien.get(role.getComedienTitulaire());
						listeSpec.add(role.getSpectacle());
						mapSpectaclesParComedien.put(role.getComedienTitulaire(), listeSpec);
					}
				}
				else {
					Collection<Spectacle> listeSpec = new ArrayList<Spectacle>();
					listeSpec.add(role.getSpectacle());
					mapSpectaclesParComedien.put(role.getComedienTitulaire(), listeSpec);
				}
				for (Comedien rempla : role.getListeRemplas()) {
					if (mapSpectaclesParComedien.containsKey(rempla)) {
						if (! mapSpectaclesParComedien.get(rempla).contains(role.getSpectacle())) {
							Collection<Spectacle> listeSpec = mapSpectaclesParComedien.get(rempla);
							listeSpec.add(role.getSpectacle());
							mapSpectaclesParComedien.put(rempla, listeSpec);
						}
					}
					else {
						Collection<Spectacle> listeSpec = new ArrayList<Spectacle>();
						listeSpec.add(role.getSpectacle());
						mapSpectaclesParComedien.put(rempla, listeSpec);	

					}
				}
			}
		}
		for (Evenement ev : merciquimetier.listeEvenements()) {

			for (Entry<Long, Comedien> entry :ev.getDistribution().entrySet()) {
				if (merciquimetier.consulterRole(entry.getKey()) != null) {
					if (! mapSpectaclesParComedien.get(entry.getValue()).contains(merciquimetier.consulterRole(entry.getKey()).getSpectacle())) {
						Collection<Spectacle> listeSpec = mapSpectaclesParComedien.get(entry.getValue());
						listeSpec.add(merciquimetier.consulterRole(entry.getKey()).getSpectacle());
						mapSpectaclesParComedien.put(entry.getValue(), listeSpec);
					}
				}
			}
		}
		//////TRAITEMENT DES MAPS POUR CHAQUE EVENEMENT FILTRE ET POUR CHAQUE COMEDIEN

		for (Evenement ev : listeEvenementsFiltres) {


			// Calcul du total de dates par comédien pour la période filtrée
			for (Entry<Long, Comedien> distrib : ev.getDistribution().entrySet()) {
				if(mapTotalDateParComedien.containsKey(distrib.getValue().getId3T())) {
					int nbreDatesParComedien = mapTotalDateParComedien.get(distrib.getValue().getId3T());
					nbreDatesParComedien = nbreDatesParComedien +1 ;
					mapTotalDateParComedien.put(distrib.getValue().getId3T(), nbreDatesParComedien);
				}
				else {
					mapTotalDateParComedien.put(distrib.getValue().getId3T(), 1);
				}
			}

			//Calcul du total de dates par Comédien et par spectacle pour période filtrée
			for (Entry<Long, Comedien> distrib : ev.getDistribution().entrySet()) {
				String keyMap = ev.getSpectacle().getNomSpectacle()+distrib.getValue().getId3T();
				if(mapTotalDateParSpectacleParComedien.containsKey(keyMap)) {
					int nbreDatesParSpectacleparComedien = mapTotalDateParSpectacleParComedien.get(keyMap);
					nbreDatesParSpectacleparComedien = nbreDatesParSpectacleparComedien +1 ;
					mapTotalDateParSpectacleParComedien.put(keyMap, nbreDatesParSpectacleparComedien);
				}
				else {
					mapTotalDateParSpectacleParComedien.put(keyMap, 1);
				}
			}
			//Calcul du total de date par comédien par mois pour la période filtrée
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(ev.getDateEvenement());
			String month = String.valueOf(cal1.get(Calendar.MONTH));
			for (Entry<Long, Comedien> distrib : ev.getDistribution().entrySet()) {
				String keyMap = distrib.getValue().getId3T()+month ;
				if (mapTotalDateParComedienParMois.containsKey(keyMap)) {
					int nbreDatesComMois = mapTotalDateParComedienParMois.get(keyMap);
					nbreDatesComMois = nbreDatesComMois+1 ;
					mapTotalDateParComedienParMois.put(keyMap, nbreDatesComMois);
				}
				else {
					mapTotalDateParComedienParMois.put(keyMap, 1);
				}


			}

			//Calcul du total de dates par mois par spectacle et par comédien
			for (Entry<Long, Comedien> distrib : ev.getDistribution().entrySet()) {
				String keyMap = distrib.getValue().getId3T()+ev.getSpectacle().getNomSpectacle()+month ;
				if (mapTotalDateParSpectacleParMois.containsKey(keyMap)){
					int nbreDates = mapTotalDateParSpectacleParMois.get(keyMap);
					nbreDates = nbreDates +1 ;
					mapTotalDateParSpectacleParMois.put(keyMap, nbreDates);
				}
				else {
					mapTotalDateParSpectacleParMois.put(keyMap, 1) ;
				}
			}

			//AJOUT DES ATTRIBUTES 
			model.addAttribute("mapTotalDateParSpectacleParMois", mapTotalDateParSpectacleParMois);
			model.addAttribute("mapTotalDateParComedienParMois", mapTotalDateParComedienParMois);
			model.addAttribute("mapTotalDateParSpectacleParComedien", mapTotalDateParSpectacleParComedien);
			model.addAttribute("mapTotalDateParComedien", mapTotalDateParComedien);
			model.addAttribute("mapSpectaclesParComedien",mapSpectaclesParComedien);
		}


		return "RecapitulatifView";
	}

}
