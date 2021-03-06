package com.merciqui.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.api.services.calendar.model.Event;
import com.merciqui.entities.Comedien;
import com.merciqui.entities.Evenement;
import com.merciqui.entities.PeriodeFiltre;
import com.merciqui.entities.Role;
import com.merciqui.entities.Spectacle;
import com.merciqui.metier.IMerciQuiMetier;

@Controller
public class GestionSpectaclesController {

	private static com.google.api.services.calendar.Calendar client;


	@Autowired
	IMerciQuiMetier merciquimetier ;

	@RequestMapping("/spectacleIndex")
	public String index(Model model) {
		return "redirect:/consulterSpectacle";
	}

	@RequestMapping("/consulterSpectacle")
	public String consulterSpectacle(Model model, String nomSpectacle, Long idPeriodeFiltre) {

		Collection<PeriodeFiltre> listePeriodesFiltres = merciquimetier.listePeriodeFiltre();
		model.addAttribute("listePeriodesFiltres", listePeriodesFiltres);
		model.addAttribute("idPeriodeFiltre",idPeriodeFiltre);

		Collection<String[]> itemsComediens = new ArrayList<String[]>() ;
		Collection<Evenement> listeEvenements37 = new ArrayList<Evenement>();
		Collection<Evenement> listeEvenements333 = new ArrayList<Evenement>();
		Map<Long, Integer> mapTotalDateParSpectacle = new HashMap<Long, Integer>();
		Map<Long, Collection<Comedien>> mapListeRemplasByRole = new HashMap<Long, Collection<Comedien>>();

		//TRAITEMENT PERIODE FILTER

		if(nomSpectacle != null) {
			Spectacle spec = merciquimetier.consulterSpectacle(nomSpectacle);
			model.addAttribute("spectacle", spec);
			Collection<Comedien> listeComediensParSpectacle = merciquimetier.getListeComediensParSpectacles(spec.getIdSpectacle());
			model.addAttribute("listeComediensParSpectacle", listeComediensParSpectacle);

			Collection<Role> listeRoles = merciquimetier.listeRolesParSpectacle(spec.getIdSpectacle());
			model.addAttribute("listeRoles", listeRoles);
			for(Role role : listeRoles) {
				if(merciquimetier.getListeRemplacants(role.getIdRole()) != null) {
					Collection<Comedien> listeRemplacants = merciquimetier.getListeRemplacants(role.getIdRole());

					mapListeRemplasByRole.put(role.getIdRole(), listeRemplacants);
					model.addAttribute("mapListeRemplasByRole",mapListeRemplasByRole );
				}

			}

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
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
			Collection<Evenement> listeEvenementsFiltres = new ArrayList<Evenement>();
			
			Collection<Evenement> listeEvenements = merciquimetier.listeEvenementsParSpectacle(spec.getIdSpectacle());

			for (Evenement evenementFiltre : listeEvenements) {
				Date dateEvenement = evenementFiltre.getDateEvenement();
				if(dateEvenement.compareTo(dateDebutFiltre)>0 && dateFinFiltre.compareTo(dateEvenement)> 0){	
					listeEvenementsFiltres.add(evenementFiltre);
				}
			}
			for (Comedien c : listeComediensParSpectacle) {
				mapTotalDateParSpectacle.put(c.getId3T(), 0);
			}

			for(Evenement ev : listeEvenementsFiltres) {
				for (Entry<Long, Comedien> entry : ev.getDistribution().entrySet()) {
					if (mapTotalDateParSpectacle.containsKey(entry.getValue().getId3T())) {
						int nbreDates = mapTotalDateParSpectacle.get(entry.getValue().getId3T());
						nbreDates = nbreDates +1 ;
						mapTotalDateParSpectacle.put(entry.getValue().getId3T(), nbreDates);	
					}
					else {
						mapTotalDateParSpectacle.put(entry.getValue().getId3T(),1);
					}

				}


				if(ev.getCompagnie().equals("Compagnie 37")) {
					listeEvenements37.add(ev);
				}
				else {
					listeEvenements333.add(ev);
				}
			}
			model.addAttribute("mapTotalDate", mapTotalDateParSpectacle);
			model.addAttribute("listeEvenements37", listeEvenements37);
			model.addAttribute("listeEvenements333", listeEvenements333);


		}

		model.addAttribute("nomSpectacle", nomSpectacle);
		Collection<Comedien> listeComediens = merciquimetier.listeComediens();
		for(Comedien c : listeComediens) {
			itemsComediens.add(new String[] {String.valueOf(c.getId3T()), c.getNomPersonne(), c.getPrenomPersonne()});

		}
		model.addAttribute("listeComediens", listeComediens);		
		model.addAttribute("itemsComediens", itemsComediens) ;


		Collection<Spectacle> listeSpectacles = merciquimetier.listeSpectacles();
		model.addAttribute("listeSpectacles", listeSpectacles);



		return "SpectacleView";
	}

	@PostMapping("/saisieSpectacle")
	public String saisieSpectacle(Model model, String nomSpectacle, String[] nomRole, String[] id3T, String[] id3TRempl) {
		Spectacle spectacle = new Spectacle();
		spectacle.setNomSpectacle(nomSpectacle);

		int indexRole = 0 ;

		Map<String, Comedien> listeRemplas = new HashMap<String, Comedien>();
		merciquimetier.creerSpectacle(spectacle);
		for(String s : nomRole) {

			Role role = new Role() ;
			if (id3T[indexRole].equals("Pas de comédien titulaire")) {
				role.setComedienTitulaire(null);
			}
			else {
				Comedien comedien = merciquimetier.consulterComedien(Long.valueOf(id3T[indexRole])) ;
				role.setComedienTitulaire(comedien);	
			}
			role.setNomRole(s);
			role.setSpectacle(merciquimetier.consulterSpectacle(nomSpectacle));
			Set<Comedien> listeRemplasSQL = new HashSet<Comedien>();
			if(id3TRempl != null) {
				for(String r : id3TRempl) {
					String[] id3TR = r.split("\\.");
					if (id3TR[0].equals(String.valueOf(indexRole))) {
						Comedien remp = merciquimetier.consulterComedien(Long.valueOf(id3TR[1])) ;
						listeRemplas.put(s, remp);
						listeRemplasSQL.add(remp);
					}
				}

				role.setListeRemplas(listeRemplasSQL);	
			}
			indexRole++ ;
			merciquimetier.creerRole(role);

		}

		return "redirect:/consulterSpectacle?nomSpectacle="+nomSpectacle ;
	}

	@PostMapping("/modifierSpectacle")
	public String modifierSpectacle(Model model, String nomSpectacle,String nouveauNomSpectacle, String[] nomRoleModif, String[] id3TModif, String[] id3TRemplModif) {
		Spectacle spec = merciquimetier.consulterSpectacle(nomSpectacle);
		spec.setNomSpectacle(nouveauNomSpectacle);
		merciquimetier.creerSpectacle(spec); //modifie le nom du spectacle
		int indexRole = 0 ;
		//Map<String, Comedien> listeRemplas = new HashMap<String, Comedien>();
		for(Role s : spec.getListeRoles()) {
			Map<String, Comedien> listeRemplas = new HashMap<String, Comedien>();		
			if (id3TModif[indexRole].equals("Pas de comédien titulaire")) {
				s.setComedienTitulaire(null);
			}
			else {
				Comedien comedien = merciquimetier.consulterComedien(Long.valueOf(id3TModif[indexRole])) ;
				s.setComedienTitulaire(comedien);	
			}

			Set<Comedien> listeRemplasSQL = new HashSet<Comedien>();
			if(id3TRemplModif != null) {
				for(String r : id3TRemplModif) {
					String[] key = r.split("\\.");
					if (s.getNomRole().equals(key[0])) {
						if (! listeRemplas.containsKey(key[1])) {
							Comedien remp = merciquimetier.consulterComedien(Long.valueOf(key[1])) ;
							listeRemplas.put(key[1], remp);
							listeRemplasSQL.add(remp);
						}
					}
				}

				s.setListeRemplas(listeRemplasSQL);	
			}
			else {
				s.setListeRemplas(null);
			}
			indexRole++ ;

			merciquimetier.creerRole(s);

		}

		//MODIFICATIONS GOOGLE CALENDAR
		client = new com.google.api.services.calendar.Calendar.Builder(GestionAgendaController.httpTransport, GestionAgendaController.JSON_FACTORY, GestionAgendaController.credential)
				.setApplicationName(GestionAgendaController.APPLICATION_NAME).build();

		Spectacle spect = merciquimetier.consulterSpectacle(nouveauNomSpectacle);
		Collection<Evenement> listeEvenements = merciquimetier.listeEvenementsParSpectacle(spect.getIdSpectacle());
		for (Evenement ev : listeEvenements) {
			try {
				Event myEvent = client.events().get("primary", ev.getIdEvenement()).execute();
				myEvent.setSummary(nouveauNomSpectacle) ;
				client.events().patch("primary",ev.getIdEvenement(), myEvent).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return "redirect:/consulterSpectacle?nomSpectacle="+nouveauNomSpectacle ;
	}

	@PostMapping("/supprimerSpectacle")
	public String supprimerSpectacle(Model model, String nomSpectacle) {
		client = new com.google.api.services.calendar.Calendar.Builder(GestionAgendaController.httpTransport, GestionAgendaController.JSON_FACTORY, GestionAgendaController.credential)
				.setApplicationName(GestionAgendaController.APPLICATION_NAME).build();
		Spectacle spectacle = merciquimetier.consulterSpectacle(nomSpectacle);
		Collection<Evenement> listeEvenements = merciquimetier.listeEvenementsParSpectacle(spectacle.getIdSpectacle());
		for (Evenement ev : listeEvenements) {
			try {
				client.events().delete("primary", ev.getIdEvenement()).setSendNotifications(false).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
			merciquimetier.supprimerEvenement(ev);
		}
		merciquimetier.supprimerSpectacle(nomSpectacle);	

		return "redirect:/consulterSpectacle" ;	
	}



}
