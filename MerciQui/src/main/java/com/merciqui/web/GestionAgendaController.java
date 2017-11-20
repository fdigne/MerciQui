package com.merciqui.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets.Details;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.merciqui.entities.Comedien;
import com.merciqui.entities.Evenement;
import com.merciqui.entities.Periode;
import com.merciqui.entities.Role;
import com.merciqui.metier.IMerciQuiMetier;

@Controller
public class GestionAgendaController {

	private final static Log logger = LogFactory.getLog(GestionAgendaController.class);
	private static final String APPLICATION_NAME = "Merci Qui";
	private static HttpTransport httpTransport;
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static com.google.api.services.calendar.Calendar client;

	GoogleClientSecrets clientSecrets;
	GoogleAuthorizationCodeFlow flow;
	Credential credential;

	@Value("${google.client.client-id}")
	private String clientId;
	@Value("${google.client.client-secret}")
	private String clientSecret;
	@Value("${google.client.redirectUri}")
	private String redirectURI;

	private Set<Event> events = new HashSet<>();

	final DateTime date1 = new DateTime("2017-05-05T16:30:00.000+05:30");
	final DateTime date2 = new DateTime(new Date());

	public void setEvents(Set<Event> events) {
		this.events = events;
	}



	@Autowired
	IMerciQuiMetier merciquimetier ;

	@RequestMapping("/")
	public String index(Model model) {
		return "redirect:/login/google";
	}
	
	@RequestMapping("/agendaView")
	public String agendaView(Model model) {
		model.addAttribute("listeSpectacles", merciquimetier.listeSpectacles());
		model.addAttribute("listeEvenements", merciquimetier.listeEvenements());
		
		
	return "AgendaView" ;	
	}

	@RequestMapping("/consulterCalendrier")
	public String consulterCalendrier(Model model, String idEvenement, String error) {
		
		model.addAttribute("listeSpectacles", merciquimetier.listeSpectacles());
		model.addAttribute("listeEvenements", merciquimetier.listeEvenements());
		model.addAttribute("error", error);
		
		return "AgendaView";
	}

	@PostMapping("/saisieEvenement")
	public String saisieEvenement(Model model, String dateEvenement, String heureEvenement, 
			String nomSpectacle, String nomSalle, String idEvenement) {

		Date dateDebut = formatDateToRFC3339(dateEvenement, heureEvenement);
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(dateDebut);
		cal.add(java.util.Calendar.HOUR_OF_DAY,2); 
		Date dateFin = cal.getTime();
		 Periode periodeIndispo = merciquimetier.creerPeriode(new Periode(dateDebut, dateFin));

		//update MySQL
		Collection<EventAttendee> comediens = new ArrayList<EventAttendee>() ;
		Collection<Role> listeRoles = merciquimetier.listeRolesParSpectacle(merciquimetier.consulterSpectacle(nomSpectacle).getIdSpectacle());
		Set<Comedien> listeComediensDistrib = new HashSet<Comedien>();
		String descriptionEvent ="Distribution :\n\n" ;
		
		for(Role role : listeRoles) {
				try {
				Comedien com = setDistribution(role, periodeIndispo);
				EventAttendee attendee = new EventAttendee();
				attendee.setDisplayName(com.getNomPersonne()+" "+com.getPrenomPersonne())
				.setEmail(com.getAdresseEmail());
				comediens.add(attendee);
				descriptionEvent += attendee.getDisplayName() +"\n" ;
				Set<Periode> listeIndispos = com.getListeIndispos();
				listeIndispos.add(periodeIndispo);
				com.setListeIndispos(listeIndispos);
				listeComediensDistrib.add(com);
				}
				catch (Exception e) {
					return "redirect:/consulterCalendrier?error=Pas de comedien disponible pour le role "+role.getNomRole();
				}
				
				}
				
		
		//Update le Google Calendar
		client = new com.google.api.services.calendar.Calendar.Builder(httpTransport, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();


		Event myEvent = new Event()
				.setSummary(nomSpectacle)
				.setAttendees((List<EventAttendee>) comediens)
				.setDescription(descriptionEvent)
				.setLocation(nomSalle);
		DateTime startDateTime = new DateTime(dateDebut);

		EventDateTime start = new EventDateTime()
				.setDateTime(startDateTime);
		myEvent.setStart(start);   



		DateTime endDateTime = new DateTime(dateFin);
		EventDateTime end = new EventDateTime()
				.setDateTime(endDateTime);    
		myEvent.setEnd(end);

		String calendarId = "primary";
		try {
			myEvent = client.events().insert(calendarId, myEvent).setSendNotifications(true).execute();
			Evenement ev = new Evenement(myEvent.getId(), mefDateEvenementSQL(dateEvenement,heureEvenement), merciquimetier.consulterSpectacle(nomSpectacle), nomSalle, listeComediensDistrib);
			ev.setPeriode(periodeIndispo);
			merciquimetier.creerEvenement(ev);
			
			return "redirect:/consulterCalendrier?idEvenement="+ev.getIdEvenement() ;
		} catch (IOException e) {
			e.printStackTrace();
			return "redirect:/consulterCalendrier?error="+e.getMessage();
		}
		
	}

		
			
	private Comedien setDistribution(Role role, Periode periode){
		Comedien distribComedien = new Comedien();
		boolean isIndispoTit = false ;
		Collection<Comedien> listeRemplacantDistrib= new ArrayList<Comedien>();
		Map<String, Integer> mapComedienNbDates = new HashMap<String, Integer>();

		for (Periode p : role.getComedienTitulaire().getListeIndispos()) {
			
				if(isOverlapping(periode.getDateDebut(), periode.getDateFin(), p.getDateDebut(), p.getDateFin())) {
					isIndispoTit = true ;
					for(Comedien rempl : role.getListeRemplas()) {
						boolean isIndispoRempl = false ;
						for(Periode pr : rempl.getListeIndispos()) {
							if(isOverlapping(periode.getDateDebut(), periode.getDateFin(), pr.getDateDebut(), pr.getDateFin())) {
								isIndispoRempl =true ;

							}
						}
						if (! isIndispoRempl) {

							listeRemplacantDistrib.add(rempl);
						}
					}

					for (Comedien comDispo : listeRemplacantDistrib) {
						int nbDatesCom = merciquimetier.getNombreDatesTotal(comDispo.getId3T());
						mapComedienNbDates.put(comDispo.getId3T(), nbDatesCom);	
					}
					Entry<String, Integer> min = Collections.min(mapComedienNbDates.entrySet(),
							Comparator.comparingInt(Entry::getValue));
					distribComedien =  merciquimetier.consulterComedien(min.getKey());


				} 
			}
				if(! isIndispoTit) {
				distribComedien = role.getComedienTitulaire();
				}
				
				return distribComedien ;
		}	
		

	private static boolean isOverlapping(Date start1, Date end1, Date start2, Date end2) {
		return start1.before(end2) && start2.before(end1);
	}

	@PostMapping("/supprimerEvenement")
	public String saisieEvenement(Model model, String idEvenement) {
		Evenement evenement = merciquimetier.consulterEvenement(idEvenement);
		merciquimetier.supprimerEvenement(evenement);

		client = new com.google.api.services.calendar.Calendar.Builder(httpTransport, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();

		try {
			client.events().delete("primary", idEvenement).setSendNotifications(true).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/";		
	}

	private Date mefDateEvenementSQL(String dateEvenement, String heureEvenement) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		java.util.Date utilDate;
		try {
			System.out.println(heureEvenement);
			utilDate = sdf.parse(dateEvenement+" "+heureEvenement);
			System.out.println(utilDate);
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());	
			System.out.println(sqlDate);
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
			DateTime datetime = new DateTime(utilDate);
			return utilDate ;
		} catch (ParseException e) {
			e.printStackTrace();
			return null ;
		}
	}




	@RequestMapping(value = "/login/google", method = RequestMethod.GET)
	public RedirectView googleConnectionStatus(HttpServletRequest request) throws Exception {
		return new RedirectView(authorize());
	}

	@RequestMapping(value = "/login/google", method = RequestMethod.GET, params = "code")
	public String oauth2Callback(@RequestParam(value = "code") String code) {
		try {
			TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectURI).execute();
			credential = flow.createAndStoreCredential(response, "userID");	
		} catch (Exception e) {
			e.printStackTrace();
		}


		return "redirect:/agendaView";
	}

	public Set<Event> getEvents() throws IOException {
		return this.events;
	}

	private String authorize() throws Exception {
		AuthorizationCodeRequestUrl authorizationUrl;
		if (flow == null) {
			Details web = new Details();
			web.setClientId(clientId);
			web.setClientSecret(clientSecret);
			clientSecrets = new GoogleClientSecrets().setWeb(web);
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets,
					Collections.singleton(CalendarScopes.CALENDAR)).build();
		}
		authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(redirectURI);
		return authorizationUrl.build();
	}
}