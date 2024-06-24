 package bo.com.titodev.Dato;

import java.sql.Connection;
import java.sql.PreparedStatement;  
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import bo.com.titodev.Services.ConexionDB;

public class carreraDato {
    private String sigla;
    private String descripcion;
    private int gestion_codigo;

    public carreraDato() {
    }

    public carreraDato(String sigla, String descripcion, int gestion_codigo) {
        this.sigla = sigla;
        this.descripcion = descripcion;
        this.gestion_codigo = gestion_codigo;
    }

    // Getters y setters
    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getGestion_codigo() {
        return gestion_codigo;
    }

    public void setGestion_codigo(int gestion_codigo) {
        this.gestion_codigo = gestion_codigo;
    }

    // Métodos CRUD
    public boolean create() {
        String sql = "INSERT INTO carreras (sigla, descripcion, gestion_codigo) VALUES (?, ?, ?)";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sigla);
            ps.setString(2, descripcion);
            ps.setInt(3, gestion_codigo);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la inserción", e);
        }
    }

    public boolean update() {
        String sql = "UPDATE carreras SET descripcion = ?, gestion_codigo = ? WHERE sigla = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, descripcion);
            ps.setInt(2, gestion_codigo);
            ps.setString(3, sigla);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la actualización", e);
        }
    }

    public boolean delete() {
        String sql = "DELETE FROM carreras WHERE sigla = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sigla);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar el borrado", e);
        }
    }

    public boolean exist(String sigla) {
        String sql = "SELECT * FROM carreras WHERE sigla = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sigla);
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
        tabla = "<h1>Lista de carreras</h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "  <tr>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">SIGLA</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">DESCRIPCIÓN</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">GESTIÓN CÓDIGO</th>\n"
                + "  </tr>\n";

        try {
            String query;
            if (params.size() == 0) {
                query = "SELECT * FROM carreras";
                Connection con = ConexionDB.getInstance().connect();
                ps = con.prepareStatement(query);
            } else {
                query = "SELECT * FROM carreras WHERE " + params.get(0) + " LIKE ?";
                Connection con = ConexionDB.getInstance().connect();
                ps = con.prepareStatement(query);
                ps.setString(1, "%" + params.get(1) + "%");
            }

            resultado = ps.executeQuery();
            while (resultado.next()) {
                tabla += "  <tr>\n"
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("sigla") + "</td>\n"
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("descripcion") + "</td>\n"
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getInt("gestion_codigo") + "</td>\n"
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
