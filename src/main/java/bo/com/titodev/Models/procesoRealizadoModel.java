package bo.com.titodev.Models;
import bo.com.titodev.Services.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

 
public class procesoRealizadoModel {

    private int id;
    private String fecha;
    private int proceso_id;
    private int modulo_id;

    private conexionDB conexion;

    public procesoRealizadoModel() {
        conexion = new conexionDB();
    }

    public procesoRealizadoModel(int id, String fecha, int proceso_id, int modulo_id) {
        this.id = id;
        this.fecha = fecha;
        this.proceso_id = proceso_id;
        this.modulo_id = modulo_id;
        conexion = new conexionDB();
    }

    // Funciones
    public boolean create() {
        String sql = "INSERT INTO proceso_realizado (fecha, proceso_modulo_id, modulo_id) VALUES (?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.fecha);
            ps.setInt(2, this.proceso_id);
            ps.setInt(3, this.modulo_id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la inserción", e);
        }
    }

    public boolean update() {
        String sql = "UPDATE proceso_realizado SET fecha = ?, proceso_modulo_id = ?, modulo_id = ? WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.fecha);
            ps.setInt(2, this.proceso_id);
            ps.setInt(3, this.modulo_id);
            ps.setInt(4, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la actualización", e);
        }
    }

    public boolean delete() {
        String sql = "DELETE FROM proceso_realizado WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
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
        Statement consulta;
        ResultSet resultado = null;
        tabla = "<h1>Lista de procesos de modulos realizados</h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "\n"
                + "  <tr>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">FECHA</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">PROCESO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ORDEN</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">MODULO</th>\n"
                + "\n";
        try {
            String query;
            if (params.size() == 0)
                query = "SELECT proceso_realizado.id, proceso_realizado.fecha, proceso_modulo.nombre, proceso_modulo.orden, modulo.nombre FROM proceso_realizado INNER JOIN proceso_modulo ON proceso_realizado.proceso_modulo_id = proceso_modulo.id INNER JOIN modulo ON proceso_realizado.modulo_id = modulo.id";
            else
                query = "SELECT proceso_realizado.id, proceso_realizado.fecha, proceso_modulo.nombre, proceso_modulo.orden, modulo.nombre FROM proceso_realizado INNER JOIN proceso_modulo ON proceso_realizado.proceso_modulo_id = proceso_modulo.id INNER JOIN modulo ON proceso_realizado.modulo_id = modulo.id WHERE "
                        + params.get(0) + " ILIKE '%" + params.get(1) + "%'";

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
