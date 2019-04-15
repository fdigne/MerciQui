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
	public String ajouterMultipleEvents(Model model) throws IOException, GeneralSecurityException {

		client = new com.google.api.services.calendar.Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, GestionAgendaController.credential)
				.setApplicationName(APPLICATION_NAME).build();

		return "MultipleEventsView";
	}
}
