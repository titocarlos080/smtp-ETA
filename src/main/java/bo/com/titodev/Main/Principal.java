package bo.com.titodev.Main;

import bo.com.titodev.Services.popService;

public class Principal {

    public static void main(String[] args) {
        popService popService = new popService();
        
        int newCantMails = popService.getCantidadEmails();
        while (true) {
            System.out.println("Escuchando EMAILS...");

            if (newCantMails != popService.getCantidadEmails()) {
                try {
                    System.out.println("********NEW EMAIL*****************");
                    newCantMails++;
                    popService.getMail();
                    System.out.println("*********************************");
                } catch (Exception e) {
                    System.out.println("Error al obtener emails");
                }
            } 

            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                System.out.println("Error en el servidor.");
            }
        }
    }
}
