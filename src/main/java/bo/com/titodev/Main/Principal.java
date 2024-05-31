 
package bo.com.titodev.Main;

 
import bo.com.titodev.Services.popService;
import bo.com.titodev.Utils.subjectValidator;
 
 
public class Principal {
    
    
    public static void main(String[] args)  {
         
        popService pop = new popService();
        int cantMails = pop.getCantidadEmails();     
         while ( true) {
            int newCantsMails = pop.getCantidadEmails();
            System.out.println("Escuchando EMAILS...");
            if (cantMails != newCantsMails) {
                cantMails = newCantsMails;
                try {
                    System.out.println("********NEW EMAIL*****************");
                    pop.getMail();
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
