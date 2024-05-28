 
package bo.com.titodev.Services;

import java.sql.*;

 
public class conexionDB {

    private final String DB = "db_grupo05sc";
    private final String USER = "grupo05sc";
    private final String PASSWORD = "grup005grup005";
    private final String URL = "jdbc:postgresql://tecnoweb.org.bo:5432/" + DB;
    private Connection conn;

    // private Statement statement;
    public conexionDB() {
    }

    public Connection getConexion() {
        return conn;
    }

    public void close() {
        try {
            conn.close();
            System.out.println("Cerrando conexion");
        } catch (SQLException ex) {
            System.out.println("No se pudo cerrar la conexion");
        }
    }

    public Connection connect() {
        try {
            System.out.println("conectando a la DB...");
            Class.forName("org.postgresql.Driver");
            conn = (Connection) DriverManager.getConnection(this.URL, this.USER, this.PASSWORD);
        } catch (SQLException ex) {
            System.out.println("Error al conectar" + ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al conectar" + ex);
        }
        return conn;
    }
}