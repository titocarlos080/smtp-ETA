package bo.com.titodev.Models;
import bo.com.titodev.Services.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

 
public class moduloModel {

    private int id;
    private String codigo_modulo;
    private String nombre;
    private String sigla;
    private String version;
    private String edicion;
    private String modalidad;
    private String fecha_inicio;
    private String fecha_finalizacion;
    private int programa_id;
    private int docente_id;

    private conexionDB conexion;

    public moduloModel() {
        conexion = new conexionDB();
    }

    public moduloModel(int id, String codigo_modulo, String nombre, String sigla, String version, String edicion,
            String modalidad,
            String fecha_inicio, String fecha_finalizacion, int programa_id, int docente_id) {
        this.id = id;
        this.codigo_modulo = codigo_modulo;
        this.nombre = nombre;
        this.sigla = sigla;
        this.version = version;
        this.edicion = edicion;
        this.modalidad = modalidad;
        this.fecha_inicio = fecha_inicio;
        this.fecha_finalizacion = fecha_finalizacion;
        this.programa_id = programa_id;
        this.docente_id = docente_id;
        conexion = new conexionDB();
    }

    // Funciones
    public boolean create() {
        String sql = "INSERT INTO modulo (codigo_modulo, nombre, sigla, version, edicion, modalidad, fecha_inicio, fecha_finalizacion, programa_id, docente_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.codigo_modulo);
            ps.setString(2, this.nombre);
            ps.setString(3, this.sigla);
            ps.setString(4, this.version);
            ps.setString(5, this.edicion);
            ps.setString(6, this.modalidad);
            ps.setString(7, this.fecha_inicio);
            ps.setString(8, this.fecha_finalizacion);
            ps.setInt(9, this.programa_id);
            ps.setInt(10, this.docente_id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la inserción", e);
        }
    }

    public boolean update() {
        String sql = "UPDATE modulo SET codigo_modulo = ?, nombre = ?, sigla = ?, version = ?, edicion = ?, modalidad = ?, fecha_inicio = ?, fecha_finalizacion = ? WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.codigo_modulo);
            ps.setString(2, this.nombre);
            ps.setString(3, this.sigla);
            ps.setString(4, this.version);
            ps.setString(5, this.edicion);
            ps.setString(6, this.modalidad);
            ps.setString(7, this.fecha_inicio);
            ps.setString(8, this.fecha_finalizacion);
            ps.setInt(9, this.id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la actualización", e);
        }
    }

    public boolean delete() {
        String sql = "DELETE FROM modulo WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, this.id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar el borrado", e);
        }
    }

    public boolean exist(int id) {
        String sql = "SELECT * FROM modulo WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
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
        Statement consulta;
        ResultSet resultado = null;
        tabla = "<h1>Lista de modulos</h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "\n"
                + "  <tr>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">CODIGO MODULO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">NOMBRE</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">SIGLA</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">VERSION</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">EDICION</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">MODALIDAD</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">INICIO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">FINALIZACION</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">PROGRAMA</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">DOCENTE</th>\n"
                + "\n";

        try {
            String query;
            if (params.size() == 0) {
                query = "SELECT  modulo.id, modulo.codigo_modulo, modulo.nombre, modulo.sigla, modulo.version, modulo.edicion, modulo.modalidad, modulo.fecha_inicio, modulo.fecha_finalizacion, programa.nombre as programa, CONCAT(docente.nombre, ' ', docente.apellido) AS docente FROM modulo, programa, docente WHERE modulo.programa_id = programa.id AND modulo.docente_id = docente.id";
            } else {
                query = "SELECT modulo.id, modulo.codigo_modulo, modulo.nombre, modulo.sigla, modulo.version, modulo.edicion, modulo.modalidad, modulo.fecha_inicio, modulo.fecha_finalizacion, programa.nombre as programa, CONCAT(docente.nombre, ' ', docente.apellido) AS docente  FROM modulo, programa, docente WHERE modulo.programa_id = programa.id AND modulo.docente_id = docente.id AND "
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

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
