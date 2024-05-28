package bo.com.titodev.Models;
import bo.com.titodev.Services.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

 
public class estudianteModel {

    private int id;
    private String honorifico;
    private String nombre;
    private String apellido;
    private String ci;
    private String ci_expedicion;
    private String telefono;
    private String correo;
    private String carrera;
    private String universidad;
    private String estado;
    private String fecha_inactividad;
    private String sexo;
    private String nacionalidad;

 
    public estudianteModel() {
     }

    public estudianteModel(int id, String honorifico, String nombre, String apellido, String ci, String ci_expedicion,
            String telefono, String correo, String carrera, String universidad, String estado, String fecha_inactividad,
            String sexo, String nacionalidad) {
         this.id = id;
        this.honorifico = honorifico;
        this.nombre = nombre;
        this.apellido = apellido;
        this.ci = ci;
        this.ci_expedicion = ci_expedicion;
        this.telefono = telefono;
        this.correo = correo;
        this.carrera = carrera;
        this.universidad = universidad;
        this.estado = estado;
        this.fecha_inactividad = fecha_inactividad;
        this.sexo = sexo;
        this.nacionalidad = nacionalidad;
    }

    // Funciones
    public boolean create() {
        String sql = "INSERT INTO estudiante (honorifico, nombre, apellido, ci, ci_expedicion, telefono, correo, carrera, universidad, estado, fecha_inactividad, sexo, nacionalidad) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        ConexionDB.getInstance();
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, honorifico);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setString(4, ci);
            ps.setString(5, ci_expedicion);
            ps.setString(6, telefono);
            ps.setString(7, correo);
            ps.setString(8, carrera);
            ps.setString(9, universidad);
            ps.setString(10, estado);
            ps.setString(11, fecha_inactividad);
            ps.setString(12, sexo);
            ps.setString(13, nacionalidad);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la inserción", e);
        }
    }

    public boolean update() {
        String sql = "UPDATE estudiante SET honorifico = ?, nombre = ?, apellido = ?, ci = ?, ci_expedicion = ?, telefono = ?, correo = ?, carrera = ?, universidad = ?, estado = ?, fecha_inactividad = ?, sexo = ?, nacionalidad = ? WHERE id = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, honorifico);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setString(4, ci);
            ps.setString(5, ci_expedicion);
            ps.setString(6, telefono);
            ps.setString(7, correo);
            ps.setString(8, carrera);
            ps.setString(9, universidad);
            ps.setString(10, estado);
            ps.setString(11, fecha_inactividad);
            ps.setString(12, sexo);
            ps.setString(13, nacionalidad);
            ps.setInt(14, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la actualización", e);
        }
    }

    public boolean delete() {
        String sql = "DELETE FROM estudiante WHERE id = ?";
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
        String sql = "SELECT * FROM estudiante WHERE id = ?";
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
        Statement consulta;
        ResultSet resultado = null;
        tabla = "<h1>Lista de estudiantes</h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "\n"
                + "  <tr>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">HONORIFICO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">NOMBRE</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">APELLIDO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">CI</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">CI EXP</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">TELEFONO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">CORREO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">CARRERA</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">UNIVERSIDAD</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ESTADO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">SEXO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">NACIONALIDAD</th>\n"
                + "\n";
        try {
            String query;
            if (params.size() == 0) {
                query = "SELECT id, honorifico, nombre, apellido, ci, ci_expedicion, telefono, correo, carrera, universidad, estado, sexo, nacionalidad FROM estudiante";
            } else {
                query = "SELECT id, honorifico, nombre, apellido, ci, ci_expedicion, telefono, correo, carrera, universidad, estado, sexo, nacionalidad FROM estudiante WHERE "
                        + params.get(0) + " ILIKE '%" + params.get(1) + "%'";
            }

            Connection con = ConexionDB.getInstance().connect();
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
