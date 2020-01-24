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
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


@Component
public class ReminderTask {

    private final String fromEmail = "les3tcafetheatreagenda@gmail.com"; //requires valid gmail id
    private final String password = "les3tcafetheatre"; // correct password for gmail id

    public static final List<String> ADMIN_LIST = Collections.unmodifiableList(Arrays.asList("fdigne@me.com",
            "laurence@3tcafetheatre.com", "peycorinne@gmail.com"));

    public String ACCOUNT_SID;
    public String AUTH_TOKEN;
    @Autowired
    IMerciQuiMetier merciquimetier ;


    @Scheduled(cron = "0 0 18 * * *")
    public void sendDailyReminder()  throws IOException{

        initTwilioToken();

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR, 1);
        today.set(Calendar.MINUTE, 0);
        today.add(Calendar.DATE, 1);

        Date dateDebut = today.getTime();
        today.add(Calendar.DATE, 1);
        Date dateFin = today.getTime();
        System.out.println("TWILIO ACCOUNT: " + ACCOUNT_SID);
        System.out.println("TOKEN: " + AUTH_TOKEN);
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Map<String, String> mapComediensSMS = new HashMap<>();
        Collection<Comedien> listeComediensNonPrevenus = new ArrayList<>();
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
                    mapComediensSMS.put(comedien.getPrenomPersonne() + " " + comedien.getNomPersonne(), message.getSid());
            } else {
                System.out.println("Le SMS n'a pas pu être envoyé à " + comedien.getPrenomPersonne() + " " + comedien.getNomPersonne());
                listeComediensNonPrevenus.add(comedien);
            }
        }
        this.sendAdminNotifMail(mapComediensSMS, listeComediensNonPrevenus);

    }

    private void initTwilioToken() throws IOException {
        FileReader reader=new FileReader("/root/MerciQui/twilio.properties");
        Properties p=new Properties();
        p.load(reader);
        this.ACCOUNT_SID = p.getProperty("TWILIO_ACCOUNT_SID");
        this.AUTH_TOKEN = p.getProperty("TWILIO_AUTH_TOKEN");

    }

    private void sendAdminNotifMail(Map<String, String> mapComediensSMS, Collection<Comedien> comediensNonPrevenus) {
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

        //MEF Date
        DateFormat dateFormat = new SimpleDateFormat("EEEE dd-MM-yyyy", Locale.FRANCE);
        String todayMail = dateFormat.format(today.getTime());

        String body = this.getBodyEmailAdmin(mapComediensSMS, comediensNonPrevenus);

        for (String toEmail : ADMIN_LIST) {
            System.out.println("Sending notification mail to admin: " + toEmail);
            GestionMailsController.sendEmail(session, toEmail,"Liste des comédiens prévenus pour " + todayMail, body);
        }

    }

    private String getBodyEmailAdmin(Map<String, String> mapComediensSMS, Collection<Comedien> comediensNonPrevenus) {
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
        body += "Bonjour,<br/><br/>";

        if ((mapComediensSMS != null && !mapComediensSMS.isEmpty()) || (comediensNonPrevenus != null && !comediensNonPrevenus.isEmpty())) {
            body += "Les comédiens suivants ont été prévenus :<br/><br/><br/>" ;
            body += "<div><table><tr><th>Comedien</th><th>SID</th></tr>" ;
            for (Map.Entry<String, String> entry : mapComediensSMS.entrySet()) {
                body += "<tr><td>"+entry.getKey()+"</td><td>" + entry.getValue() + "</td><tr>";
            }
            body += "</table></div>";
            if (comediensNonPrevenus != null && !comediensNonPrevenus.isEmpty()) {
                body += "<br/><br/>ATTENTION : Les comédiens suivants n'ont pas été prévenus : <br/><br/>";
                for (Comedien com : comediensNonPrevenus) {
                    body+= "<br/>" + com.getPrenomPersonne() + " " + com.getNomPersonne();
                }
            }
        } else {
            body += "Aucun comédien n'est prévu de jouer demain.";
        }

        return body;
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
                DateFormat dateFormat = new SimpleDateFormat("EEEE dd-MM-yyyy HH:mm", Locale.FRANCE);
                String dateEv = dateFormat.format(evenement.getDateEvenement());

                    body += "<tr><td>"+dateEv+"</td><td>" + evenement.getSpectacle().getNomSpectacle() + "</td><td>"+
                            evenement.getNomSalle()+"</td></tr>";
        }

        return body;
    }

    private String getSMSBody(Comedien com, Collection<Evenement> listeEvenements, Date dateDebut) {
        DateFormat df = new SimpleDateFormat("EEEE dd/MM/yyyy", Locale.FRANCE);
        String completeDate = df.format(dateDebut);
        String body = "Bonjour " + com.getPrenomPersonne() +",\nTu as " + listeEvenements.size() + " représentation(s) demain "
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
