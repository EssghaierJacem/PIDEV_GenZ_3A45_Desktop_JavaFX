package tn.esprit.entites;

import javax.mail.Session;


import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;


public class EmailSender {
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL_USERNAME = "malekbdiri06@gmail.com";
    private static final String EMAIL_PASSWORD = "eoaq tsch zyks xcfm";
    private static final Session session = createSession();

    private static Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        return Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
                    }
                });


    }
    public static void sendWelcomeEmailWithSignature(String recipientEmail, String nom) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Rappel Visite");
            Multipart multipart = new MimeMultipart();
            String emailContentWithSignature = "<html>" +
                    "<body>" +
                    "<p>Cher " + nom + ",</p>" +
                    "<p>Nous vous remercions chaleureusement d'avoir confirmé votre participation à notre événement. C'est avec joie que nous vous accueillerons parmi nous.\n" +
                    "\n" +
                    "Votre engagement contribue à rendre cet événement encore plus spécial et mémorable pour tous les participants.\n" +
                    "\n" +
                    "Nous avons hâte de vous retrouver le jour de l'événement et de partager des moments exceptionnels ensemble.\n" +
                    "\n" +
                    "Encore une fois, merci pour votre soutien et votre participation.</p>" +
                    "<p>Cordialement,<br></p>" +
                    "</body>" +
                    "</html>";
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(emailContentWithSignature, "text/html");
            multipart.addBodyPart(textPart);




            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email : " + e.getMessage());
        }
    }



}
