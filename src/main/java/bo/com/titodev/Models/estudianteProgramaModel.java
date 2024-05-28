package bo.com.titodev.Models;
import bo.com.titodev.Services.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

 
public class estudianteProgramaModel {

    private int id;
    private String fecha_inscripcion;
    private int estudiante_id;
    private int programa_id;
    private conexionDB conexion;

    public estudianteProgramaModel() {
        conexion = new conexionDB();
    }

    public estudianteProgramaModel(int id, String fecha_inscripcion, int estudiante_id, int programa_id) {
        this.id = id;
        this.fecha_inscripcion = fecha_inscripcion;
        this.estudiante_id = estudiante_id;
        this.programa_id = programa_id;
        conexion = new conexionDB();
    }

    // Funciones
    public boolean create() {
        String sql = "INSERT INTO estudiante_programa (fecha_inscripcion, estudiante_id, programa_id) VALUES (?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.fecha_inscripcion);
            ps.setInt(2, this.estudiante_id);
            ps.setInt(3, this.programa_id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la inserción", e);
        }
    }

    public boolean update() {
        String sql = "UPDATE estudiante_programa SET fecha_inscripcion = ?, estudiante_id = ?, programa_id = ? WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.fecha_inscripcion);
            ps.setInt(2, this.estudiante_id);
            ps.setInt(3, this.programa_id);
            ps.setInt(4, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la actualización", e);
        }
    }

    public boolean delete() {
        String sql = "DELETE FROM estudiante_programa WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la eliminación", e);
        }
    }

    public String getAll(LinkedList<String> params) {
        String tabla = "";
        Statement consulta;
        ResultSet resultado = null;
        tabla = "<h1>Lista de estudiantes inscritos en programas</h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "\n"
                + "  <tr>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ESTUDIANTE</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">PROGRAMA</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">FECHA INSCRIPCION</th>\n"
                + "\n";
        try {
            String query;
            if (params.size() == 0) {
                query = "SELECT estudiante_programa.id, CONCAT(estudiante.nombre, ' ', estudiante.apellido) AS estudiante, programa.nombre, estudiante_programa.fecha_inscripcion FROM estudiante_programa INNER JOIN estudiante ON estudiante_programa.estudiante_id = estudiante.id INNER JOIN programa ON estudiante_programa.programa_id = programa.id";
            } else {
                query = "SELECT estudiante_programa.id, CONCAT(estudiante.nombre, ' ', estudiante.apellido) AS estudiante, programa.nombre, estudiante_programa.fecha_inscripcion FROM estudiante_programa INNER JOIN estudiante ON estudiante_programa.estudiante_id = estudiante.id INNER JOIN programa ON estudiante_programa.programa_id = programa.id WHERE "
                        + params.get(0) + " ILIKE '%" + params.get(1) + "%'";
            }

            Connection con = conexion.connect();
            consulta = con.createStatement();
            resultado = consulta.executeQuery(query);
            ResultSetMetaData rsmd = resultado.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();
            while (resultado.next()) {
                tabla = tabla
                        + "  <tr>\n"
                        + "\n";
                for (int i = 0; i < cantidadColumnas; i++) {
                    tabla = tabla
                            + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">"
                            + resultado.getString(i + 1) + "</td>\n"
                            + "\n";
                }
                tabla = tabla
                        + "  </tr>\n"
                        + "\n";
            }
            tabla = tabla
                    + "\n"
                    + "</table>";
            consulta.close();
            con.close();
        } catch (SQLException e) {
            tabla = "No se pudieron listar los datos.";
        }
        return tabla;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
