package bo.com.titodev.Services;

import java.io.*;
import java.net.*;

import bo.com.titodev.Utils.*;

public class popServiceGmail {

    // Constantes para la configuración del servidor POP3
    private final String SERVER = "gonzalotech.ciencia.bo";//"mail.tecnoweb.org.bo";
    private final String USUARIO ="tito.carlos"; //"grupo05sc";
    private final String CONTRASEÑA ="tito";// "grup005grup005";
    private final int PUERTO = 110;

    // Constructor de la clase
    public popServiceGmail() {
    }

    /**
     * Método para obtener la cantidad de correos electrónicos en el servidor.
     *
     * @return La cantidad de correos electrónicos.
     */
    public int getCantidadEmails() {
        String number = "";
        try (
                // Conexión al servidor POP3
                Socket socket = new Socket(SERVER, PUERTO);
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                DataOutputStream salida = new DataOutputStream(socket.getOutputStream())) {
            if (socket.isConnected()) {
                // Enviar comandos para autenticarse y obtener la lista de correos
                if (!executeCommand(salida, entrada, "USER " + USUARIO + "\r\n"))
                    return 0;
                if (!executeCommand(salida, entrada, "PASS " + CONTRASEÑA + "\r\n"))
                    return 0;
                if (!executeCommand(salida, entrada, "LIST\r\n"))
                    return 0;

                // Obtener el último correo de la lista
                number = getLastMail(entrada);

                // Cerrar la sesión
                executeCommand(salida, entrada, "QUIT\r\n");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println(" S : no se pudo conectar con el servidor indicado");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Hola este la cantidad de numeros "+number);
        return Integer.parseInt(number);
    }

    /**
     * Método para obtener y procesar el correo más reciente.
     */
    public void getMail() {
        String number;
        String subject;
        String mailEmisor;
        try (
                // Conexión al servidor POP3
                Socket socket = new Socket(SERVER, PUERTO);
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                DataOutputStream salida = new DataOutputStream(socket.getOutputStream())) {
            if (socket.isConnected()) {
                // Enviar comandos para autenticarse y obtener la lista de correos
                if (!executeCommand(salida, entrada, "USER " + USUARIO + "\r\n"))
                    return;
                if (!executeCommand(salida, entrada, "PASS " + CONTRASEÑA + "\r\n"))
                    return;
                if (!executeCommand(salida, entrada, "LIST\r\n"))
                    return;

                // Obtener el número del último correo
                number = getLastMail(entrada);

                // Recuperar y procesar el contenido del correo
                if (executeCommand(salida, entrada, "RETR " + number + "\n")) {
                    String text = getMultiline(entrada);
                    mailEmisor = extractEmailValue(text);
                    subject = extractSubjectValue(text);
                    System.out.println("EMAIL: " + mailEmisor);
                    System.out.println("COMANDO: " + subject);

                    // Validar y procesar el asunto del correo
                    if (!subject.isEmpty() && !mailEmisor.isEmpty() && validatorUtils.validateEmail(mailEmisor)) {
                        subjectValidator validatorSubject = new subjectValidator(subject, mailEmisor);
                        validatorSubject.ValidateSuject();
                    }
                }

                // Cerrar la sesión
                executeCommand(salida, entrada, "QUIT\r\n");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println(" S : no se pudo conectar con el servidor indicado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean executeCommand(DataOutputStream salida, BufferedReader entrada, String command) throws IOException {
        salida.writeBytes(command);
        String response = entrada.readLine();
        return response != null && response.startsWith("+OK");
    }

    public String getLastMail(BufferedReader in) throws IOException {
        String number = "";
        String line;
        String previousLine = "";
        while ((line = in.readLine()) != null) {
            if (line.equals("."))
                break;
            previousLine = line;
        }
        if (!previousLine.isEmpty()) {
            number = previousLine.split(" ")[0].trim();
        }
        return number;
    }

    public String extractSubjectValue(String inputString) {
        int startIndex = inputString.indexOf("Subject:") + "Subject:".length();
        int endIndex = inputString.indexOf("To:");
        if (endIndex == -1 || endIndex < startIndex) {
            endIndex = inputString.indexOf("In-Reply-To:");
        }
        if (endIndex == -1) {
            endIndex = inputString.length();
        }
        return inputString.substring(startIndex, endIndex).trim().replaceAll("[\n\r]", "");
    }

    /**
     * Extrae la dirección de correo del remitente de un correo.
     *
     * @param inputString El texto del correo.
     * @return La dirección de correo del remitente.
     */
    public String extractEmailValue(String inputString) {
        int startIndex = inputString.indexOf("From:") + "From:".length();
        int endIndex = inputString.indexOf("\n", startIndex);
        String subject = inputString.substring(startIndex, endIndex).trim();
        String[] parts = subject.split("<");
        if (parts.length > 1) {
            return parts[1].replace(">", "").trim();
        }
        return "";
    }

    static protected String getMultiline(BufferedReader in) throws IOException {
        StringBuilder lines = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            if (line.equals("."))
                break;
            if (line.startsWith("+OK"))
                continue;
            if (line.length() > 0 && line.charAt(0) == '.') {
                line = line.substring(1);
            }
            lines.append("\n").append(line);
        }
        return lines.toString();
    }
}
