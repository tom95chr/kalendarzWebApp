package pl.pwsztar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.pwsztar.daos.ReservationDAO;
import pl.pwsztar.entities.Reservation;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Service
public class EmailService {

    @Value("${email.username}")
    protected String username;
    @Value("${email.password}")
    protected String password;
    @Value("${email.username}")
    protected String mailEmailFrom;
    protected String mailSmtpAuth = "true";
    protected String mailSmtpHost = "smtp.gmail.com";
    protected String mailSmtpPort = "587";
    protected String mailSmtpStarttlsEnable = "true";

    public void sendHtmlEmail(Reservation reservation, String recipientEmail, Character messageType, String confirmationUrl) {

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

        //choose type
        String subject;
        String intro,code,afterCode,details,button,footer, footer2;
        switch (messageType) {
            case 'n':
                subject = "Proszę o potwierdzenie rezerwacji terminu spotkania.";
                intro = "Witaj, oto unikalny kod Twojej rezerwacji: <br>";
                code = reservation.getConfirmationCode();
                afterCode = "Rezerwację możesz potwierdzić na dwa sposoby:" +
                        "<br>• Używając kodu rezerwacji" +
                        "<br>• Klikając w przycisk ,,Potwierdź / Zobacz rezerwację''";
                details = "SZCZEGÓŁY SPOTKANIA <br>"+
                        "specjalista:<br>"+reservation.getEvent().getTherapist().getSpecialization() +
                        " " +reservation.getEvent().getTherapist().getFirstName() + " " +
                        reservation.getEvent().getTherapist().getLastName()+"<br>"+
                        "Typ spotkania: "+ reservation.getEvent().getEventType().getEventTypeId()+
                        "<br>Data: "+reservation.getEvent().getStartDateTime().toLocalDate()+"" +
                        "<br>Godzina: "+ reservation.getEvent().getStartDateTime().toLocalTime()+
                        "<br>Czas trwania: "+reservation.getEvent().calculateDuration()+" min."+
                        "<br>Sala nr. : "+ reservation.getEvent().getRoom();
                button ="                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\">\n" +
                        "                          <tbody>\n" +
                        "                            <tr>\n" +
                        "                              <td align=\"center\">\n" +
                        "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                        "                                  <tbody>\n" +
                        "                                    <tr>\n" +
                        "                                      <td> <a href=\""+confirmationUrl+"\" target=\"_blank\">Potwierdź / Zobacz rezerwację</a> </td>\n" +
                        "                                    </tr>\n" +
                        "                                  </tbody>\n" +
                        "                                </table>\n" +
                        "                              </td>\n" +
                        "                            </tr>\n" +
                        "                          </tbody>\n" +
                        "                        </table>";
                footer = "<br>W każdym momencie możesz podglądnąć szczegóły swojej rezerwacji klikając w przycisk powyżej ↑ ↑ ↑";
                footer2 = "UWAGA! Jeśli nie potwierdzisz swojej rezerwacji w przeciągu 10 minut od momentu otrzymania " +
                        "niniejszej wiadomości, to Twoja rezerwacja zostanie automatycznie usunięta, a wybrany przez " +
                        "Ciebie termin znów pojawi się jako dostępny. Jeśli zrezygnujesz z wzięcia udziału w spotkaniu," +
                        " prosimy o anulację rezerwacji na stronie szczegółów rezerwacji (kliknij w przycisk " +
                        ",,Potwierdź / Zobacz rezerwację'' a następnie anuluj rezerwację.)";
                break;
            case 'e':
                subject = "Modyfikacja spotkania";
                intro = "Witaj, Twoje spotkanie z terapeutą w Uczelnianym Centrum Wsparcia przy PWSZ w Tarnowie" +
                        " zostało edytowane.<br><br>Kod rezerwacji:<br>";
                code = reservation.getConfirmationCode();
                afterCode = "";
                details = "SZCZEGÓŁY SPOTKANIA PO EDYCJI<br>"+
                        "specjalista:<br>"+reservation.getEvent().getTherapist().getSpecialization() +
                        " " +reservation.getEvent().getTherapist().getFirstName() + " " +
                        reservation.getEvent().getTherapist().getLastName()+"<br>"+
                        "Typ spotkania: "+ reservation.getEvent().getEventType().getEventTypeId()+
                        "<br>Data: "+reservation.getEvent().getStartDateTime().toLocalDate()+"" +
                        "<br>Godzina: "+ reservation.getEvent().getStartDateTime().toLocalTime()+
                        "<br>Czas trwania: "+reservation.getEvent().calculateDuration()+" min."+
                        "<br>Sala nr. : "+ reservation.getEvent().getRoom();
                button ="                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\">\n" +
                        "                          <tbody>\n" +
                        "                            <tr>\n" +
                        "                              <td align=\"center\">\n" +
                        "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                        "                                  <tbody>\n" +
                        "                                    <tr>\n" +
                        "                                      <td> <a href=\""+confirmationUrl+"\" target=\"_blank\">Potwierdź / Zobacz rezerwację</a> </td>\n" +
                        "                                    </tr>\n" +
                        "                                  </tbody>\n" +
                        "                                </table>\n" +
                        "                              </td>\n" +
                        "                            </tr>\n" +
                        "                          </tbody>\n" +
                        "                        </table>";
                footer = "<br>W każdym momencie możesz podglądnąć szczegóły swojej rezerwacji klikając w przycisk powyżej ↑ ↑ ↑";
                footer2 = "UWAGA! Jeśli zrezygnujesz z wzięcia udziału w spotkaniu," +
                        " prosimy o anulację rezerwacji na stronie szczegółów rezerwacji (kliknij w przycisk " +
                        ",,Potwierdź / Zobacz rezerwację'' a następnie anuluj rezerwację.)";
                break;
            case 'c':
                subject = "Odwołano Twoje spotkanie ze specjalistą.";
                intro = "Witaj, Twoje spotkanie z terapeutą w Uczelnianym Centrum Wsparcia przy PWSZ w Tarnowie" +
                        " zostało odwołane<br><br>";
                code = "";
                afterCode = "";
                details = "SZCZEGÓŁY ODWOŁANEGO SPOTKANIA<br>"+
                        "specjalista:<br>"+reservation.getEvent().getTherapist().getSpecialization() +
                        " " +reservation.getEvent().getTherapist().getFirstName() + " " +
                        reservation.getEvent().getTherapist().getLastName()+"<br>"+
                        "Typ spotkania: "+ reservation.getEvent().getEventType().getEventTypeId()+
                        "<br>Data: "+reservation.getEvent().getStartDateTime().toLocalDate()+"" +
                        "<br>Godzina: "+ reservation.getEvent().getStartDateTime().toLocalTime()+
                        "<br>Czas trwania: "+reservation.getEvent().calculateDuration()+" min."+
                        "<br>Sala nr. : "+ reservation.getEvent().getRoom();
                button = "";
                footer = "Przepraszamy za utrudnienia. Zapraszamy ponownie.";
                footer2 = "";
                break;
            default:
                subject = "";
                intro = "";
                code = "";
                afterCode = "";
                details = "";
                button = "";
                footer = "";
                footer2 = "";
                break;
        }
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
            message.setContent("<!doctype html>\n" +
                            "<html>\n" +
                            "  <head>\n" +
                            "    <meta name=\"viewport\" content=\"width=device-width\" />\n" +
                            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                            "    <title>Simple Transactional Email</title>\n" +
                            "    <style>\n" +
                            "      /* -------------------------------------\n" +
                            "          GLOBAL RESETS\n" +
                            "      ------------------------------------- */\n" +
                            "      img {\n" +
                            "        border: none;\n" +
                            "        -ms-interpolation-mode: bicubic;\n" +
                            "        max-width: 100%; }\n" +
                            "      body {\n" +
                            "        background-color: #f6f6f6;\n" +
                            "        font-family: sans-serif;\n" +
                            "        -webkit-font-smoothing: antialiased;\n" +
                            "        font-size: 14px;\n" +
                            "        line-height: 1.4;\n" +
                            "        margin: 0;\n" +
                            "        padding: 0;\n" +
                            "        -ms-text-size-adjust: 100%;\n" +
                            "        -webkit-text-size-adjust: 100%; }\n" +
                            "      table {\n" +
                            "        border-collapse: separate;\n" +
                            "        mso-table-lspace: 0pt;\n" +
                            "        mso-table-rspace: 0pt;\n" +
                            "        width: 100%; }\n" +
                            "        table td {\n" +
                            "          font-family: sans-serif;\n" +
                            "          font-size: 14px;\n" +
                            "          vertical-align: top; }\n" +
                            "      /* -------------------------------------\n" +
                            "          BODY & CONTAINER\n" +
                            "      ------------------------------------- */\n" +
                            "      .body {\n" +
                            "        background-color: #f6f6f6;\n" +
                            "        width: 100%; }\n" +
                            "      /* Set a max-width, and make it display as block so it will automatically stretch to that width, but will also shrink down on a phone or something */\n" +
                            "      .container {\n" +
                            "        display: block;\n" +
                            "        Margin: 0 auto !important;\n" +
                            "        /* makes it centered */\n" +
                            "        max-width: 580px;\n" +
                            "        padding: 10px;\n" +
                            "        width: 580px; }\n" +
                            "      /* This should also be a block element, so that it will fill 100% of the .container */\n" +
                            "      .content {\n" +
                            "        box-sizing: border-box;\n" +
                            "        display: block;\n" +
                            "        Margin: 0 auto;\n" +
                            "        max-width: 580px;\n" +
                            "        padding: 10px; }\n" +
                            "      /* -------------------------------------\n" +
                            "          HEADER, FOOTER, MAIN\n" +
                            "      ------------------------------------- */\n" +
                            "      .main {\n" +
                            "        background: #ffffff;\n" +
                            "        border-radius: 3px;\n" +
                            "        width: 100%; }\n" +
                            "      .wrapper {\n" +
                            "        box-sizing: border-box;\n" +
                            "        text-align: center;\n" +
                            "        padding: 20px; }\n" +
                            "      .content-block {\n" +
                            "        padding-bottom: 10px;\n" +
                            "        padding-top: 10px;\n" +
                            "      }\n" +
                            "      .footer {\n" +
                            "        clear: both;\n" +
                            "        Margin-top: 10px;\n" +
                            "        text-align: center;\n" +
                            "        width: 100%; }\n" +
                            "        .footer td,\n" +
                            "        .footer p,\n" +
                            "        .footer span,\n" +
                            "        .footer a {\n" +
                            "          color: #999999;\n" +
                            "          font-size: 12px;\n" +
                            "          text-align: center; }\n" +
                            "      /* -------------------------------------\n" +
                            "          TYPOGRAPHY\n" +
                            "      ------------------------------------- */\n" +
                            "      h1,\n" +
                            "      h2,\n" +
                            "      h3,\n" +
                            "      h4 {\n" +
                            "        color: #000000;\n" +
                            "        font-family: sans-serif;\n" +
                            "        font-weight: 400;\n" +
                            "        line-height: 1.4;\n" +
                            "        margin: 0;\n" +
                            "        Margin-bottom: 30px; }\n" +
                            "      h1 {\n" +
                            "        font-size: 35px;\n" +
                            "        font-weight: 300;\n" +
                            "        text-align: center;\n" +
                            "        text-transform: capitalize; }\n" +
                            "      p,\n" +
                            "      ul,\n" +
                            "      ol {\n" +
                            "        font-family: sans-serif;\n" +
                            "        font-size: 14px;\n" +
                            "        font-weight: normal;\n" +
                            "        margin: 0;\n" +
                            "        Margin-bottom: 15px; }\n" +
                            "        p li,\n" +
                            "        ul li,\n" +
                            "        ol li {\n" +
                            "          list-style-position: inside;\n" +
                            "          margin-left: 5px; }\n" +
                            "      a {\n" +
                            "        color: #3498db;\n" +
                            "        text-decoration: underline; }\n" +
                            "      /* -------------------------------------\n" +
                            "          BUTTONS\n" +
                            "      ------------------------------------- */\n" +
                            "      .btn {\n" +
                            "        box-sizing: border-box;\n" +
                            "        width: 100%; }\n" +
                            "        .btn > tbody > tr > td {\n" +
                            "          padding-bottom: 15px; }\n" +
                            "        .btn table {\n" +
                            "          width: auto; }\n" +
                            "        .btn table td {\n" +
                            "          background-color: #ffffff;\n" +
                            "          border-radius: 5px;\n" +
                            "          text-align: center; }\n" +
                            "        .btn a {\n" +
                            "          background-color: #ffffff;\n" +
                            "          border: solid 1px #3498db;\n" +
                            "          border-radius: 5px;\n" +
                            "          box-sizing: border-box;\n" +
                            "          color: #3498db;\n" +
                            "          cursor: pointer;\n" +
                            "          display: inline-block;\n" +
                            "          font-size: 14px;\n" +
                            "          font-weight: bold;\n" +
                            "          margin: 0;\n" +
                            "          padding: 12px 25px;\n" +
                            "          text-decoration: none;\n" +
                            "          text-transform: capitalize; }\n" +
                            "      .btn-primary table td {\n" +
                            "        background-color: #3498db; }\n" +
                            "      .btn-primary a {\n" +
                            "        background-color: #3498db;\n" +
                            "        border-color: #3498db;\n" +
                            "        color: #ffffff; }\n" +
                            "      /* -------------------------------------\n" +
                            "          OTHER STYLES THAT MIGHT BE USEFUL\n" +
                            "      ------------------------------------- */\n" +
                            "      .last {\n" +
                            "        margin-bottom: 0; }\n" +
                            "      .first {\n" +
                            "        margin-top: 0; }\n" +
                            "      .align-center {\n" +
                            "        text-align: center; }\n" +
                            "      .align-right {\n" +
                            "        text-align: right; }\n" +
                            "      .align-left {\n" +
                            "        text-align: left; }\n" +
                            "      .clear {\n" +
                            "        clear: both; }\n" +
                            "      .mt0 {\n" +
                            "        margin-top: 0; }\n" +
                            "      .mb0 {\n" +
                            "        margin-bottom: 0; }\n" +
                            "      .preheader {\n" +
                            "        color: transparent;\n" +
                            "        display: none;\n" +
                            "        height: 0;\n" +
                            "        max-height: 0;\n" +
                            "        max-width: 0;\n" +
                            "        opacity: 0;\n" +
                            "        overflow: hidden;\n" +
                            "        mso-hide: all;\n" +
                            "        visibility: hidden;\n" +
                            "        width: 0; }\n" +
                            "      .powered-by a {\n" +
                            "        text-decoration: none; }\n" +
                            "      hr {\n" +
                            "        border: 0;\n" +
                            "        border-bottom: 1px solid #f6f6f6;\n" +
                            "        Margin: 20px 0; }\n" +
                            "      /* -------------------------------------\n" +
                            "          RESPONSIVE AND MOBILE FRIENDLY STYLES\n" +
                            "      ------------------------------------- */\n" +
                            "      @media only screen and (max-width: 620px) {\n" +
                            "        table[class=body] h1 {\n" +
                            "          font-size: 28px !important;\n" +
                            "          margin-bottom: 10px !important; }\n" +
                            "        table[class=body] p,\n" +
                            "        table[class=body] ul,\n" +
                            "        table[class=body] ol,\n" +
                            "        table[class=body] td,\n" +
                            "        table[class=body] span,\n" +
                            "        table[class=body] a {\n" +
                            "          font-size: 16px !important; }\n" +
                            "        table[class=body] .wrapper,\n" +
                            "        table[class=body] .article {\n" +
                            "          padding: 10px !important; }\n" +
                            "        table[class=body] .content {\n" +
                            "          padding: 0 !important; }\n" +
                            "        table[class=body] .container {\n" +
                            "          padding: 0 !important;\n" +
                            "          width: 100% !important; }\n" +
                            "        table[class=body] .main {\n" +
                            "          border-left-width: 0 !important;\n" +
                            "          border-radius: 0 !important;\n" +
                            "          border-right-width: 0 !important; }\n" +
                            "        table[class=body] .btn table {\n" +
                            "          width: 100% !important; }\n" +
                            "        table[class=body] .btn a {\n" +
                            "          width: 100% !important; }\n" +
                            "        table[class=body] .img-responsive {\n" +
                            "          height: auto !important;\n" +
                            "          max-width: 100% !important;\n" +
                            "          width: auto !important; }}\n" +
                            "      /* -------------------------------------\n" +
                            "          PRESERVE THESE STYLES IN THE HEAD\n" +
                            "      ------------------------------------- */\n" +
                            "      @media all {\n" +
                            "        .ExternalClass {\n" +
                            "          width: 100%; }\n" +
                            "        .ExternalClass,\n" +
                            "        .ExternalClass p,\n" +
                            "        .ExternalClass span,\n" +
                            "        .ExternalClass font,\n" +
                            "        .ExternalClass td,\n" +
                            "        .ExternalClass div {\n" +
                            "          line-height: 100%; }\n" +
                            "        .apple-link a {\n" +
                            "          color: inherit !important;\n" +
                            "          font-family: inherit !important;\n" +
                            "          font-size: inherit !important;\n" +
                            "          font-weight: inherit !important;\n" +
                            "          line-height: inherit !important;\n" +
                            "          text-decoration: none !important; }\n" +
                            "        .btn-primary table td:hover {\n" +
                            "          background-color: #34495e !important; }\n" +
                            "        .btn-primary a:hover {\n" +
                            "          background-color: #34495e !important;\n" +
                            "          border-color: #34495e !important; } }\n" +
                            "    </style>\n" +
                            "  </head>\n" +
                            "  <body class=\"\">\n" +
                            "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\">\n" +
                            "      <tr>\n" +
                            "        <td>&nbsp;</td>\n" +
                            "        <td class=\"container\">\n" +
                            "          <div class=\"content\">\n" +
                            "\n" +
                            "            <!-- START CENTERED WHITE CONTAINER -->\n" +
                            "            <span class=\"preheader\">Kod rezerwacji: "+code+"</span>\n" +
                            "            <table class=\"main\">\n" +
                            "\n" +
                            "              <!-- START MAIN CONTENT AREA -->\n" +
                            "              <tr>\n" +
                            "                <td class=\"wrapper\">\n" +
                            "                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                            "                    <tr>\n" +
                            "                      <td>\n" +
                            "                        <p>"+intro+"</p>\n" +
                            "                        <h3 style=\"color: #000bff\">"+code+"</h3>\n" +
                            "                        <p>"+afterCode+"</p>\n" +
                            "                        <p style=\"font-weight: bold\">"+details+"</p>" +button+
                            "                        <p>"+footer+"</p>\n" +
                            "                        <p>"+footer2+"</p>\n" +
                            "                      </td>\n" +
                            "                    </tr>\n" +
                            "                  </table>\n" +
                            "                </td>\n" +
                            "              </tr>\n" +
                            "\n" +
                            "            <!-- END MAIN CONTENT AREA -->\n" +
                            "            </table>\n" +
                            "\n" +
                            "            <!-- START FOOTER -->\n" +
                            "            <div class=\"footer\">\n" +
                            "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                            "                <tr>\n" +
                            "                  <td class=\"content-block\">\n" +
                            "                    <span class=\"apple-link\">Uczelniane Centrum Wsparcia przy PWSZ w Tarnowie</span>\n" +
                            "                  </td>\n" +
                            "                </tr>\n" +
                            "                <tr>\n" +
                            "                  <td class=\"content-block powered-by\">\n" +
                            "                    Nie odpowiadaj na ten email. Został on wygenerowany automatycznie.\n" +
                            "                  </td>\n" +
                            "                </tr>\n" +
                            "              </table>\n" +
                            "            </div>\n" +
                            "            <!-- END FOOTER -->\n" +
                            "\n" +
                            "          <!-- END CENTERED WHITE CONTAINER -->\n" +
                            "          </div>\n" +
                            "        </td>\n" +
                            "        <td>&nbsp;</td>\n" +
                            "      </tr>\n" +
                            "    </table>\n" +
                            "  </body>\n" +
                            "</html>",
                    "text/html; charset=utf-8");

            // Send message
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void sendEmail(String recipientEmail, String subject, String content) {

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

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public void resetPasswordEmail(String recipientEmail, String subject, String token, String emailToken) {

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
            message.setContent("<head></head><body><a href=http://localhost:8080/reset-"+token+"-"+emailToken+">" +
                            "Kliknij tutaj aby zresetować hasło</a><br><p>Zostaniesz przekierowany do " +
                            "formularza odzyskiwania hasła.</p>",
                    "text/html; charset=utf-8");

            // Send message
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}