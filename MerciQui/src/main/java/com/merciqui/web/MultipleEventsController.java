package com.merciqui.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.merciqui.entities.Evenement;
import com.merciqui.entities.Periode;
import com.merciqui.entities.PeriodeFiltre;
import com.merciqui.entities.Spectacle;
import com.merciqui.metier.IMerciQuiMetier;

@Controller
public class MultipleEventsController {

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


	@Autowired
	IMerciQuiMetier merciquimetier ;

	@RequestMapping("/multipleEventsIndex")
	public String index(Model model) {
		return "redirect:/multipleEvents";
	}

	@RequestMapping("/multipleEvents")
	public String ajouterMultipleEvents(Model model) {
		Collection<PeriodeFiltre> listePeriodesFiltres = merciquimetier.listePeriodeFiltre();
		model.addAttribute("listePeriodesFiltres", listePeriodesFiltres);
		return "MultipleEventsView";
	}

	@RequestMapping("/multipleEvents/periodeFiltre")
	public String ajouterMultipleEventsPeriode(Model model, Long idPeriodeFiltre) throws GeneralSecurityException, IOException {
		Collection<PeriodeFiltre> listePeriodesFiltres = merciquimetier.listePeriodeFiltre();
		model.addAttribute("listePeriodesFiltres", listePeriodesFiltres);
		PeriodeFiltre periodeFiltre = merciquimetier.consulterPeriodeFiltre(idPeriodeFiltre);
		model.addAttribute("numberOfDays", getNumberOfDays(periodeFiltre));
		client = new com.google.api.services.calendar.Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, GestionAgendaController.credential)
				.setApplicationName(APPLICATION_NAME).build();
		Collection<Spectacle> listeSpectacle = merciquimetier.listeSpectacles();
		model.addAttribute("listeSpectacles", listeSpectacle);
		//Collection<Evenement> listeEvenements = merciquimetier.listeEvenementsParPeriode(periodeFiltre.getDateDebut(), periodeFiltre.getDateFin());


		return "MultipleEventsView";
	}

	@PostMapping("/ajouterEvenementsMultiples")
	public String ajouterMultiplesEvents(Model model, String[] dateEvenement, String[] heureEvenement, 
			String[] nomSpectacle, String[] nomSalle,String[] compagnie) {


		Collection<Evenement> listeEvenementsSaisis = new ArrayList<Evenement>();
		for (int i=0 ; i < nomSalle.length ; i++) {
			//Calcul date evenement et periode i
			Date dateDebut = formatDateToRFC3339(dateEvenement[i], heureEvenement[i]);
			java.util.Calendar cal = java.util.Calendar.getInstance();
			cal.setTime(dateDebut);
			cal.add(java.util.Calendar.HOUR_OF_DAY,2); 
			Date dateFin = cal.getTime();
			Evenement ev = new Evenement();
			ev.setIdEvenement("azerty");
			ev.setDateEvenement(mefDateEvenementSQL(dateEvenement[i], heureEvenement[i]));
			ev.setSpectacle(merciquimetier.consulterSpectacle(nomSpectacle[i]));
			ev.setNomSalle(nomSalle[i]);
			ev.setPeriode(new Periode(dateDebut, dateFin));
			listeEvenementsSaisis.add(ev);
		}
		return "MultipleEventsView";	
	}

	private int getNumberOfDays(final PeriodeFiltre periodeFiltre) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(periodeFiltre.getDateDebut());
		cal2.setTime(periodeFiltre.getDateFin());

		return (int) ((cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (1000 * 60 * 60 * 24));
	}

	private Date mefDateEvenementSQL(String dateEvenement, String heureEvenement) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		java.util.Date utilDate;
		try {
			utilDate = sdf.parse(dateEvenement+" "+heureEvenement);
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());	
			return sqlDate;	
		} catch (ParseException e) {
			e.printStackTrace();
			return null ;
		}
	}

	private Date formatDateToRFC3339(String dateEvenement, String heureEvenement) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		java.util.Date utilDate;
		try {
			utilDate = sdf.parse(dateEvenement + " "+heureEvenement);
			return utilDate ;
		} catch (ParseException e) {
			e.printStackTrace();
			return null ;
		}
	}
}
