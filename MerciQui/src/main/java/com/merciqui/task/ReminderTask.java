package com.merciqui.task;

import com.merciqui.entities.Comedien;
import com.merciqui.entities.Evenement;
import com.merciqui.entities.PeriodeFiltre;
import com.merciqui.entities.Spectacle;
import com.merciqui.metier.IMerciQuiMetier;
import com.merciqui.web.GestionMailsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

@Component
public class ReminderTask {

    private final String fromEmail = "les3tcafetheatreagenda@gmail.com"; //requires valid gmail id
    private final String password = "les3tcafetheatre"; // correct password for gmail id

    @Autowired
    IMerciQuiMetier merciquimetier ;

    @Scheduled(cron = "0 0 17 * * SUN")
    public void sendReminder() {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "*");

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE, 1);

        Date dateDebut = today.getTime();

        today.add(Calendar.DATE, 6);
        Date dateFin = today.getTime();

        for (Comedien comedien : merciquimetier.getListeComediensParPeriode(dateDebut, dateFin)) {
            String toEmail = comedien.getAdresseEmail(); // can be any email id
            String body = this.getBodyEmail(comedien, merciquimetier.listeEvenementsParComedienParPeriode(comedien.getId3T(), dateDebut, dateFin));

            GestionMailsController.sendEmail(session, toEmail,"RAPPEL : REPRÉSENTATIONS CETTE SEMAINE", body);
        }

    }

    private String getBodyEmail(Comedien com, Collection<Evenement> listeEvenements) {
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

        body += "Un petit rappel de tes dates de cette semaine.<br/><br/>" ;
        body += "Bises.<br/>Laurence.<br/><br/><br/>";
        body += "<div><table><tr><th>Date</th><th>Spectacle</th><th>Salle</th></tr>" ;
        for (Evenement evenement : listeEvenements) {
                //MEF Date évènement
                DateFormat dateFormat = new SimpleDateFormat("EEEE dd-MM-yyyy HH:mm");
                String dateEv = dateFormat.format(evenement.getDateEvenement());

                    body += "<tr><td>"+dateEv+"</td><td>" + evenement.getSpectacle().getNomSpectacle() + "</td><td>"+
                            evenement.getNomSalle()+"</td></tr>";
        }

        return body;
    }
}
