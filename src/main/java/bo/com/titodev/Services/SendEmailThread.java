 package bo.com.titodev.Services;


import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

//SOLO TOCAR LAS CREDENCIALES
public class SendEmailThread implements Runnable {
    
    /*
    private final static String PORT_SMTP = "465";
    private final static String PROTOCOL = "smtp";
    private final static String HOST = "smtp.gmail.com";
    private final static String USER = "ramirez.ricky2021@gmail.com";
    private final static String MAIL = "ramirez.ricky2021@gmail.com";
    private final static String MAIL_PASSWORD = "lttp ecsu ywmh jdge";
    
    
    private final static String PORT_SMTP = "465";
    private final static String PROTOCOL = "smtp";
    private final static String HOST = "smtp.googlemail.com";
    private final static String USER = "carlosenrique753p@gmail.com";
    private final static String MAIL = "carlosenrique753p@gmail.com";
    private final static String MAIL_PASSWORD = "jmia tdit gtew ihxl";
    */
   
    private final static String PORT_SMTP = "25";
    private final static String PROTOCOL = "smtp";
    private final static String HOST = "mail.tecnoweb.org.bo";
    private final static String MAIL = "grupo06sc@tecnoweb.org.bo";
    
    private String received;
    private String subject;
    private String body;

    public SendEmailThread(String received, String subject, String body) {
        this.received = received;
        this.subject = subject;
        this.body = body;
    }

    @Override
    public void run() {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", PROTOCOL);
        properties.setProperty("mail.smtp.host", HOST);
        properties.setProperty("mail.smtp.port", PORT_SMTP);
      //  properties.setProperty("mail.smtp.starttls.enable", "true"); // Cambiado de tls a starttls
        // No se agrega propiedades de autenticación

        Session session = Session.getInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(MAIL));

            InternetAddress[] toAddresses = {new InternetAddress(received)};
            message.setRecipients(MimeMessage.RecipientType.TO, toAddresses);
            message.setSubject(subject);

            Multipart multipart = new MimeMultipart("alternative");
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(body, "text/html; charset=utf-8");
            multipart.addBodyPart(htmlPart);
            message.setContent(multipart);
            message.saveChanges();

            Transport.send(message);
            System.out.println("MENSAJE ENVIADO CON EXITO");
        } catch (NoSuchProviderException | AddressException ex) {
            Logger.getLogger(SendEmailThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(SendEmailThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}