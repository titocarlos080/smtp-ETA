package bo.com.titodev.Models;
import bo.com.titodev.Services.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

 
public class programaModel {

    private int id;
    private String codigo_programa;
    private String nombre;
    private String sigla;
    private String edicion;
    private String version;
    private String fecha_inicio;
    private String fecha_finalizacion;
    private String costo;
    private String tipo;
    private String modalidad;
    private String hrs_academicas;
    private conexionDB conexion;

    public programaModel() {
        conexion = new conexionDB();
    }

    public programaModel(int id, String codigo_programa, String nombre, String sigla, String edicion, String version,
            String fecha_inicio, String fecha_finalizacion, String costo, String tipo, String modalidad,
            String hrs_academicas) {
        this.id = id;
        this.codigo_programa = codigo_programa;
        this.nombre = nombre;
        this.sigla = sigla;
        this.edicion = edicion;
        this.version = version;
        this.fecha_inicio = fecha_inicio;
        this.fecha_finalizacion = fecha_finalizacion;
        this.costo = costo;
        this.tipo = tipo;
        this.modalidad = modalidad;
        this.hrs_academicas = hrs_academicas;
        conexion = new conexionDB();
    }

    // Funciones
    public boolean create() {
        String sql = "INSERT INTO programa (codigo_programa, nombre, sigla, edicion, version, fecha_inicio, fecha_finalizacion, costo, tipo, modalidad, hrs_academicas) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.codigo_programa);
            ps.setString(2, this.nombre);
            ps.setString(3, this.sigla);
            ps.setString(4, this.edicion);
            ps.setString(5, this.version);
            ps.setString(6, this.fecha_inicio);
            ps.setString(7, this.fecha_finalizacion);
            ps.setString(8, this.costo);
            ps.setString(9, this.tipo);
            ps.setString(10, this.modalidad);
            ps.setString(11, this.hrs_academicas);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la inserción", e);
        }
    }

    public boolean update() {
        String sql = "UPDATE programa SET codigo_programa = ?, nombre = ?, sigla = ?, edicion = ?, version = ?, fecha_inicio = ?, fecha_finalizacion = ?, costo = ?, tipo = ?, modalidad = ?, hrs_academicas = ? WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.codigo_programa);
            ps.setString(2, this.nombre);
            ps.setString(3, this.sigla);
            ps.setString(4, this.edicion);
            ps.setString(5, this.version);
            ps.setString(6, this.fecha_inicio);
            ps.setString(7, this.fecha_finalizacion);
            ps.setString(8, this.costo);
            ps.setString(9, this.tipo);
            ps.setString(10, this.modalidad);
            ps.setString(11, this.hrs_academicas);
            ps.setInt(12, this.id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la actualización", e);
        }
    }

    public boolean delete() {
        String sql = "DELETE FROM programa WHERE id = ?";
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
        String sql = "SELECT * FROM programa WHERE id = ?";
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
        tabla = "<h1>Lista de programas</h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "\n"
                + "  <tr>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">CODIGO PROGRAMA</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">NOMBRE</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">SIGLA</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">EDICION</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">VERSION</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">INICIO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">FINALIZACION</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">COSTO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">TIPO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">MODALIDAD</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">HRS ACADEMICAS</th>\n"
                + "\n";

        try {
            String query;
            if (params.size() == 0) {
                query = "SELECT id, codigo_programa, nombre, sigla, edicion, version, fecha_inicio, fecha_finalizacion, costo, tipo, modalidad, hrs_academicas FROM programa";
            } else {
                query = "SELECT id, codigo_programa, nombre, sigla, edicion, version, fecha_inicio, fecha_finalizacion, costo, tipo, modalidad, hrs_academicas FROM programa WHERE "
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

    public String getOferta(LinkedList<String> params) {
        String tabla = "";
        Statement consulta;
        ResultSet resultado = null;
        tabla = "<h1>Lista de programas</h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "\n"
                + "  <tr>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">CODIGO PROGRAMA</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">NOMBRE</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">SIGLA</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">EDICION</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">VERSION</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">INICIO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">FINALIZACION</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">COSTO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">TIPO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">MODALIDAD</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">HRS ACADEMICAS</th>\n"
                + "\n";

        try {
            String query;
            if (params.size() == 0) {
                query = "SELECT id, codigo_programa, nombre, sigla, edicion, version, fecha_inicio, fecha_finalizacion, costo, tipo, modalidad, hrs_academicas FROM programa WHERE fecha_finalizacion::date >= CURRENT_DATE";
            } else {
                query = "SELECT id, codigo_programa, nombre, sigla, edicion, version, fecha_inicio, fecha_finalizacion, costo, tipo, modalidad, hrs_academicas FROM programa WHERE fecha_finalizacion::date >= CURRENT_DATE AND "
                        + params.get(0) + " LIKE '%" + params.get(1) + "%'";
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

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
