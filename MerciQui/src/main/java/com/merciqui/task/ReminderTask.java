package com.merciqui.task;

import com.merciqui.entities.Comedien;
import com.merciqui.entities.Evenement;
import com.merciqui.metier.IMerciQuiMetier;
import com.merciqui.web.GestionMailsController;
import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


@Component
public class ReminderTask {

    private final String fromEmail = "les3tcafetheatreagenda@gmail.com"; //requires valid gmail id
    private final String password = "les3tcafetheatre"; // correct password for gmail id

    public static final String ACCOUNT_SID = "AC5004a7c3df22a2936423fb717614709a";
    public static final String AUTH_TOKEN = "c7a878ea2ada71f5b49289aa8eccd414";


    @Autowired
    IMerciQuiMetier merciquimetier ;

    @Scheduled(cron = "0 0 18 * * *")
    public void sendDailyReminder() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR, 1);
        today.set(Calendar.MINUTE, 0);
        today.add(Calendar.DATE, 1);

        Date dateDebut = today.getTime();
        today.add(Calendar.DATE, 1);
        Date dateFin = today.getTime();

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        for (Comedien comedien : merciquimetier.getListeComediensParPeriode(dateDebut, dateFin)) {
            if (comedien.getNumTel() != null && !comedien.getNumTel().isEmpty()) {
                String numTel = comedien.getNumTel();
                numTel = numTel.replaceAll("\\s+","");
                if (!numTel.startsWith("+33")) {
                    numTel = numTel.replaceFirst("0","+33");
                }
                    Message message = Message.creator(new PhoneNumber(numTel), new PhoneNumber("+17543335908"),
                            this.getSMSBody(comedien, merciquimetier.listeEvenementsParComedienParPeriode(comedien.getId3T(), dateDebut, dateFin), dateDebut)).create();
                    System.out.println("SMS envoyé à " + comedien.getPrenomPersonne() + " " + comedien.getNomPersonne());
                    System.out.println("SID: "+ message.getSid());
            } else {
                System.out.println("Le SMS n'a pas pu être envoyé à " + comedien.getPrenomPersonne() + " " + comedien.getNomPersonne());
            }
        }

    }

    @Scheduled(cron = "0 0 17 * * SUN")
    public void sendWeeklyReminder() {

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

    private String getSMSBody(Comedien com, Collection<Evenement> listeEvenements, Date dateDebut) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String completeDate = df.format(dateDebut);
        String body = "Bonjour " + com.getPrenomPersonne() +",\nTu as " + listeEvenements.size() + " représentation(s) demain le "
                + completeDate + ": \n";
        for (Evenement evenement : listeEvenements) {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String dateEv = dateFormat.format(evenement.getDateEvenement());
            body += dateEv + " " + evenement.getSpectacle().getNomSpectacle() + "\n";
        }
        if (com.getId3T() == 33) {
            body += "Je t'aime.\nTon doudou qui t'écrit un message spécial que pour toi.";
        } else {
            body += "A demain.\nLaurence.";
        }

        return body;
    }
}
