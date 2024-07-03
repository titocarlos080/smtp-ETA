package bo.com.titodev.Dato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import bo.com.titodev.Services.ConexionDB;

public class EstudianteCarreraDato {
    private int id;
    private String fechaInscripcion;
    private String estudianteCi;
    private String carreraSigla;

    public EstudianteCarreraDato() {
    }

    public EstudianteCarreraDato(int id, String fechaInscripcion, String estudianteCi, String carreraSigla) {
        this.id = id;
        this.fechaInscripcion = fechaInscripcion;
        this.estudianteCi = estudianteCi;
        this.carreraSigla = carreraSigla;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(String fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getEstudianteCi() {
        return estudianteCi;
    }

    public void setEstudianteCi(String estudianteCi) {
        this.estudianteCi = estudianteCi;
    }

    public String getCarreraSigla() {
        return carreraSigla;
    }

    public void setCarreraSigla(String carreraSigla) {
        this.carreraSigla = carreraSigla;
    }

    // Métodos CRUD
    public boolean create() {
        String sql = "INSERT INTO estudiantes_carrera (fecha_inscripcion, estudiante_ci, carrera_sigla) VALUES (?, ?, ?)";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, fechaInscripcion);
            ps.setString(2, estudianteCi);
            ps.setString(3, carreraSigla);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la inserción", e);
        }
    }

    public boolean update() {
        String sql = "UPDATE estudiantes_carrera SET fecha_inscripcion = ?, estudiante_ci = ?, carrera_sigla = ? WHERE id = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, fechaInscripcion);
            ps.setString(2, estudianteCi);
            ps.setString(3, carreraSigla);
            ps.setInt(4, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la actualización", e);
        }
    }

    public boolean delete() {
        String sql = "DELETE FROM estudiantes_carrera WHERE id = ?";
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
        String sql = "SELECT * FROM estudiantes_carrera WHERE id = ?";
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
        tabla = "<h1>Lista de estudiantes_carrera</h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "  <tr>\n"
                + "    <th style=\"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
                + "    <th style=\"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Fecha Inscripción</th>\n"
                + "    <th style=\"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Estudiante CI</th>\n"
                + "    <th style=\"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Carrera Sigla</th>\n"
                + "  </tr>\n";

        try {
            String query;
            if (params.size() == 0) {
                query = "SELECT * FROM estudiantes_carrera";
                Connection con = ConexionDB.getInstance().connect();
                ps = con.prepareStatement(query);
            } else {
                query = "SELECT * FROM estudiantes_carrera WHERE " + params.get(0) + " LIKE ?";
                Connection con = ConexionDB.getInstance().connect();
                ps = con.prepareStatement(query);
                ps.setString(1, "%" + params.get(1) + "%");
            }

            resultado = ps.executeQuery();
            while (resultado.next()) {
                tabla += "  <tr>\n"
                        + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getInt("id") + "</td>\n"
                        + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("fecha_inscripcion") + "</td>\n"
                        + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("estudiante_ci") + "</td>\n"
                        + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("carrera_sigla") + "</td>\n"
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
