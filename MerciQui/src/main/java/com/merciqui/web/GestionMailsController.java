package com.merciqui.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.merciqui.entities.Comedien;
import com.merciqui.entities.Evenement;
import com.merciqui.entities.Spectacle;
import com.merciqui.metier.IMerciQuiMetier;

@Controller
public class GestionMailsController {

	@Autowired
	IMerciQuiMetier merciquimetier ;

	private final String fromEmail = "les3tcafetheatreagenda@gmail.com"; //requires valid gmail id
	private final String password = "les3tcafetheatre"; // correct password for gmail id

	private static final Map<String, Integer>seasons = new HashMap<String, Integer>() ;
	static {
		seasons.put("AutomneDebut" , Calendar.SEPTEMBER);
		seasons.put("AutomneFin" , Calendar.JANUARY);
		seasons.put("HiverDebut" , Calendar.FEBRUARY);
		seasons.put("HiverFin" , Calendar.APRIL);
		seasons.put("PrintempsDebut" , Calendar.MAY);
		seasons.put("PrintempsFin" , Calendar.JULY);
		seasons.put("EteDebut" , Calendar.AUGUST);
		seasons.put("EteFin" , Calendar.AUGUST);


	};




	@GetMapping("/sendEmail")
	public String sendEmail(Model model,String yearFilter, String periodFilter) {

		if(yearFilter == null) {
			yearFilter = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.valueOf(yearFilter));
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.set(Calendar.HOUR_OF_DAY,  0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MONTH, seasons.get(periodFilter+"Debut"));

		Date dateDebutFiltre = cal.getTime();
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MONTH, seasons.get(periodFilter+"Fin"));
		if(periodFilter.equals("Automne")) {
			int nextYear = Integer.valueOf(yearFilter) +1 ;
			cal.set(Calendar.YEAR, nextYear);
		}
		Date dateFinFiltre = cal.getTime();
		Collection<Evenement> listeEvenementsFiltres = new ArrayList<Evenement>();
		Collection<Evenement> listeEvenements = merciquimetier.listeEvenements();
		for (Evenement ev : listeEvenements) {
			Date dateEvenement = ev.getDateEvenement();
			if(dateEvenement.compareTo(dateDebutFiltre)>0 && dateFinFiltre.compareTo(dateEvenement)> 0){	
				listeEvenementsFiltres.add(ev);
			}
		}

		Collection<Comedien> listeComediens = new ArrayList<Comedien>();
		for (Evenement evenement : listeEvenementsFiltres) {
			for(Entry<Long, Comedien> entry : evenement.getDistribution().entrySet()) {
				if(! listeComediens.contains(entry.getValue())) {
					listeComediens.add(entry.getValue());
				}	
			}
		}

		for (Comedien com : listeComediens) {
			//Envoi des emails pour chaque comédien


			String toEmail = com.getAdresseEmail(); // can be any email id 

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			//create Authenticator object to pass in Session.getInstance argument
			Authenticator auth = new Authenticator() {
				//override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, password);
				}
			};
			Session session = Session.getInstance(props, auth);
			String body = this.getBodyEmail(com, periodFilter, yearFilter);
			this.sendEmail(session, toEmail,"Planning "+periodFilter+" "+yearFilter, body);


		}


		return "redirect:/" ;	
	}

	private String getBodyEmail(Comedien com, String periodFilter, String yearFilter) {

		Collection<Evenement> listeEvenements37 = new ArrayList<Evenement>();
		Collection<Evenement> listeEvenements333 = new ArrayList<Evenement>();


		Collection<Evenement> listeEvenementParComedien = merciquimetier.listeEvenementsParComedien(com.getId3T());

		if(yearFilter == null) {
			yearFilter = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.valueOf(yearFilter));
		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.set(Calendar.HOUR_OF_DAY,  0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MONTH, seasons.get(periodFilter+"Debut"));

		Date dateDebutFiltre = cal.getTime();
		System.out.println("Début filtre "+dateDebutFiltre);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MONTH, seasons.get(periodFilter+"Fin"));

		Date dateFinFiltre = cal.getTime();
		Collection<Evenement> listeEvenementsFiltres = new ArrayList<Evenement>();

		for (Evenement evenementFiltre : listeEvenementParComedien) {
			Date dateEvenement = evenementFiltre.getDateEvenement();
			if(dateEvenement.compareTo(dateDebutFiltre)>0 && dateFinFiltre.compareTo(dateEvenement)> 0){	
				listeEvenementsFiltres.add(evenementFiltre);
			}
		}

		Collection<Spectacle> listeSpec37 = new ArrayList<Spectacle>();
		Collection<Spectacle> listeSpec333 = new ArrayList<Spectacle>();
		for (Evenement ev : listeEvenementsFiltres) {
			if(ev.getNomSalle().equals("3T") || ev.getNomSalle().equals("PRIVÉ")) {

				listeEvenements37.add(ev);
				if(! listeSpec37.contains(ev.getSpectacle())) {
					listeSpec37.add(ev.getSpectacle());
				}
			}
			else {
				listeEvenements333.add(ev);
				if(! listeSpec333.contains(ev.getSpectacle())) {
					listeSpec333.add(ev.getSpectacle());
				}
			}
		}

		String body = "<html>\n" + 
				"<head>\n" + 
				"<style>\n" + 
				"table {\n" + 
				"    border-collapse: collapse;\n" + 
				"    width: 100%;\n" + 
				"}\n" + 
				"\n" + 
				"th, td {\n" + 
				"    text-align: left;\n" + 
				"    padding: 8px;\n" + 
				"}\n" + 
				"\n" + 
				"tr:nth-child(even) {background-color: #f2f2f2;}\n" + 
				"</style>\n" + 
				"</head>\n" + 
				"<body>" ;
		body += "Bonjour "+com.getPrenomPersonne()+",<br/><br/>";

		body += "Tu trouveras ci-dessous ton planning pour la période "+periodFilter+" "+yearFilter+".<br/><br/>" ;
		body += "Bises.<br/>Laurence.<br/><br/><br/>";
		body += "<div><table><tr><th>Représentations</th><th>Salle</th><th>Role</th><th>Distribution</th></tr>" ;	
		body +="<tr><th>Compagnie 37</th><td></td><td></td><td></td></tr>";
		for (Spectacle spec : listeSpec37) {

			body +="<tr><th>"+spec.getNomSpectacle()+"</th><td></td><td></td><td></td><td></td></tr>" ;
			for (Evenement evParSpec : merciquimetier.listeEvenementsParSpectacle(spec.getIdSpectacle())) {
				//MEF Date évènement
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
				String dateEv = dateFormat.format(evParSpec.getDateEvenement());
				
				if(listeEvenements37.contains(evParSpec)) {
					body += "<tr><td>"+dateEv+"</td><td>"+evParSpec.getNomSalle()+"</td><td>"+merciquimetier.consulterRole(getKeyValueFromMapDistribution(com, evParSpec.getDistribution())).getNomRole()+"</td><td>";
					for (Comedien comDistrib : evParSpec.getDistribution().values()) {
						body += comDistrib.getNomPersonne()+" "+comDistrib.getPrenomPersonne()+"<br/>";
					}
					body += "</td></tr>";
				}
			}

		}

		body +="<tr><th>Compagnie 333+1</th><td></td><td></td><td></td></tr>";

		for (Spectacle spec : listeSpec333) {
			body +="<tr><th>"+spec.getNomSpectacle()+"</th><td></td><td></td><td></td></tr>" ;	
			for (Evenement evParSpec : merciquimetier.listeEvenementsParSpectacle(spec.getIdSpectacle())) {
				//MEF Date évènement
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
				String dateEv = dateFormat.format(evParSpec.getDateEvenement());
				
				if(listeEvenements333.contains(evParSpec)) {
					body += "<tr><td>"+dateEv+"</td><td>"+evParSpec.getNomSalle()+"</td><td>"+merciquimetier.consulterRole(getKeyValueFromMapDistribution(com, evParSpec.getDistribution())).getNomRole()+"</td><td>" ;
					for (Comedien comDistrib : evParSpec.getDistribution().values()) {
						body += comDistrib.getNomPersonne()+" "+comDistrib.getPrenomPersonne()+"<br/>";
					}
					body += "</td></tr>";
				}
			}
		}

		body += "<tr><th>Total</th><th>"+String.valueOf(listeEvenementsFiltres.size())+"</th><td></td><td></td></tr></table></div></body></html>" ;

		return body;
	}

	/**
	 * Utility method to send simple HTML email
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	public static void sendEmail(Session session, String toEmail, String subject, String body){
		try
		{
			MimeMessage msg = new MimeMessage(session);
			//set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress("les3tcafetheatreagenda@gmail.com"));

			msg.setSubject(subject, "UTF-8");
			// Send the actual HTML message, as big as you like
			msg.setContent(
					body,
					"text/HTML; charset=UTF-8");

			//msg.setText(body, "UTF-8");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			Transport.send(msg);  
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Long getKeyValueFromMapDistribution(Comedien value, Map<Long, Comedien> distribution) {
		Long keys = null ;
		for(Entry<Long, Comedien> entry: distribution.entrySet()) {
			if(value.equals(entry.getValue())) {
				keys = entry.getKey();
			}
		}
		return keys ;
	}
}
