package com.merciqui.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.google.api.services.calendar.Calendar.Colors;
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

	public final static Log logger = LogFactory.getLog(GestionAgendaController.class);
	public static final String APPLICATION_NAME = "Merci Qui";
	public static HttpTransport httpTransport;
	public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static com.google.api.services.calendar.Calendar client;

	private static final Map<String, Integer>seasons = new HashMap<String, Integer>() ;
	static {
		seasons.put("AutomneDebut" , Calendar.SEPTEMBER);
		seasons.put("AutomneFin" , Calendar.DECEMBER);
		seasons.put("HiverDebut" , Calendar.JANUARY);
		seasons.put("HiverFin" , Calendar.MARCH);
		seasons.put("PrintempsDebut" , Calendar.APRIL);
		seasons.put("PrintempsFin" , Calendar.JUNE);
		seasons.put("EteDebut" , Calendar.JULY);
		seasons.put("EteFin" , Calendar.AUGUST);


	};

	private static final Map<String, String> mapSalleCouleur = new HashMap<String, String>();
	static {
		mapSalleCouleur.put("3T", "6");
		mapSalleCouleur.put("3T D'A COTE", "2");
		mapSalleCouleur.put("GRANDE SALLE", "3");
		mapSalleCouleur.put("PRIVÉ", "4");


	};




	GoogleClientSecrets clientSecrets;
	GoogleAuthorizationCodeFlow flow;

	static Credential credential;


	@Value("${google.client.client-id}")
	private String clientId;
	@Value("${google.client.client-secret}")
	private String clientSecret;
	@Value("${google.client.redirectUri}")
	private String redirectURI;


	private Set<Event> events = new HashSet<>();


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
	public String consulterCalendrier(Model model, String idEvenement, String error, String yearFilterEvent, String periodFilterEvent, String monthFilterEvent, String errorModif) {
		model.addAttribute("idEvenement", idEvenement);
		model.addAttribute("yearFilterEvent", yearFilterEvent);
		model.addAttribute("periodFilterEvent", periodFilterEvent);
		model.addAttribute("monthFilterEvent", monthFilterEvent);



		if(idEvenement != null) {
			Evenement evenement = merciquimetier.consulterEvenement(idEvenement);
			model.addAttribute("evenement", evenement);
			model.addAttribute("listeRoles", merciquimetier.listeRolesParSpectacle(evenement.getSpectacle().getIdSpectacle()));
			model.addAttribute("listeComediensParEv", evenement.getListeComediens());
			model.addAttribute("listeComediensParSpectacle", merciquimetier.getListeComediensParSpectacles(evenement.getSpectacle().getIdSpectacle()));

		}


		model.addAttribute("listeSpectacles", merciquimetier.listeSpectacles());

		if(yearFilterEvent == null) {
			yearFilterEvent = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.valueOf(yearFilterEvent));
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.set(Calendar.HOUR_OF_DAY,  0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		if(monthFilterEvent != null) {
			cal.set(Calendar.MONTH, Integer.valueOf(monthFilterEvent));
		}
		if(periodFilterEvent != null) {
			cal.set(Calendar.MONTH, seasons.get(periodFilterEvent+"Debut"));
		}

		Date dateDebutFiltre = cal.getTime();
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		if(monthFilterEvent != null) {
			cal.set(Calendar.MONTH, Integer.valueOf(monthFilterEvent));
		}
		if(periodFilterEvent != null) {
			cal.set(Calendar.MONTH, seasons.get(periodFilterEvent+"Fin"));
		}


		Date dateFinFiltre = cal.getTime();
		Collection<Evenement> listeEvenementsFiltres = new ArrayList<Evenement>();

		for (Evenement evenementFiltre : merciquimetier.listeEvenements()) {
			Date dateEvenement = evenementFiltre.getDateEvenement();
			if(dateEvenement.compareTo(dateDebutFiltre)>0 && dateFinFiltre.compareTo(dateEvenement)> 0){	
				listeEvenementsFiltres.add(evenementFiltre);
			}
		}

		model.addAttribute("listeEvenements", listeEvenementsFiltres);

		model.addAttribute("error", error);
		model.addAttribute("errorModif", errorModif);


		return "AgendaView";
	}


	@PostMapping("/saisieEvenement")
	public String saisieEvenement(Model model, String dateEvenement, String heureEvenement, 
			String nomSpectacle, String nomSalle, String lieuEvent,String compagnie, String idEvenement, boolean notifications) {

		Date dateDebut = formatDateToRFC3339(dateEvenement, heureEvenement);
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(dateDebut);
		cal.add(java.util.Calendar.HOUR_OF_DAY,2); 
		Date dateFin = cal.getTime();
		Periode periodeIndispo = merciquimetier.creerPeriode(new Periode(dateDebut, dateFin));
		if (! salleDispo(periodeIndispo, nomSalle)) {
			return "redirect:/consulterCalendrier?error=La salle "+nomSalle+" n'est pas disponible !";
		}

		//update MySQL
		Collection<EventAttendee> comediens = new ArrayList<EventAttendee>() ;
		Collection<Role> listeRoles = merciquimetier.listeRolesParSpectacle(merciquimetier.consulterSpectacle(nomSpectacle).getIdSpectacle());
		Set<Comedien> listeComediensDistrib = new HashSet<Comedien>();

		String descriptionEvent ="Distribution :\n\n" ;
		Map<Long, Comedien> mapDistribution = new HashMap<Long , Comedien>();
		for(Role role : listeRoles) {
			try {
				Comedien com = setDistribution(role, periodeIndispo);
				mapDistribution.put(role.getIdRole(), com);
				EventAttendee attendee = new EventAttendee();
				attendee.setId(com.getId3T());
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
		if (nomSalle.equals("PRIVÉ")) {
			nomSalle =nomSalle+ " - "+lieuEvent;
		}
		Event myEvent = new Event()
				.setSummary(nomSpectacle)
				.setAttendees((List<EventAttendee>) comediens)
				.setDescription(descriptionEvent)
				.setColorId(mapSalleCouleur.get(nomSalle))
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
			myEvent = client.events().insert(calendarId, myEvent).setSendNotifications(notifications).execute();

			Evenement ev = new Evenement(myEvent.getId(), mefDateEvenementSQL(dateEvenement,heureEvenement), merciquimetier.consulterSpectacle(nomSpectacle), nomSalle, listeComediensDistrib);
			ev.setPeriode(periodeIndispo);
			ev.setDistribution(mapDistribution);
			ev.setCompagnie(compagnie);
			merciquimetier.creerEvenement(ev);

			return "redirect:/consulterCalendrier?idEvenement="+ev.getIdEvenement() ;
		} catch (IOException e) {
			e.printStackTrace();
			return "redirect:/consulterCalendrier?error="+e.getMessage();
		}

	}



	private boolean salleDispo(Periode periodeIndispo, String nomSalle) {
		Collection<Evenement> listeEvenements = merciquimetier.listeEvenementParSalle(nomSalle);
		boolean isDispo = true ;
		for (Evenement ev : listeEvenements) {
			if (isOverlapping(periodeIndispo.getDateDebut(), periodeIndispo.getDateFin(), ev.getPeriode().getDateDebut(), ev.getPeriode().getDateFin())) {
				isDispo = false ;
			}
		}
		return isDispo;
	}

	private Comedien setDistribution(Role role, Periode periode){
		Comedien distribComedien = new Comedien();
		boolean isIndispoTit = false ;
		Collection<Comedien> listeRemplacantDistrib= new ArrayList<Comedien>();
		Map<String, Integer> mapComedienNbDates = new HashMap<String, Integer>();


		if (role.getComedienTitulaire() != null) {
			for (Periode p : role.getComedienTitulaire().getListeIndispos()) {

				if(isOverlapping(periode.getDateDebut(), periode.getDateFin(), p.getDateDebut(), p.getDateFin())) {
					isIndispoTit = true ;
				} 
			}
		}
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


		
		if(! isIndispoTit && role.getComedienTitulaire() != null) {
			distribComedien = role.getComedienTitulaire();
		}

		return distribComedien ;
	}	


	private static boolean isOverlapping(Date start1, Date end1, Date start2, Date end2) {
		return start1.before(end2) && start2.before(end1);
	}

	@PostMapping("/modifierEvenement")
	public String modifierEvenement(Model model, String idEvenement, String[] id3T, boolean notificationsModif, String compagnieModif) {
		client = new com.google.api.services.calendar.Calendar.Builder(httpTransport, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();

		Evenement evenement = merciquimetier.consulterEvenement(idEvenement);
		Set<Comedien> listeComediensDistrib = new HashSet<Comedien>();
		String descriptionEvent ="Distribution :\n\n" ;
		Map<Long, Comedien> distribution = new HashMap<Long, Comedien>();



		try {
			Event myEvent = client.events().get("primary", evenement.getIdEvenement()).execute();
			List<EventAttendee> listeAttendees = myEvent.getAttendees() ;
			for (String s : id3T) {
				String[] keyValue = s.split("\\.");
				Comedien comedien = merciquimetier.consulterComedien(keyValue[1]) ;
				if (! evenement.getDistribution().containsValue(comedien)) {

					boolean comedienIndispo = false ;

					for (Periode p : comedien.getListeIndispos()) {
						if (isOverlapping(p.getDateDebut(), p.getDateFin(), evenement.getPeriode().getDateDebut(), evenement.getPeriode().getDateFin())) {
							comedienIndispo = true ;
						}
					}
					if (comedienIndispo) {
						return "redirect:/consulterCalendrier?idEvenement="+idEvenement+"&errorModif="+comedien.getNomPersonne()+" "+comedien.getPrenomPersonne()+" n'est pas disponible !";
					}
				}

				distribution.put(Long.valueOf(keyValue[0]),comedien);
				merciquimetier.supprimerEvenement(evenement);	
				for (EventAttendee eva : listeAttendees ) {

					listeAttendees.remove(eva) ;
					EventAttendee attendee = new EventAttendee();
					attendee.setId(comedien.getId3T());
					attendee.setDisplayName(comedien.getNomPersonne()+" "+comedien.getPrenomPersonne())
					.setEmail(comedien.getAdresseEmail());
					listeAttendees.add(attendee);
					listeComediensDistrib.add(comedien);
					descriptionEvent += comedien.getNomPersonne()+" "+comedien.getPrenomPersonne()+"\n";
				}
				myEvent.setAttendees(listeAttendees) ;
				myEvent.setDescription(descriptionEvent);
			}	

			client.events().update("primary", evenement.getIdEvenement(), myEvent).setSendNotifications(notificationsModif).execute();
			evenement.setListeComediens(listeComediensDistrib);
			evenement.setDistribution(distribution);
			evenement.setCompagnie(compagnieModif);
			merciquimetier.creerEvenement(evenement);
		} catch (IOException e) {
			e.printStackTrace();
		}



		return "redirect:/consulterCalendrier?idEvenement="+idEvenement ;	
	}

	@PostMapping("/supprimerEvenement")
	public String supprimerEvenement(Model model, String idEvenement, boolean notificationsSuppr) {
		Evenement evenement = merciquimetier.consulterEvenement(idEvenement);
		merciquimetier.supprimerEvenement(evenement);

		client = new com.google.api.services.calendar.Calendar.Builder(httpTransport, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();

		try {
			client.events().delete("primary", idEvenement).setSendNotifications(notificationsSuppr).execute();
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