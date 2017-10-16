package pl.pwsztar.mainServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwsztar.client.reservation.Reservation;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

/**
 * Created by Lapek on 19.05.2017.
 */
@Service
public class EmailService {
    protected String mailSmtpAuth = "true";
    protected String mailSmtpHost = "smtp.gmail.com";
    protected String mailSmtpPort = "587";
    protected String mailSmtpStarttlsEnable = "true";
    protected String mailEmailFrom = "dzialpomocy.pwsztar@gmail.com ";
    protected String username = "dzialpomocy.pwsztar@gmail.com";
    protected String password = "1qazxsw23edc";


/*    public static void main(String[] args) {
        EmailService emailService = new EmailService();
        emailService.sendEmail("nerq95@gmail.com","Temat maila2", "Tresc miala2");

    }*/

    public boolean sendEmail(String recipientEmail, String subject, String content) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", mailSmtpAuth);
        props.put("mail.smtp.starttls.enable", mailSmtpStarttlsEnable);
        props.put("mail.smtp.host", mailSmtpHost);
        props.put("mail.smtp.port", mailSmtpPort);
        props.put("mail.smtp.ssl.trust", mailSmtpHost);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailEmailFrom));
            message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return false;
    }

/*    public void sendCancellationToParticipants(List<Reservation> listOfParticipants){

        for (Reservation reservation : listOfParticipants
                ) {
            String email = reservation.getClient().getEmail();
            sendEmail(email,"Event cancellation", "Hello.\n\nWe would like to inform, that" +
                    " event which you were signed has been cancelled.\n\nEvent details: " +
                    "\nTherapist: " + reservation.getEvent().getTherapist().getFirstName() + " " +
                    reservation.getEvent().getTherapist().getLastName() +
                    "\nStart date/time: " + reservation.getEvent().getStartDateTime() +
                    "\nroom nr:" + reservation.getEvent().getRoom() + "\n\nKind regards\n " + reservation.getEvent().getTherapist().getFirstName() + " " +
                    reservation.getEvent().getTherapist().getLastName());
        }
    }*/
//edited,canceled
    public void sendMultiple(List<Reservation> listOfParticipants,Character type){
        String subject = null;
        String summary = null;
        switch (type){
            case 'e':
                subject = "Event has been modified";
                summary = "Hello.\n\nWe would like to inform, that event which you were signed has been modified.";
                break;
            case 'c':
                subject = "Event cancellation";
                summary = "Hello.\n\nWe would like to inform, that event which you were signed has been cancelled.";
                break;
                default: subject = null;
                summary = null;
                break;
        }
        System.out.println(subject);

        for (Reservation reservation : listOfParticipants
                ) {
            String email = reservation.getClient().getEmail();
            sendEmail(email,subject, summary+"\n\nEvent details: " +
                    "\nEvent type: "+ reservation.getEvent().getEventType().getEventTypeId()+
                    "\nTherapist: " + reservation.getEvent().getTherapist().getFirstName() + " " +
                    reservation.getEvent().getTherapist().getLastName() +
                    "\nStart date/time: " + reservation.getEvent().getStartDateTime() +
                    "\nroom nr:" + reservation.getEvent().getRoom() + "\n\nKind regards\n " + reservation.getEvent().getTherapist().getFirstName() + " " +
                    reservation.getEvent().getTherapist().getLastName());
        }
    }
}
