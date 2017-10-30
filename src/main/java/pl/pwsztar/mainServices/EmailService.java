package pl.pwsztar.mainServices;

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
        *//*emailService.sendEmail("tom-chr@wp.pl","Temat maila2", "Tresc miala2");*//*
        String text1 = "Gratulacje. Udało Ci się zarezerwować termin spotkania. psychoterapeutą. Czekamy na Ciebie dnia" +
                "<br>Teraz prosimy o potwierdzenie swojej obecności. <br> ";
        String text2 = "Oto Twój kod potwierdzenia: ";
        String text3 = "Aby potwierdzić swoją obecność przejdź na ";
        String confirmPageUrl = "http://localhost:8080/confirm-reservation";
        String text4 = " i wprowadź swój kod rezerwacji. W przeciwnym razie Twoja rezerwacja zostanie automatycznie usunięta.";
        String confirmationLinkName = "stronę potwierdzenia";
        emailService.sendHtmlEmail("tom-chr@wp.pl", "Potwierdź swoją rezerwację",text1,text2,text3,
                confirmPageUrl,text4,confirmationLinkName, "key");

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
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return false;
    }

    //edited,canceled
    public void sendMultiple(List<Reservation> listOfParticipants, Character type) {

        for (Reservation reservation : listOfParticipants
                ) {
            String subject;
            String text1,text2,text3;
            switch (type) {
                case 'e':
                    subject = "Modyfikacja spotkania";
                    text1 = "Witaj.\n\nSpotkanie z "+reservation.getEvent().getTherapist().getSpecialization()
                            + "<br>" +reservation.getEvent().getTherapist().getFirstName() + " " +
                            reservation.getEvent().getTherapist().getLastName()+"zostało edytowane.";
                    text2 = "Detale spotkania (po edycji):";
                    text3 = "Data: "+reservation.getEvent().getStartDateTime().toLocalDate()+"<br>Godzina "+
                            reservation.getEvent().getStartDateTime().toLocalTime()+"<br>Sala nr. "+
                            reservation.getEvent().getRoom()+".<br> Typ spotkania: spotkanie "+
                            reservation.getEvent().getEventType().getEventTypeId();
                    break;
                case 'c':
                    subject = "Odwołano spotkanie";
                    text1 = "Witaj. Spotkanie z "+reservation.getEvent().getTherapist().getSpecialization()+
                            "<br>"+reservation.getEvent().getTherapist().getFirstName()+" "+
                            reservation.getEvent().getTherapist().getLastName()+" zostało odwołane." +
                            " <br>Dane odwołanego spotkania: <br>Data: " +
                            reservation.getEvent().getStartDateTime().toLocalDate()+
                            " godz. " +reservation.getEvent().getStartDateTime().toLocalTime()+
                            "<br>Sala nr: "+reservation.getEvent().getRoom()+
                            "<br><br>Przepraszamy za utrudnienia. <br> ";
                    text2 = "";
                    text3 = "";
                    break;
                default:
                    subject = "";
                    text1 = "";
                    text2 = "";
                    text3 = "";
                    break;
            }
            String email = reservation.getClient().getEmail();
            sendHtmlEmail(email, subject,text1,text2,text3,"","","","");
        }
    }


    public void sendHtmlEmail(String recipientEmail, String subject, String text1, String text2, String text3,
                              String confirmPageUrl, String text4, String confirmationLinkName, String code) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", mailSmtpAuth);
        props.put("mail.smtp.starttls.enable", mailSmtpStarttlsEnable);
        props.put("mail.smtp.host", mailSmtpHost);
        props.put("mail.smtp.port", mailSmtpPort);
        props.put("mail.smtp.ssl.trust", mailSmtpHost);

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(mailEmailFrom));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail));

            // Set Subject: header field
            message.setSubject(subject);

            // Send the actual HTML message, as big as you like
            message.setContent(
                    "<head>\n" +
                            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                            "    <title></title>\n" +
                            "    <!--[if !mso]><!-->\n" +
                            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\n" +
                            "    <meta name=\"viewport\" content=\"width=device-width\">\n" +
                            "    <style type=\"text/css\">\n" +
                            "        @media only screen and (min-width: 620px) {\n" +
                            "            .wrapper {\n" +
                            "                min-width: 600px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper h1 {\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper h1 {\n" +
                            "                font-size: 64px !important;\n" +
                            "                line-height: 63px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper h2 {\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper h2 {\n" +
                            "                font-size: 30px !important;\n" +
                            "                line-height: 38px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper h3 {\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper h3 {\n" +
                            "                font-size: 22px !important;\n" +
                            "                line-height: 31px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .column {\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-8 {\n" +
                            "                font-size: 8px !important;\n" +
                            "                line-height: 14px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-9 {\n" +
                            "                font-size: 9px !important;\n" +
                            "                line-height: 16px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-10 {\n" +
                            "                font-size: 10px !important;\n" +
                            "                line-height: 18px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-11 {\n" +
                            "                font-size: 11px !important;\n" +
                            "                line-height: 19px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-12 {\n" +
                            "                font-size: 12px !important;\n" +
                            "                line-height: 19px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-13 {\n" +
                            "                font-size: 13px !important;\n" +
                            "                line-height: 21px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-14 {\n" +
                            "                font-size: 14px !important;\n" +
                            "                line-height: 21px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-15 {\n" +
                            "                font-size: 15px !important;\n" +
                            "                line-height: 23px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-16 {\n" +
                            "                font-size: 16px !important;\n" +
                            "                line-height: 24px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-17 {\n" +
                            "                font-size: 17px !important;\n" +
                            "                line-height: 26px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-18 {\n" +
                            "                font-size: 18px !important;\n" +
                            "                line-height: 26px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-20 {\n" +
                            "                font-size: 20px !important;\n" +
                            "                line-height: 28px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-22 {\n" +
                            "                font-size: 22px !important;\n" +
                            "                line-height: 31px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-24 {\n" +
                            "                font-size: 24px !important;\n" +
                            "                line-height: 32px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-26 {\n" +
                            "                font-size: 26px !important;\n" +
                            "                line-height: 34px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-28 {\n" +
                            "                font-size: 28px !important;\n" +
                            "                line-height: 36px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-30 {\n" +
                            "                font-size: 30px !important;\n" +
                            "                line-height: 38px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-32 {\n" +
                            "                font-size: 32px !important;\n" +
                            "                line-height: 40px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-34 {\n" +
                            "                font-size: 34px !important;\n" +
                            "                line-height: 43px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-36 {\n" +
                            "                font-size: 36px !important;\n" +
                            "                line-height: 43px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper\n" +
                            "            .size-40 {\n" +
                            "                font-size: 40px !important;\n" +
                            "                line-height: 47px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-44 {\n" +
                            "                font-size: 44px !important;\n" +
                            "                line-height: 50px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-48 {\n" +
                            "                font-size: 48px !important;\n" +
                            "                line-height: 54px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-56 {\n" +
                            "                font-size: 56px !important;\n" +
                            "                line-height: 60px !important\n" +
                            "            }\n" +
                            "\n" +
                            "            .wrapper .size-64 {\n" +
                            "                font-size: 64px !important;\n" +
                            "                line-height: 63px !important\n" +
                            "            }\n" +
                            "        }\n" +
                            "    </style>\n" +
                            "    <style type=\"text/css\">\n" +
                            "        body {\n" +
                            "            margin: 0;\n" +
                            "            padding: 0;\n" +
                            "        }\n" +
                            "\n" +
                            "        table {\n" +
                            "            border-collapse: collapse;\n" +
                            "            table-layout: fixed;\n" +
                            "        }\n" +
                            "\n" +
                            "        * {\n" +
                            "            line-height: inherit;\n" +
                            "        }\n" +
                            "\n" +
                            "        [x-apple-data-detectors],\n" +
                            "        [href^=\"tel\"],\n" +
                            "        [href^=\"sms\"] {\n" +
                            "            color: inherit !important;\n" +
                            "            text-decoration: none !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .wrapper .footer__share-button a:hover,\n" +
                            "        .wrapper .footer__share-button a:focus {\n" +
                            "            color: #ffffff !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .btn a:hover,\n" +
                            "        .btn a:focus,\n" +
                            "        .footer__share-button a:hover,\n" +
                            "        .footer__share-button a:focus,\n" +
                            "        .email-footer__links a:hover,\n" +
                            "        .email-footer__links a:focus {\n" +
                            "            opacity: 0.8;\n" +
                            "        }\n" +
                            "\n" +
                            "        .preheader,\n" +
                            "        .header,\n" +
                            "        .layout,\n" +
                            "        .column {\n" +
                            "            transition: width 0.25s ease-in-out, max-width 0.25s ease-in-out;\n" +
                            "        }\n" +
                            "\n" +
                            "        .preheader td {\n" +
                            "            padding-bottom: 8px;\n" +
                            "        }\n" +
                            "\n" +
                            "        .layout,\n" +
                            "        div.header {\n" +
                            "            max-width: 400px !important;\n" +
                            "            -fallback-width: 95% !important;\n" +
                            "            width: calc(100% - 20px) !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        div.preheader {\n" +
                            "            max-width: 360px !important;\n" +
                            "            -fallback-width: 90% !important;\n" +
                            "            width: calc(100% - 60px) !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .snippet,\n" +
                            "        .webversion {\n" +
                            "            Float: none !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .column {\n" +
                            "            max-width: 400px !important;\n" +
                            "            width: 100% !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .fixed-width.has-border {\n" +
                            "            max-width: 402px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .fixed-width.has-border .layout__inner {\n" +
                            "            box-sizing: border-box;\n" +
                            "        }\n" +
                            "\n" +
                            "        .snippet,\n" +
                            "        .webversion {\n" +
                            "            width: 50% !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .ie .btn {\n" +
                            "            width: 100%;\n" +
                            "        }\n" +
                            "\n" +
                            "        [owa] .column div,\n" +
                            "        [owa] .column button {\n" +
                            "            display: block !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .ie .column,\n" +
                            "        [owa] .column,\n" +
                            "        .ie .gutter,\n" +
                            "        [owa] .gutter {\n" +
                            "            display: table-cell;\n" +
                            "            float: none !important;\n" +
                            "            vertical-align: top;\n" +
                            "        }\n" +
                            "\n" +
                            "        .ie div.preheader,\n" +
                            "        [owa] div.preheader,\n" +
                            "        .ie .email-footer,\n" +
                            "        [owa] .email-footer {\n" +
                            "            max-width: 560px !important;\n" +
                            "            width: 560px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .ie .snippet,\n" +
                            "        [owa] .snippet,\n" +
                            "        .ie .webversion,\n" +
                            "        [owa] .webversion {\n" +
                            "            width: 280px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .ie div.header,\n" +
                            "        [owa] div.header,\n" +
                            "        .ie .layout,\n" +
                            "        [owa] .layout,\n" +
                            "        .ie .one-col .column,\n" +
                            "        [owa] .one-col .column {\n" +
                            "            max-width: 600px !important;\n" +
                            "            width: 600px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .ie .fixed-width.has-border,\n" +
                            "        [owa] .fixed-width.has-border,\n" +
                            "        .ie .has-gutter.has-border,\n" +
                            "        [owa] .has-gutter.has-border {\n" +
                            "            max-width: 602px !important;\n" +
                            "            width: 602px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .ie .two-col .column,\n" +
                            "        [owa] .two-col .column {\n" +
                            "            max-width: 300px !important;\n" +
                            "            width: 300px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .ie .three-col .column,\n" +
                            "        [owa] .three-col .column,\n" +
                            "        .ie .narrow,\n" +
                            "        [owa] .narrow {\n" +
                            "            max-width: 200px !important;\n" +
                            "            width: 200px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .ie .wide,\n" +
                            "        [owa] .wide {\n" +
                            "            width: 400px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .ie .two-col.has-gutter .column,\n" +
                            "        [owa] .two-col.x_has-gutter .column {\n" +
                            "            max-width: 290px !important;\n" +
                            "            width: 290px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .ie .three-col.has-gutter .column,\n" +
                            "        [owa] .three-col.x_has-gutter .column,\n" +
                            "        .ie .has-gutter .narrow,\n" +
                            "        [owa] .has-gutter .narrow {\n" +
                            "            max-width: 188px !important;\n" +
                            "            width: 188px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .ie .has-gutter .wide,\n" +
                            "        [owa] .has-gutter .wide {\n" +
                            "            max-width: 394px !important;\n" +
                            "            width: 394px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .ie .two-col.has-gutter.has-border .column,\n" +
                            "        [owa] .two-col.x_has-gutter.x_has-border .column {\n" +
                            "            max-width: 292px !important;\n" +
                            "            width: 292px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .ie .three-col.has-gutter.has-border .column,\n" +
                            "        [owa] .three-col.x_has-gutter.x_has-border .column,\n" +
                            "        .ie .has-gutter.has-border .narrow,\n" +
                            "        [owa] .has-gutter.x_has-border .narrow {\n" +
                            "            max-width: 190px !important;\n" +
                            "            width: 190px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .ie .has-gutter.has-border .wide,\n" +
                            "        [owa] .has-gutter.x_has-border .wide {\n" +
                            "            max-width: 396px !important;\n" +
                            "            width: 396px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .ie .fixed-width .layout__inner {\n" +
                            "            border-left: 0 none white !important;\n" +
                            "            border-right: 0 none white !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .ie .layout__edges {\n" +
                            "            display: none;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .layout__edges {\n" +
                            "            font-size: 0;\n" +
                            "        }\n" +
                            "\n" +
                            "        .layout-fixed-width,\n" +
                            "        .mso .layout-full-width {\n" +
                            "            background-color: #ffffff;\n" +
                            "        }\n" +
                            "\n" +
                            "        @media only screen and (min-width: 620px) {\n" +
                            "            .column,\n" +
                            "            .gutter {\n" +
                            "                display: table-cell;\n" +
                            "                Float: none !important;\n" +
                            "                vertical-align: top;\n" +
                            "            }\n" +
                            "\n" +
                            "            div.preheader,\n" +
                            "            .email-footer {\n" +
                            "                max-width: 560px !important;\n" +
                            "                width: 560px !important;\n" +
                            "            }\n" +
                            "\n" +
                            "            .snippet,\n" +
                            "            .webversion {\n" +
                            "                width: 280px !important;\n" +
                            "            }\n" +
                            "\n" +
                            "            div.header,\n" +
                            "            .layout,\n" +
                            "            .one-col .column {\n" +
                            "                max-width: 600px !important;\n" +
                            "                width: 600px !important;\n" +
                            "            }\n" +
                            "\n" +
                            "            .fixed-width.has-border,\n" +
                            "            .fixed-width.ecxhas-border,\n" +
                            "            .has-gutter.has-border,\n" +
                            "            .has-gutter.ecxhas-border {\n" +
                            "                max-width: 602px !important;\n" +
                            "                width: 602px !important;\n" +
                            "            }\n" +
                            "\n" +
                            "            .two-col .column {\n" +
                            "                max-width: 300px !important;\n" +
                            "                width: 300px !important;\n" +
                            "            }\n" +
                            "\n" +
                            "            .three-col .column,\n" +
                            "            .column.narrow {\n" +
                            "                max-width: 200px !important;\n" +
                            "                width: 200px !important;\n" +
                            "            }\n" +
                            "\n" +
                            "            .column.wide {\n" +
                            "                width: 400px !important;\n" +
                            "            }\n" +
                            "\n" +
                            "            .two-col.has-gutter .column,\n" +
                            "            .two-col.ecxhas-gutter .column {\n" +
                            "                max-width: 290px !important;\n" +
                            "                width: 290px !important;\n" +
                            "            }\n" +
                            "\n" +
                            "            .three-col.has-gutter .column,\n" +
                            "            .three-col.ecxhas-gutter .column,\n" +
                            "            .has-gutter .narrow {\n" +
                            "                max-width: 188px !important;\n" +
                            "                width: 188px !important;\n" +
                            "            }\n" +
                            "\n" +
                            "            .has-gutter .wide {\n" +
                            "                max-width: 394px !important;\n" +
                            "                width: 394px !important;\n" +
                            "            }\n" +
                            "\n" +
                            "            .two-col.has-gutter.has-border .column,\n" +
                            "            .two-col.ecxhas-gutter.ecxhas-border .column {\n" +
                            "                max-width: 292px !important;\n" +
                            "                width: 292px !important;\n" +
                            "            }\n" +
                            "\n" +
                            "            .three-col.has-gutter.has-border .column,\n" +
                            "            .three-col.ecxhas-gutter.ecxhas-border .column,\n" +
                            "            .has-gutter.has-border .narrow,\n" +
                            "            .has-gutter.ecxhas-border .narrow {\n" +
                            "                max-width: 190px !important;\n" +
                            "                width: 190px !important;\n" +
                            "            }\n" +
                            "\n" +
                            "            .has-gutter.has-border .wide,\n" +
                            "            .has-gutter.ecxhas-border .wide {\n" +
                            "                max-width: 396px !important;\n" +
                            "                width: 396px !important;\n" +
                            "            }\n" +
                            "        }\n" +
                            "\n" +
                            "        @media only screen and (-webkit-min-device-pixel-ratio: 2), only screen and (min--moz-device-pixel-ratio: 2), only screen and (-o-min-device-pixel-ratio: 2/1), only screen and (min-device-pixel-ratio: 2), only screen and (min-resolution: 192dpi), only screen and (min-resolution: 2dppx) {\n" +
                            "            .fblike {\n" +
                            "                background-image: url(https://i7.createsend1.com/static/eb/master/13-the-blueprint-3/images/fblike@2x.png) !important;\n" +
                            "            }\n" +
                            "\n" +
                            "            .tweet {\n" +
                            "                background-image: url(https://i8.createsend1.com/static/eb/master/13-the-blueprint-3/images/tweet@2x.png) !important;\n" +
                            "            }\n" +
                            "\n" +
                            "            .linkedinshare {\n" +
                            "                background-image: url(https://i10.createsend1.com/static/eb/master/13-the-blueprint-3/images/lishare@2x.png) !important;\n" +
                            "            }\n" +
                            "\n" +
                            "            .forwardtoafriend {\n" +
                            "                background-image: url(https://i9.createsend1.com/static/eb/master/13-the-blueprint-3/images/forward@2x.png) !important;\n" +
                            "            }\n" +
                            "        }\n" +
                            "\n" +
                            "        @media (max-width: 321px) {\n" +
                            "            .fixed-width.has-border .layout__inner {\n" +
                            "                border-width: 1px 0 !important;\n" +
                            "            }\n" +
                            "\n" +
                            "            .layout,\n" +
                            "            .column {\n" +
                            "                min-width: 320px !important;\n" +
                            "                width: 320px !important;\n" +
                            "            }\n" +
                            "\n" +
                            "            .border {\n" +
                            "                display: none;\n" +
                            "            }\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso div {\n" +
                            "            border: 0 none white !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .w560 .divider {\n" +
                            "            Margin-left: 260px !important;\n" +
                            "            Margin-right: 260px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .w360 .divider {\n" +
                            "            Margin-left: 160px !important;\n" +
                            "            Margin-right: 160px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .w260 .divider {\n" +
                            "            Margin-left: 110px !important;\n" +
                            "            Margin-right: 110px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .w160 .divider {\n" +
                            "            Margin-left: 60px !important;\n" +
                            "            Margin-right: 60px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .w354 .divider {\n" +
                            "            Margin-left: 157px !important;\n" +
                            "            Margin-right: 157px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .w250 .divider {\n" +
                            "            Margin-left: 105px !important;\n" +
                            "            Margin-right: 105px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .w148 .divider {\n" +
                            "            Margin-left: 54px !important;\n" +
                            "            Margin-right: 54px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-8,\n" +
                            "        .ie .size-8 {\n" +
                            "            font-size: 8px !important;\n" +
                            "            line-height: 14px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-9,\n" +
                            "        .ie .size-9 {\n" +
                            "            font-size: 9px !important;\n" +
                            "            line-height: 16px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-10,\n" +
                            "        .ie .size-10 {\n" +
                            "            font-size: 10px !important;\n" +
                            "            line-height: 18px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-11,\n" +
                            "        .ie .size-11 {\n" +
                            "            font-size: 11px !important;\n" +
                            "            line-height: 19px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-12,\n" +
                            "        .ie .size-12 {\n" +
                            "            font-size: 12px !important;\n" +
                            "            line-height: 19px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-13,\n" +
                            "        .ie .size-13 {\n" +
                            "            font-size: 13px !important;\n" +
                            "            line-height: 21px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-14,\n" +
                            "        .ie .size-14 {\n" +
                            "            font-size: 14px !important;\n" +
                            "            line-height: 21px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-15,\n" +
                            "        .ie .size-15 {\n" +
                            "            font-size: 15px !important;\n" +
                            "            line-height: 23px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-16,\n" +
                            "        .ie .size-16 {\n" +
                            "            font-size: 16px !important;\n" +
                            "            line-height: 24px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-17,\n" +
                            "        .ie .size-17 {\n" +
                            "            font-size: 17px !important;\n" +
                            "            line-height: 26px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-18,\n" +
                            "        .ie .size-18 {\n" +
                            "            font-size: 18px !important;\n" +
                            "            line-height: 26px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-20,\n" +
                            "        .ie .size-20 {\n" +
                            "            font-size: 20px !important;\n" +
                            "            line-height: 28px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-22,\n" +
                            "        .ie .size-22 {\n" +
                            "            font-size: 22px !important;\n" +
                            "            line-height: 31px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-24,\n" +
                            "        .ie .size-24 {\n" +
                            "            font-size: 24px !important;\n" +
                            "            line-height: 32px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-26,\n" +
                            "        .ie .size-26 {\n" +
                            "            font-size: 26px !important;\n" +
                            "            line-height: 34px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-28,\n" +
                            "        .ie .size-28 {\n" +
                            "            font-size: 28px !important;\n" +
                            "            line-height: 36px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-30,\n" +
                            "        .ie .size-30 {\n" +
                            "            font-size: 30px !important;\n" +
                            "            line-height: 38px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-32,\n" +
                            "        .ie .size-32 {\n" +
                            "            font-size: 32px !important;\n" +
                            "            line-height: 40px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-34,\n" +
                            "        .ie .size-34 {\n" +
                            "            font-size: 34px !important;\n" +
                            "            line-height: 43px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-36,\n" +
                            "        .ie .size-36 {\n" +
                            "            font-size: 36px !important;\n" +
                            "            line-height: 43px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-40,\n" +
                            "        .ie .size-40 {\n" +
                            "            font-size: 40px !important;\n" +
                            "            line-height: 47px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-44,\n" +
                            "        .ie .size-44 {\n" +
                            "            font-size: 44px !important;\n" +
                            "            line-height: 50px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-48,\n" +
                            "        .ie .size-48 {\n" +
                            "            font-size: 48px !important;\n" +
                            "            line-height: 54px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-56,\n" +
                            "        .ie .size-56 {\n" +
                            "            font-size: 56px !important;\n" +
                            "            line-height: 60px !important;\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .size-64,\n" +
                            "        .ie .size-64 {\n" +
                            "            font-size: 64px !important;\n" +
                            "            line-height: 63px !important;\n" +
                            "        }\n" +
                            "    </style>\n" +
                            "\n" +
                            "    <style type=\"text/css\">\n" +
                            "        body {\n" +
                            "            background-color: #fff\n" +
                            "        }\n" +
                            "\n" +
                            "        .logo a:hover, .logo a:focus {\n" +
                            "            color: #859bb1 !important\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .layout-has-border {\n" +
                            "            border-top: 1px solid #ccc;\n" +
                            "            border-bottom: 1px solid #ccc\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .layout-has-bottom-border {\n" +
                            "            border-bottom: 1px solid #ccc\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .border, .ie .border {\n" +
                            "            background-color: #ccc\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso h1, .ie h1 {\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso h1, .ie h1 {\n" +
                            "            font-size: 64px !important;\n" +
                            "            line-height: 63px !important\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso h2, .ie h2 {\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso h2, .ie h2 {\n" +
                            "            font-size: 30px !important;\n" +
                            "            line-height: 38px !important\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso h3, .ie h3 {\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso h3, .ie h3 {\n" +
                            "            font-size: 22px !important;\n" +
                            "            line-height: 31px !important\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .layout__inner, .ie .layout__inner {\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .footer__share-button p {\n" +
                            "        }\n" +
                            "\n" +
                            "        .mso .footer__share-button p {\n" +
                            "            font-family: sans-serif\n" +
                            "        }\n" +
                            "    </style>\n" +
                            "    <meta name=\"robots\" content=\"noindex,nofollow\">\n" +
                            "    <meta property=\"og:title\" content=\"My First Campaign\">\n" +
                            "</head>\n" +
                            "<!--[if mso]>\n" +
                            "<body class=\"mso\">\n" +
                            "<![endif]-->\n" +
                            "<!--[if !mso]><!-->\n" +
                            "<body class=\"no-padding\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;\">\n" +
                            "<!--<![endif]-->\n" +
                            "<table class=\"wrapper\"\n" +
                            "       style=\"border-collapse: collapse;table-layout: fixed;min-width: 320px;width: 100%;background-color: #000000;\"\n" +
                            "       role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                            "    <tbody>\n" +
                            "    <tr>\n" +
                            "        <td>\n" +
                            "\n" +
                            "            <div role=\"section\">\n" +
                            "                <div style=\"background-color: #000000;\">\n" +
                            "                    <div class=\"layout one-col\"\n" +
                            "                         style=\"Margin: 0 auto;max-width: 600px;min-width: 320px; width: 320px;width: calc(28000% - 167400px);overflow-wrap: break-word;word-wrap: break-word;\">\n" +
                            "                        <div class=\"layout__inner\" style=\"border-collapse: collapse;display: table;width: 100%;\">\n" +
                            "                            <!--[if (mso)|(IE)]>\n" +
                            "                            <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\n" +
                            "                                <tr class=\"layout-full-width\" style=\"background-color: #000000;\">\n" +
                            "                                    <td class=\"layout__edges\">&nbsp;</td>\n" +
                            "                                    <td style=\"width: 600px\" class=\"w560\"><![endif]-->\n" +
                            "                            <div class=\"column\"\n" +
                            "                                 style=\"max-width: 600px;min-width: 320px; width: 320px;width: calc(28000% - 167400px);text-align: left;color: #8e959c;font-size: 14px;line-height: 21px;font-family: sans-serif;\">\n" +
                            "\n" +
                            "                                <div style=\"Margin-left: 20px;Margin-right: 20px;\">\n" +
                            "                                    <div style=\"mso-line-height-rule: exactly;line-height: 10px;font-size: 1px;\">\n" +
                            "                                        &nbsp;\n" +
                            "                                    </div>\n" +
                            "                                </div>\n" +
                            "\n" +
                            "                            </div>\n" +
                            "                            <!--[if (mso)|(IE)]></td>\n" +
                            "                        <td class=\"layout__edges\">&nbsp;</td></tr></table><![endif]-->\n" +
                            "                        </div>\n" +
                            "                    </div>\n" +
                            "                </div>\n" +
                            "\n" +
                            "                <div style=\"background-color: #000000;\">\n" +
                            "                    <div class=\"layout one-col\"\n" +
                            "                         style=\"Margin: 0 auto;max-width: 600px;min-width: 320px; width: 320px;width: calc(28000% - 167400px);overflow-wrap: break-word;word-wrap: break-word;\">\n" +
                            "                        <div class=\"layout__inner\" style=\"border-collapse: collapse;display: table;width: 100%;\">\n" +
                            "                            <!--[if (mso)|(IE)]>\n" +
                            "                            <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\n" +
                            "                                <tr class=\"layout-full-width\" style=\"background-color: #000000;\">\n" +
                            "                                    <td class=\"layout__edges\">&nbsp;</td>\n" +
                            "                                    <td style=\"width: 600px\" class=\"w560\"><![endif]-->\n" +
                            "                            <div class=\"column\"\n" +
                            "                                 style=\"max-width: 600px;min-width: 320px; width: 320px;width: calc(28000% - 167400px);text-align: left;color: #8e959c;font-size: 14px;line-height: 21px;font-family: sans-serif;\">\n" +
                            "\n" +
                            "                                <div style=\"Margin-left: 20px;Margin-right: 20px;\">\n" +
                            "                                    <div style=\"mso-line-height-rule: exactly;line-height: 110px;font-size: 1px;\">\n" +
                            "                                        &nbsp;\n" +
                            "                                    </div>\n" +
                            "                                </div>\n" +
                            "\n" +
                            "                                <div style=\"Margin-left: 20px;Margin-right: 20px;\">\n" +
                            "                                    <div style=\"mso-line-height-rule: exactly;mso-text-raise: 4px;\">\n" +
                            "                                        <h2 class=\"size-30\"\n" +
                            "                                            style=\"Margin-top: 0;Margin-bottom: 16px;font-style: normal;font-weight: normal;color: #e31212;font-size: 26px;line-height: 34px;font-family: Avenir,sans-serif;text-align: center;\"\n" +
                            "                                            lang=\"x-size-30\"><span style=\"color:#fff\">" + text1 + "</span>\n" +
                            "                                        </h2>\n" +
                            "                                    </div>\n" +
                            "                                </div>\n" +
                            "\n" +
                            "                                <div style=\"Margin-left: 20px;Margin-right: 20px;\">\n" +
                            "                                    <div style=\"mso-line-height-rule: exactly;line-height: 43px;font-size: 1px;\">\n" +
                            "                                        &nbsp;\n" +
                            "                                    </div>\n" +
                            "                                </div>\n" +
                            "\n" +
                            "                                <div style=\"Margin-left: 20px;Margin-right: 20px;\">\n" +
                            "                                    <div style=\"mso-line-height-rule: exactly;mso-text-raise: 4px;\">\n" +
                            "                                        <p class=\"size-40\"\n" +
                            "                                           style=\"Margin-top: 0;Margin-bottom: 20px;font-size: 32px;line-height: 40px;text-align: center;\"\n" +
                            "                                           lang=\"x-size-40\"><span\n" +
                            "                                                style=\"color:#fff\"><strong>" + text2 + "</strong></span>\n" +
                            "                                        </p>\n" +
                            "                                    </div>\n" +
                            "                                </div>\n" +
                            "\n" +
                            "                                <div style=\"Margin-left: 20px;Margin-right: 20px;\">\n" +
                            "                                    <div style=\"mso-line-height-rule: exactly;mso-text-raise: 4px;\">\n" +
                            "                                        <p class=\"size-40\"\n" +
                            "                                           style=\"Margin-top: 0;Margin-bottom: 20px;font-size: 32px;line-height: 40px;text-align: center;\"\n" +
                            "                                           lang=\"x-size-40\"><span style=\"color:#e31212\">" + code + "</span></p>\n" +
                            "                                    </div>\n" +
                            "                                </div>\n" +
                            "\n" +
                            "                                <div style=\"Margin-left: 20px;Margin-right: 20px;\">\n" +
                            "                                    <div style=\"mso-line-height-rule: exactly;line-height: 20px;font-size: 1px;\">\n" +
                            "                                        &nbsp;\n" +
                            "                                    </div>\n" +
                            "                                </div>\n" +
                            "\n" +
                            "                                <div style=\"Margin-left: 20px;Margin-right: 20px;\">\n" +
                            "                                    <div style=\"mso-line-height-rule: exactly;mso-text-raise: 4px;\">\n" +
                            "                                        <p class=\"size-18\"\n" +
                            "                                           style=\"Margin-top: 0;Margin-bottom: 20px;font-size: 17px;line-height: 26px;text-align: center;\"\n" +
                            "                                           lang=\"x-size-18\"><span style=\"color:#fff\">" + text3 + "</span><a\n" +
                            "                                                style=\"text-decoration: underline;transition: opacity 0.1s ease-in;color: #18527c;\"\n" +
                            "                                                href=\"" + confirmPageUrl + "\"\n" +
                            "                                                target=\"_blank\"><span style=\"color:#fff\">" + confirmationLinkName + "</span></a><span\n" +
                            "                                                style=\"color:#fff\">" + text4 + "</span>\n" +
                            "                                        </p>\n" +
                            "                                    </div>\n" +
                            "                                </div>\n" +
                            "                            </div>\n" +
                            "                            <!--[if (mso)|(IE)]></td>\n" +
                            "                        <td class=\"layout__edges\">&nbsp;</td></tr></table><![endif]-->\n" +
                            "                        </div>\n" +
                            "                    </div>\n" +
                            "                </div>\n" +
                            "\n" +
                            "                <div style=\"mso-line-height-rule: exactly;line-height: 15px;font-size: 15px;\">&nbsp;</div>\n" +
                            "\n" +
                            "                <div class=\"layout one-col fixed-width\"\n" +
                            "                     style=\"Margin: 0 auto;max-width: 600px;min-width: 320px; width: 320px;width: calc(28000% - 167400px);overflow-wrap: break-word;word-wrap: break-word;\">\n" +
                            "\n" +
                            "                </div>\n" +
                            "\n" +
                            "                <div style=\"mso-line-height-rule: exactly;line-height: 20px;font-size: 20px;\">&nbsp;</div>\n" +
                            "\n" +
                            "            </div>\n" +
                            "        </td>\n" +
                            "    </tr>\n" +
                            "    </tbody>\n" +
                            "</table>\n" +
                            "\n" +
                            "</body>",
                    "text/html; charset=utf-8");

            // Send message
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}