package bo.com.titodev.Dato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import bo.com.titodev.Services.ConexionDB;

public class diaDato { 
    private int id;
    private String nombre;

    public diaDato() {
    }

    public diaDato(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos CRUD
    public boolean create() {
        String sql = "INSERT INTO dias (nombre) VALUES (?)";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la inserción", e);
        }
    }

    public boolean update() {
        String sql = "UPDATE dias SET nombre = ? WHERE id = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la actualización", e);
        }
    }

    public boolean delete() {
        String sql = "DELETE FROM dias WHERE id = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar el borrado", e);
        }
    }

    public boolean exist(int id) {
        String sql = "SELECT * FROM dias WHERE id = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet resultado = ps.executeQuery()) {
                return resultado.next(); // Devuelve true si hay un registro, false si no
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getAll(LinkedList<String> params) {
        String tabla = "";
        PreparedStatement ps = null;
        ResultSet resultado = null;
        tabla = "<h1>Lista de días</h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "  <tr>\n"
                + "    <th style=\"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
                + "    <th style=\"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">NOMBRE</th>\n"
                + "  </tr>\n";

        try {
            String query;
            if (params.size() == 0) {
                query = "SELECT * FROM dias";
                Connection con = ConexionDB.getInstance().connect();
                ps = con.prepareStatement(query);
            } else {
                query = "SELECT * FROM dias WHERE " + params.get(0) + " LIKE ?";
                Connection con = ConexionDB.getInstance().connect();
                ps = con.prepareStatement(query);
                ps.setString(1, "%" + params.get(1) + "%");
            }
 
            resultado = ps.executeQuery();
            while (resultado.next()) {
                tabla += "  <tr>\n"
                        + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getInt("id") + "</td>\n"
                        + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("nombre") + "</td>\n"
                        + "  </tr>\n";
            }
            tabla += "</table>";
            ps.close();
            resultado.close();
        } catch (SQLException e) {
            tabla = "No se pudieron listar los datos.";
        }
        return tabla;
    }
}
