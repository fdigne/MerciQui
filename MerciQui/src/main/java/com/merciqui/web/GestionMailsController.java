package com.merciqui.web;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.merciqui.entities.Comedien;
import com.merciqui.entities.Evenement;
import com.merciqui.entities.PeriodeFiltre;
import com.merciqui.entities.Spectacle;
import com.merciqui.metier.IMerciQuiMetier;

@Controller
public class GestionMailsController {

	@Autowired
	IMerciQuiMetier merciquimetier ;

	private final String fromEmail = "les3tcafetheatreagenda@gmail.com"; //requires valid gmail id
	private final String password = "les3tcafetheatre"; // correct password for gmail id


	@GetMapping("/sendEmail")
	public String sendEmail(Model model, Long idPeriodeFiltre, String[] listeComediensAjoutes) {

		
		System.out.println(listeComediensAjoutes);
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


		PeriodeFiltre periodeFiltre = merciquimetier.consulterPeriodeFiltre(idPeriodeFiltre);
		dateDebutFiltre = periodeFiltre.getDateDebut();
		dateFinFiltre = periodeFiltre.getDateFin();


		Collection<Evenement> listeEvenementsFiltres = merciquimetier.listeEvenementsParPeriode(dateDebutFiltre, dateFinFiltre);

		
		Collection<Comedien> listeComediensCheck = new ArrayList<Comedien>();
		Collection<Comedien> listeComediens = new ArrayList<Comedien>();

		boolean allComediens = false ;
		for (String idCom : listeComediensAjoutes) {
			if (idCom.equals("all")) {
				allComediens= true ;
			}
			else {
				listeComediensCheck.add(merciquimetier.consulterComedien(Long.valueOf(idCom)));
			}
		}
		if (allComediens) {
			for (Evenement evenement : listeEvenementsFiltres) {
				for(Entry<Long, Comedien> entry : evenement.getDistribution().entrySet()) {
					if(! listeComediens.contains(entry.getValue())) {
						listeComediens.add(entry.getValue());
					}	
				}
			}
		}
		else {
			listeComediens.addAll(listeComediensCheck);
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
			String body = this.getBodyEmail(com, periodeFiltre);
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			GestionMailsController.sendEmail(session, toEmail,"Planning du "+df.format(periodeFiltre.getDateDebut())+" au "+df.format(periodeFiltre.getDateFin()), body);


		}


		return "redirect:/" ;	
	}

	private String getBodyEmail(Comedien com, PeriodeFiltre periodeFiltre) {


		Collection<Evenement> listeEvenements37 = merciquimetier.listeEvenementsParComedienParPeriodeParCompagnie(com.getId3T(), periodeFiltre.getDateDebut(), periodeFiltre.getDateFin(), "Compagnie 37");
		Collection<Evenement> listeEvenements333 = merciquimetier.listeEvenementsParComedienParPeriodeParCompagnie(com.getId3T(), periodeFiltre.getDateDebut(), periodeFiltre.getDateFin(), "Compagnie 333+1");
		Collection<BigInteger> listeSpec37 = merciquimetier.listeSpectacleParComedienParPeriodeParCompagnie(com.getId3T(), periodeFiltre.getDateDebut(), periodeFiltre.getDateFin(), "Compagnie 37");
		Collection<BigInteger> listeSpec333 = merciquimetier.listeSpectacleParComedienParPeriodeParCompagnie(com.getId3T(), periodeFiltre.getDateDebut(), periodeFiltre.getDateFin(), "Compagnie 333+1");
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
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

		body += "Tu trouveras ci-dessous ton planning pour la période du "+df.format(periodeFiltre.getDateDebut())+" au "+df.format(periodeFiltre.getDateFin())+".<br/><br/>" ;
		body += "Bises.<br/>Laurence.<br/><br/><br/>";
		body += "<div><table><tr><th>Représentations</th><th>Salle</th><th>Role</th><th>Distribution</th></tr>" ;	
		body +="<tr><th>Compagnie 37</th><td></td><td></td><td></td></tr>";
		for (BigInteger idSpectacle : listeSpec37) {
			Spectacle spec = merciquimetier.consulterSpectacle(idSpectacle.longValue()) ;
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

		for (BigInteger idSpectacle : listeSpec333) {
			Spectacle spec = merciquimetier.consulterSpectacle(idSpectacle.longValue());
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

		body += "<tr><th>Total</th><th>"+String.valueOf(listeEvenements37.size()+listeEvenements333.size())+"</th><td></td><td></td></tr></table></div></body></html>" ;

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
		long keys = 301 ;
		for(Entry<Long, Comedien> entry: distribution.entrySet()) {
			if(value.equals(entry.getValue())) {
				keys = entry.getKey();
			}
		}
		return keys ;
	}
}
