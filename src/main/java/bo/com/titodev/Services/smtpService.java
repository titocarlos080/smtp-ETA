package bo.com.titodev.Services;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class smtpService {

    private final static String PORT_SMTP = "587";
    private final static String HOST = "smtp.gmail.com";
    private final static String USER = "titodev83@gmail.com";
    private final static String MAIL_PASSWORD = "wfjd nwcs glcz gkkw";// "wfjd nwcs glcz gkkw";

    public smtpService() {
    }

    public void sendEmail(String receptor, String mensaje) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Use STARTTLS for encryption
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", PORT_SMTP); // Standard port for STARTTLS
        properties.put("mail.smtp.ssl.trust", HOST); // Trust the SMTP host

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER, MAIL_PASSWORD);
            }
        });

        try {
            // Create an email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USER)); // Set the sender email address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receptor)); // Set the recipient email
                                                                                              // address
            message.setSubject(" INSTITUTO-ETA "); // Set the subject of the email

            // Send the email
            String mensajeCompleto = "<html>" +
                    "<body style='font-family: Arial, sans-serif; background-color: #fff; '>" +
                    "<div style='max-width: 600px; margin: auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);'>"
                    +
                    mensaje +
                    "</div>" +
                    "</body>" +
                    "</html>";

            message.setContent(mensajeCompleto, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException ex) {
            System.out.println("Messaging error: " + ex);
        }
    }
}
