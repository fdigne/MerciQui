package com.merciqui.web;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

import groovy.lang.Tuple;


@Controller
public class RecapitulatifController {

	@Autowired
	IMerciQuiMetier merciquimetier ;

	@RequestMapping("/recapitulatifIndex")
	public String index(Model model, Long idPeriodeFiltre) {
		Collection<Comedien> listeComediens = merciquimetier.listeComediens();
		model.addAttribute("listeComediens", listeComediens);

		Collection<PeriodeFiltre> listePeriodesFiltres = merciquimetier.listePeriodeFiltre();
		model.addAttribute("listePeriodesFiltres", listePeriodesFiltres);
		model.addAttribute("idPeriodeFiltre",idPeriodeFiltre);

		Map<String, Integer> mapTotalDateParSpectacleParMois = new HashMap<String, Integer>();
		Map<String, Integer> mapTotalDateParSpectacleParComedien = new HashMap<String, Integer>();
		Map<String, Integer> mapTotalDateParComedienParMois = new HashMap<String, Integer>();
		Map<String, Integer> mapTotalDateParComedien = new HashMap<String, Integer>();

		Map<Comedien, Collection<Spectacle>> mapSpectaclesParComedien = new HashMap<Comedien, Collection<Spectacle>>();


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
				listeMois.add(date);
				beginCalendar.add(Calendar.MONTH, 1);
			}

		}

		model.addAttribute("listeMois", listeMois);

		Collection<Evenement> listeEvenementsFiltres = merciquimetier.listeEvenementsParPeriode(dateDebutFiltre, dateFinFiltre);


		//////TRAITEMENT DES MAPS POUR CHAQUE EVENEMENT FILTRE ET POUR CHAQUE COMEDIEN


		for (Evenement ev : listeEvenementsFiltres) {

			// Calcul du total de dates par comédien pour la période filtrée
			for (Entry<Long, Comedien> distrib : ev.getDistribution().entrySet()) {
				//Récupération liste spectacles pour chaque comédien
				Collection<Spectacle> listeSpec = new ArrayList<Spectacle>();
				Collection<BigInteger> listeSpecId = merciquimetier.listeSpectacleParComedienParPeriode(distrib.getValue().getId3T(), dateDebutFiltre, dateFinFiltre);
				for (BigInteger specid : listeSpecId) {
					listeSpec.add(merciquimetier.consulterSpectacle(specid.longValue()));
				}
				mapSpectaclesParComedien.put(distrib.getValue(), listeSpec);
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

			DateFormat formater = new SimpleDateFormat("MMM-yyyy");
			String month = formater.format(ev.getDateEvenement().getTime()).toUpperCase();
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
				//Calcul du total de dates par mois par spectacle et par comédien

				String keyMap2 = distrib.getValue().getId3T()+ev.getSpectacle().getNomSpectacle()+month ;
				if (mapTotalDateParSpectacleParMois.containsKey(keyMap2)){
					int nbreDates = mapTotalDateParSpectacleParMois.get(keyMap2);
					nbreDates = nbreDates +1 ;
					mapTotalDateParSpectacleParMois.put(keyMap2, nbreDates);
				}
				else {
					mapTotalDateParSpectacleParMois.put(keyMap2, 1) ;
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
