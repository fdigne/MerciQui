package com.merciqui.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.merciqui.entities.Periode;
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
	public String consulterPeriodes(Model model) throws IOException, GeneralSecurityException {
	    
	    client = new com.google.api.services.calendar.Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, GestionAgendaController.credential)
                .setApplicationName(APPLICATION_NAME).build();
	    Collection<String> listEventId = new ArrayList<String>() ; 
	    Events googleEvents = null;
            googleEvents = client.events().list("primary").execute();
            
            for (Event e : googleEvents.getItems()) {
            	listEventId.add(e.getId());
            }
	    
	    Map<String, String> mapErreurs = new HashMap<String, String>();
		Collection<Evenement> listeEvenements = merciquimetier.listeEvenements();
		//Collection<Comedien> listeComediens = merciquimetier.listeComediens();
		Collection<String> evenementDB = new ArrayList<String>();
		for (Evenement ev : listeEvenements) {
		   for (Comedien com : ev.getDistribution().values()) {
		       // Check si evenement présent dans google Calendar
		       if (! listEventId.contains(ev.getIdEvenement())) {
		           String message = "Evenement n'apparaît pas dans Google Agenda";
		           mapErreurs.put(ev.getIdEvenement(), message);
		       }
		       // Check si le comedien est bien indispo sur la periode de l'évènement
		       if (! com.getListeIndispos().contains(ev.getPeriode())) {
		           String message = "Comedien " + com.getId3T() + " doit avoir periode "+ ev.getPeriode().getIdPeriode() + " dans ses indispos";
		           mapErreurs.put(ev.getIdEvenement(), message);
		       }
		   }
		   evenementDB.add(ev.getIdEvenement());
		}
		for (String id : listEventId) {
			if (! evenementDB.contains(id)) {
				String message = "Evenement n'apparaît que dans Google Agenda. A recréer en base.";
				mapErreurs.put(id, message);
			}
		}
		if (mapErreurs != null && !mapErreurs.isEmpty()) {
		    String alerte = mapErreurs.size() + " anomalies ont été détectées.";
		    model.addAttribute("alerte", alerte);
		    model.addAttribute("mapErreurs",mapErreurs);
		}
		else {
		    model.addAttribute("no_error", NO_ERROR);
		}
		return "AnalyseView";
	}
}