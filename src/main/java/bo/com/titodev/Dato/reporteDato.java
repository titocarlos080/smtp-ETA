package bo.com.titodev.Dato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import bo.com.titodev.Services.ConexionDB;

public class reporteDato {

    // Reporte de Egresos por Gestión
    public String getEgresosPorGestion() {
        String tabla = "";
        PreparedStatement ps = null;
        ResultSet resultado = null;
        tabla = "<h1>Egresos por Gestión</h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "  <tr>\n"
                + "    <th style=\"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Gestión</th>\n"
                + "    <th style=\"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Total Egresos</th>\n"
                + "  </tr>\n";

        try {
            String query = "SELECT g.descripcion AS gestion, SUM(e.monto) AS total_egresos "
                         + "FROM egresos e "
                         + "JOIN gestiones g ON e.gestion_codigo = g.codigo "
                         + "GROUP BY g.descripcion";
            Connection con = ConexionDB.getInstance().connect();
            ps = con.prepareStatement(query);
            resultado = ps.executeQuery();
            while (resultado.next()) {
                tabla += "  <tr>\n"
                        + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("gestion") + "</td>\n"
                        + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getDouble("total_egresos") + "</td>\n"
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

    // Reporte de Ingresos por Gestión
    public String getIngresosPorGestion() {
        String tabla = "";
        PreparedStatement ps = null;
        ResultSet resultado = null;
        tabla = "<h1>Ingresos por Gestión</h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "  <tr>\n"
                + "    <th style=\"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Gestión</th>\n"
                + "    <th style=\"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Total Ingresos</th>\n"
                + "  </tr>\n";

        try {
            String query = "SELECT g.descripcion AS gestion, SUM(p.monto) AS total_ingresos "
                         + "FROM pagos p "
                         + "JOIN estudiante_materia em ON p.estudiante_materia_id = em.id "
                         + "JOIN estudiantes e ON em.estudiante_ci = e.ci "
                         + "JOIN estudiantes_carrera ec ON e.ci = ec.estudiante_ci "
                         + "JOIN gestiones g ON ec.carrera_sigla = g.codigo "
                         + "GROUP BY g.descripcion";
            Connection con = ConexionDB.getInstance().connect();
            ps = con.prepareStatement(query);
            resultado = ps.executeQuery();
            while (resultado.next()) {
                tabla += "  <tr>\n"
                        + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("gestion") + "</td>\n"
                        + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getDouble("total_ingresos") + "</td>\n"
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

    // Reporte de Estudiantes por Carrera
    public String getEstudiantesPorCarrera() {
        String tabla = "";
        PreparedStatement ps = null;
        ResultSet resultado = null;
        tabla = "<h1>Estudiantes por Carrera</h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "  <tr>\n"
                + "    <th style=\"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Carrera</th>\n"
                + "    <th style=\"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Número de Estudiantes</th>\n"
                + "  </tr>\n";

        try {
            String query = "SELECT c.descripcion AS carrera, COUNT(ec.estudiante_ci) AS num_estudiantes "
                         + "FROM estudiantes_carrera ec "
                         + "JOIN carreras c ON ec.carrera_sigla = c.sigla "
                         + "GROUP BY c.descripcion";
            Connection con = ConexionDB.getInstance().connect();
            ps = con.prepareStatement(query);
            resultado = ps.executeQuery();
            while (resultado.next()) {
                tabla += "  <tr>\n"
                        + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("carrera") + "</td>\n"
                        + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getInt("num_estudiantes") + "</td>\n"
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
