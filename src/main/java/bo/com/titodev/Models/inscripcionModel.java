package bo.com.titodev.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import bo.com.titodev.Services.ConexionDB;

public class inscripcionModel {
    private int id;
    private int estudianteId;
    private String grupoSigla;
    private java.sql.Date fecha;

    public inscripcionModel() {
    }

    public inscripcionModel(int id, int estudianteId, String grupoSigla, java.sql.Date fecha) {
        this.id = id;
        this.estudianteId = estudianteId;
        this.grupoSigla = grupoSigla;
        this.fecha = fecha;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(int estudianteId) {
        this.estudianteId = estudianteId;
    }

    public String getGrupoSigla() {
        return grupoSigla;
    }

    public void setGrupoSigla(String grupoSigla) {
        this.grupoSigla = grupoSigla;
    }

    public java.sql.Date getFecha() {
        return fecha;
    }

    public void setFecha(java.sql.Date fecha) {
        this.fecha = fecha;
    }

    // Métodos CRUD
    public boolean create() {
        String sql = "INSERT INTO inscripciones (estudiante_id, grupo_sigla, fecha) VALUES (?, ?, ?)";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, estudianteId);
            ps.setString(2, grupoSigla);
            ps.setDate(3, fecha);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la inserción", e);
        }
    }

    public boolean delete() {
        String sql = "DELETE FROM inscripciones WHERE id = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar el borrado", e);
        }
    }

    public String getAll(LinkedList<String> params) {
        String tabla = "";
        PreparedStatement ps = null;
        ResultSet resultado = null;
        tabla = "<h1>Lista de inscripciones</h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "  <tr>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Estudiante ID</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Grupo Sigla</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Fecha</th>\n"
                + "  </tr>\n";

        try {
            String query;
            Connection con = ConexionDB.getInstance().connect();
            if (params.size() == 0) {
                query = "SELECT * FROM inscripciones";
                ps = con.prepareStatement(query);
            } else {
                query = "SELECT * FROM inscripciones WHERE " + params.get(0) + " LIKE ?";
                ps = con.prepareStatement(query);
                ps.setString(1, "%" + params.get(1) + "%");
            }

            resultado = ps.executeQuery();
            while (resultado.next()) {
                tabla += "  <tr>\n"
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getInt("id") + "</td>\n"
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getInt("estudiante_id") + "</td>\n"
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("grupo_sigla") + "</td>\n"
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getDate("fecha") + "</td>\n"
                        + "  </tr>\n";
            }
            tabla += "</table>";
        } catch (SQLException e) {
            tabla = "No se pudieron listar los datos.";
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (resultado != null) {
                    resultado.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tabla;
    }
}
