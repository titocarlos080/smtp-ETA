package bo.com.titodev.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private final String DB = "db_grupo05sc";
    private final String USER = "grupo05sc";
    private final String PASSWORD = "grup005grup005";
    private final String URL = "jdbc:postgresql://localhost:5432/" + DB;
    private Connection conn;
    private static ConexionDB instance;

    private ConexionDB() {
    }

    public static ConexionDB getInstance() {
        if (instance == null) {
            synchronized (ConexionDB.class) {
                if (instance == null) {
                    instance = new ConexionDB();
                }
            }
        }
        return instance;
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
            System.out.println("Conectando a la DB...");
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(this.URL, this.USER, this.PASSWORD);
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Error al conectar: " + ex);
        }
        return conn;
    }
}
