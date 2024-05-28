package bo.com.titodev.Models;
import bo.com.titodev.Services.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

 
public class contratoModel {
    private int id;
    private String honorario;
    private String fecha_inicio;
    private String fecha_finalizacion;
    private String horario;
    private String pagado;
    private String nro_preventiva;
    private String estado;
    private int modulo_id;
    private conexionDB conexion;

    public contratoModel() {
        conexion = new conexionDB();
    }

    public contratoModel(int id, String honorario, String fecha_inicio, String fecha_finalizacion, String horario,
            String pagado, String nro_preventiva, String estado, int modulo_id) {
        this.id = id;
        this.honorario = honorario;
        this.fecha_inicio = fecha_inicio;
        this.fecha_finalizacion = fecha_finalizacion;
        this.horario = horario;
        this.pagado = pagado;
        this.nro_preventiva = nro_preventiva;
        this.estado = estado;
        this.modulo_id = modulo_id;
        conexion = new conexionDB();
    }

    // Funciones
    public boolean create() {
        String sql = "INSERT INTO contrato (honorario, fecha_inicio, fecha_finalizacion, horario, pagado, nro_preventiva, estado, modulo_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.honorario);
            ps.setString(2, this.fecha_inicio);
            ps.setString(3, this.fecha_finalizacion);
            ps.setString(4, this.horario);
            ps.setString(5, this.pagado);
            ps.setString(6, this.nro_preventiva);
            ps.setString(7, this.estado);
            ps.setInt(8, this.modulo_id);
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
        String sql = "UPDATE contrato SET honorario = ?, fecha_inicio = ?, fecha_finalizacion = ?, horario = ?, pagado = ?, nro_preventiva = ?, estado = ? WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.honorario);
            ps.setString(2, this.fecha_inicio);
            ps.setString(3, this.fecha_finalizacion);
            ps.setString(4, this.horario);
            ps.setString(5, this.pagado);
            ps.setString(6, this.nro_preventiva);
            ps.setString(7, this.estado);
            ps.setInt(8, this.id);
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
        String sql = "DELETE FROM contrato WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar el borrado", e);
        }
    }

    public boolean exist(int id) {
        String sql = "SELECT * FROM contrato WHERE id = ?";
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
        tabla = "<h1>Lista de contratos</h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "\n"
                + "  <tr>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">HONORARIO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">INICIO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">FINALIZACION</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">HORARIO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">PAGADO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">NRO PREVENTIVA</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ESTADO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">MODULO</th>\n"
                + "\n";

        try {
            String query;
            if (params.size() == 0)
                query = "SELECT contrato.id as id, contrato.honorario, contrato.fecha_inicio, contrato.fecha_finalizacion, contrato.horario, contrato.pagado, contrato.nro_preventiva, contrato.estado, modulo.nombre as modulo FROM contrato, modulo WHERE contrato.modulo_id = modulo.id";
            else
                query = "SELECT contrato.id as id, contrato.honorario, contrato.fecha_inicio, contrato.fecha_finalizacion, contrato.horario, contrato.pagado, contrato.nro_preventiva, contrato.estado, modulo.nombre as modulo FROM contrato, modulo WHERE contrato.modulo_id = modulo.id AND "
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

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
