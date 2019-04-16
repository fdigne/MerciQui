package com.merciqui.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.merciqui.entities.Periode;
import com.merciqui.entities.PeriodeFiltre;
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
		Periode periodeFiltre = merciquimetier.consulterPeriode(idPeriodeFiltre);
		//int numberOfDays = this.getNumberOfDays(periodeFiltre);
		model.addAttribute("days", idPeriodeFiltre);
		client = new com.google.api.services.calendar.Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, GestionAgendaController.credential)
				.setApplicationName(APPLICATION_NAME).build();

		return "MultipleEventsView";
	}

	private int getNumberOfDays(Periode periodeFiltre) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(periodeFiltre.getDateDebut());
		cal2.setTime(periodeFiltre.getDateFin());
		
		return (int) ((cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (1000 * 60 * 60 * 24));
	}
	
	
}
