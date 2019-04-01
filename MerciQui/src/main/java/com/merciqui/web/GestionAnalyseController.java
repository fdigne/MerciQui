package com.merciqui.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.merciqui.entities.Comedien;
import com.merciqui.entities.Evenement;
import com.merciqui.metier.IMerciQuiMetier;

@Controller
public class GestionAnalyseController {

	public final static Log logger = LogFactory.getLog(GestionAgendaController.class);
	public static final String APPLICATION_NAME = "Merci Qui";
	public static HttpTransport httpTransport;
	public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static com.google.api.services.calendar.Calendar client;
	GoogleClientSecrets clientSecrets;
	GoogleAuthorizationCodeFlow flow;
	static Credential credential;


	@Value("${google.client.client-id}")
	private String clientId;
	@Value("${google.client.client-secret}")
	private String clientSecret;
	@Value("${google.client.redirectUri}")
	private String redirectURI;

	private static final String NO_ERROR = "Aucune anomalie détectée.";



	@Autowired
	IMerciQuiMetier merciquimetier ;

	@RequestMapping("/gestionAnalyseIndex")
	public String index(Model model) {
		return "redirect:/consulterAnalyses";
	}

	@RequestMapping("/consulterAnalyses")
	public String consulterAnalyses(Model model) throws IOException, GeneralSecurityException {

		client = new com.google.api.services.calendar.Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, GestionAgendaController.credential)
				.setApplicationName(APPLICATION_NAME).build();
		Collection<Event> listEvents = new ArrayList<Event>() ; 
		Events googleEvents = null;
		googleEvents = client.events().list("primary").execute();

		for (Event e : googleEvents.getItems()) {
			listEvents.add(e);

		}
		Collection<RepairIndispo> mapRepair = new ArrayList<RepairIndispo>();
		Map<String, String> mapErreurs = new HashMap<String, String>();
		Map<Long, Long> mapRepairIndispo = new HashMap<Long, Long>();
		Collection<Evenement> listeEvenements = merciquimetier.listeEvenements();
		Collection<String> evenementDB = new ArrayList<String>();
		for (Evenement ev : listeEvenements) {
			for (Comedien com : ev.getDistribution().values()) {
				// Check si le comedien est bien indispo sur la periode de l'évènement
				if (! com.getListeIndispos().contains(ev.getPeriode())) {
					String message = "Comedien " + com.getId3T() + " doit avoir periode "+ ev.getPeriode().getIdPeriode() + " dans ses indispos";
					mapErreurs.put(ev.getIdEvenement(), message);
					mapRepairIndispo.put(ev.getPeriode().getIdPeriode(), com.getId3T());
					RepairIndispo mri = new RepairIndispo();
					mri.setComedien(com.getId3T());
					mri.setPeriode(ev.getPeriode().getIdPeriode());
					mapRepair.add(mri);
				}
			}

			evenementDB.add(ev.getIdEvenement());
		}
		
		for (Event ev : listEvents) {
			if (! evenementDB.contains(ev.getId())) {
			//	Date dateEvent = new Date(ev.getStart().getDateTime().getValue());
			//	if (dateEvent.before(dateToCompare)) {
					String message = "Evenement " + ev.getStart() + "n'apparaît que dans Google Agenda. A recréer en base.";
					mapErreurs.put(ev.getId(), message);
			//	}
			}
		}
		if (mapErreurs != null && !mapErreurs.isEmpty()) {
			String alerte = mapErreurs.size() + " anomalies ont été détectées.";
			model.addAttribute("alerte", alerte);
			model.addAttribute("mapErreurs",mapErreurs);
			model.addAttribute("listRepairIndispo", new ListRepairIndispos(mapRepair));
		}
		else {
			model.addAttribute("no_error", NO_ERROR);
		}

		return "AnalyseView";
	}

	@PostMapping(value="/reparerAnomalie")
	public String reparerAnomalies(Model model, String[] listRepairIndispoInput) {

		if (listRepairIndispoInput != null) {
			for (String r : listRepairIndispoInput) {
				String[] split = r.split("-");
				merciquimetier.repairIndispos(Long.valueOf(split[1]), Long.valueOf(split[0])); 

			}
		}

		return "redirect:/consulterAnalyses";

	}

	public static class RepairIndispo {
		private Long periode;
		private Long comedien;

		public RepairIndispo() {
		}

		public Long getPeriode() {
			return periode;
		}
		public void setPeriode(Long periode) {
			this.periode = periode;
		}
		public Long getComedien() {
			return comedien;
		}
		public void setComedien(Long comedien) {
			this.comedien = comedien;
		}	
	}

	public static class ListRepairIndispos {

		private Collection<RepairIndispo> listRepairIndispos;

		public ListRepairIndispos() {
		}

		public ListRepairIndispos(Collection<RepairIndispo> listRepairIndispos) {
			this.listRepairIndispos = listRepairIndispos;
		}

		public Collection<RepairIndispo> getListRepairIndispos() {
			return listRepairIndispos;
		}

		public void setListRepairIndispos(Collection<RepairIndispo> listRepairIndispos) {
			this.listRepairIndispos = listRepairIndispos;
		}
	}	
}
