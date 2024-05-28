package bo.com.titodev.Services;

import java.io.*;
import java.net.*;

import bo.com.titodev.Utils.*;
 
public class popService {

    private final String SERVER ="mail.titodev.com.bo"; //"mail.tecnoweb.org.bo";
    private final String USUARIO = "tito";//"grupo05sc";
    private final String CONTRASEÑA ="123"; //"grup005grup005"; 
    private final int PUERTO = 110;

    public popService() {
     }

    public int getCantidadEmails() {
        String comando = "";
        String number = "";
        try {
            Socket socket = new Socket(SERVER, PUERTO);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            if (socket != null && entrada != null && salida != null) {

                comando = "USER " + USUARIO + "\r\n";
                salida.writeBytes(comando);

                comando = "PASS " + CONTRASEÑA + "\r\n";
                salida.writeBytes(comando);

                comando = "LIST \r\n";
                salida.writeBytes(comando);
                number = getLastMail(entrada);

                comando = "QUIT\r\n";
                salida.writeBytes(comando);
            }
            salida.close();
            entrada.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println(" S : no se pudo conectar con el servidor indicado");
        } catch (IOException e) {
            e.printStackTrace();
        }
         return Integer.parseInt(number);
    }

    public void getMail() {
        String comando = "";
        String number = "";
        String subject = "";
        String mailEmisor = "";
        try {
            Socket socket = new Socket(SERVER, PUERTO);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            if (socket != null && entrada != null && salida != null) {

                comando = "USER " + USUARIO + "\r\n";
                salida.writeBytes(comando);

                comando = "PASS " + CONTRASEÑA + "\r\n";
                salida.writeBytes(comando);

                comando = "LIST \r\n";
                salida.writeBytes(comando);
                number = getLastMail(entrada);

                comando = "RETR " + number + "\n";
                salida.writeBytes(comando);
                String text = getMultiline(entrada);
                //System.out.println("Text: " + text);

                mailEmisor = extractEmailValue(text);
                subject = extractSubjectValue(text);
                System.out.println("EMAIL: " + mailEmisor);
                System.out.println("COMANDO: " + subject);

                comando = "QUIT\r\n";
                salida.writeBytes(comando);
            }
            salida.close();
            entrada.close();
            socket.close();

            if (subject != "" && mailEmisor != "" && validatorUtils.validateEmail(mailEmisor)) {
                subjectValidator validatorSubject = new subjectValidator(subject,mailEmisor);

                validatorSubject.ValidateSuject();

            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println(" S : no se pudo conectar con el servidor indicado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLastMail(BufferedReader in) throws IOException {
        String number = "";
        String line = "";
        String anteriorLine = "";
        while (true) {
            anteriorLine = line;
            line = in.readLine();
            if (line == null) {
                throw new IOException(" S : Server unawares closed the connection.");
            }
            if (line.equals(".")) {
                break;
            }
        }
        number = anteriorLine.substring(0, anteriorLine.indexOf(" "));
        number = number.trim();
        return number;
    }

    public String extractSubjectValue(String inputString) {
        // posicion de Subject
        int startIndex = inputString.indexOf("Subject:") + "Subject:".length();
        // posicion de In-Reply-To or To
        int endIndex = inputString.indexOf("To:");
        if (endIndex == -1 || endIndex < startIndex) {
            endIndex = inputString.indexOf("In-Reply-To:");
        }

        // obtener el texto entre Subject y In-Reply-To
        String subject = inputString.substring(startIndex, endIndex);

        // eliminar espacios en blanco al inicio y al final
        subject = subject.trim();
        // eliminar los \n
        subject = subject.replace("\n", "");
        // eliminar los \r
        subject = subject.replace("\r", "");

        return subject;
    }

    public String extractEmailValue(String inputString) {
        int startIndex = inputString.indexOf("From:") + "From:".length();
        int endIndex = inputString.indexOf("\n", startIndex);
        String subject = inputString.substring(startIndex, endIndex);
        // Tito Carlos <titocarlos080@gmail.com>
        String[] parts = subject.split("<");
        String part2 = parts[1]; // 034556
        part2 = part2.replace(">", "");
        part2 = part2.trim();
        return part2;
    }

    static protected String getMultiline(BufferedReader in) throws IOException {
        String lines = "";
        while (true) {
            String line = in.readLine();
            if (line == null) {
                // Server closed connection
                throw new IOException(" S : Server unawares closed the connection.");
            }
            if (line.equals(".")) {
                // No more lines in the server response
                break;
            }
            if ((line.length() > 0) && (line.charAt(0) == '.')) {
                // The line starts with a "." - strip it off.
                line = line.substring(1);
            }
            // Add read line to the list of lines
            lines = lines + "\n" + line;
        }
        return lines;
    }

}
