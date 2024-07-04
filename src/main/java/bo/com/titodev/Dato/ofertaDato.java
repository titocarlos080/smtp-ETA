package bo.com.titodev.Dato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import bo.com.titodev.Services.ConexionDB;

public class ofertaDato {

    // Atributos de la ofertaDato
    private String materia; // Descripción de la materia
    private String grupo; // Descripción del grupo
    private String horaInicio; // Hora de inicio del horario
    private String horaFin; // Hora de fin del horario
    private String profesor; // Nombre del profesor
    private String gestion; // Descripción de la gestión

    // Constructor vacío
    public ofertaDato() {
    }

    // Constructor con parámetros
    public ofertaDato(String materia, String grupo, String horaInicio, String horaFin, String profesor, String gestion) {
        this.materia = materia;
        this.grupo = grupo;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.profesor = profesor;
        this.gestion = gestion;
    }

    // Getters y setters
    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getGestion() {
        return gestion;
    }

    public void setGestion(String gestion) {
        this.gestion = gestion;
    }

    // Método para obtener todos los datos de la vista ofertas_view
    public String getAll(LinkedList<String> params) {
        String tabla = "";
        PreparedStatement ps = null;
        ResultSet resultado = null;
        tabla = "<h1>Lista de Ofertas</h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "  <tr>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Materia</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Grupo</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Hora Inicio</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Hora Fin</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Profesor</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Gestión</th>\n"
                + "  </tr>\n";

        try {
            String query;
            Connection con = ConexionDB.getInstance().connect();
            if (params.size() == 0) {
                query = "SELECT * FROM ofertas_view";
                ps = con.prepareStatement(query);
            } else {
                query = "SELECT * FROM ofertas_view WHERE " + params.get(0) + " LIKE ?";
                ps = con.prepareStatement(query);
                ps.setString(1, "%" + params.get(1) + "%");
            }

            resultado = ps.executeQuery();
            while (resultado.next()) {
                tabla += "  <tr>\n"
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("Materia") + "</td>\n"
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("Grupo") + "</td>\n"
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("Hora Inicio") + "</td>\n"
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("Hora Fin") + "</td>\n"
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("Profesor") + "</td>\n"
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("Gestión") + "</td>\n"
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
