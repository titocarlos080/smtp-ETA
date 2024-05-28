package bo.com.titodev.Models;
import bo.com.titodev.Services.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

 
public class calendarioAcademicoModel {

    private int id;
    private String nombre;
    private String fecha_inicio;
    private String fecha_finalizacion;
    private String tipo;
    private String tipo_fecha;
    private String lugar;
    private String hora;
    private String encargado;
    private conexionDB conexion;

    public calendarioAcademicoModel() {
        conexion = new conexionDB();
    }

    public calendarioAcademicoModel(int id, String nombre, String fecha_inicio, String fecha_finalizacion, String tipo,
            String tipo_fecha, String lugar, String hora, String encargado) {
        this.id = id;
        this.nombre = nombre;
        this.fecha_inicio = fecha_inicio;
        this.fecha_finalizacion = fecha_finalizacion;
        this.tipo = tipo;
        this.tipo_fecha = tipo_fecha;
        this.lugar = lugar;
        this.hora = hora;
        this.encargado = encargado;
        conexion = new conexionDB();
    }

    // Funciones
    public boolean create() {
        String sql = "INSERT INTO calendario_academico (nombre, fecha_inicio, fecha_finalizacion, tipo, tipo_fecha, lugar, hora, encargado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.nombre);
            ps.setString(2, this.fecha_inicio);
            ps.setString(3, this.fecha_finalizacion);
            ps.setString(4, this.tipo);
            ps.setString(5, this.tipo_fecha);
            ps.setString(6, this.lugar);
            ps.setString(7, this.hora);
            ps.setString(8, this.encargado);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la inserción", e);
        }
        return false;
    }

    public boolean update() {
        String sql = "UPDATE calendario_academico SET nombre = ?, fecha_inicio = ?, fecha_finalizacion = ?, tipo = ?, tipo_fecha = ?, lugar = ?, hora = ?, encargado = ? WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.nombre);
            ps.setString(2, this.fecha_inicio);
            ps.setString(3, this.fecha_finalizacion);
            ps.setString(4, this.tipo);
            ps.setString(5, this.tipo_fecha);
            ps.setString(6, this.lugar);
            ps.setString(7, this.hora);
            ps.setString(8, this.encargado);
            ps.setInt(9, this.id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la actualización", e);
        }
        return false;
    }

    public boolean delete() {
        String sql = "DELETE FROM calendario_academico WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, this.id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la eliminación", e);
        }
        return false;
    }

    public boolean exist(int id) {
        String sql = "SELECT * FROM calendario_academico WHERE id = ?";
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
        tabla = "<h1>Lista de eventos academicos</h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "\n"
                + "  <tr>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">NOMBRE</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">INICIO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">FINALIZACION</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">TIPO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">TIPO FECHA</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">LUGAR</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">HORA</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ENCARGADO</th>\n"
                + "\n";

        try {
            String query;
            if (params.size() == 0) {
                query = "SELECT id, nombre, fecha_inicio, fecha_finalizacion, tipo, tipo_fecha, lugar, hora, encargado FROM calendario_academico";
            } else {
                query = "SELECT id, nombre, fecha_inicio, fecha_finalizacion, tipo, tipo_fecha, lugar, hora, encargado FROM calendario_academico WHERE "
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
