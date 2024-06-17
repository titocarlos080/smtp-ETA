package bo.com.titodev.Models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import bo.com.titodev.Services.ConexionDB;

public class gestionModel {
    private int codigo;
    private String descripcion;
    private Date fecha_inicio;
    private Date fecha_fin;

    public gestionModel() {
    }

    public gestionModel(int codigo, String descripcion, Date fecha_inicio, Date fecha_fin) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }

    // Getters y setters
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    // Métodos CRUD
    public boolean create() {
        String sql = "INSERT INTO gestiones (descripcion, fecha_inicio, fecha_fin) VALUES (?, ?, ?)";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, descripcion);
            ps.setDate(2, fecha_inicio);
            ps.setDate(3, fecha_fin);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la inserción", e);
        }
    }

    public boolean update() {
        String sql = "UPDATE gestiones SET descripcion = ?, fecha_inicio = ?, fecha_fin = ? WHERE codigo = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, descripcion);
            ps.setDate(2, fecha_inicio);
            ps.setDate(3, fecha_fin);
            ps.setInt(4, codigo);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la actualización", e);
        }
    }

    public boolean delete() {
        String sql = "DELETE FROM gestiones WHERE codigo = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar el borrado", e);
        }
    }

    public boolean exist(int codigo) {
        String sql = "SELECT * FROM gestiones WHERE codigo = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigo);
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
        tabla = "<h1>Lista de gestiones</h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "  <tr>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">CÓDIGO</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">DESCRIPCIÓN</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">FECHA INICIO</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">FECHA FIN</th>\n"
                + "  </tr>\n";

        try {
            String query;
            if (params.size() == 0) {
                query = "SELECT * FROM gestiones";
                Connection con = ConexionDB.getInstance().connect();
                ps = con.prepareStatement(query);
            } else {
                query = "SELECT * FROM gestiones WHERE " + params.get(0) + " LIKE ?";
                Connection con = ConexionDB.getInstance().connect();
                ps = con.prepareStatement(query);
                ps.setString(1, "%" + params.get(1) + "%");
            }

            resultado = ps.executeQuery();
            while (resultado.next()) {
                tabla += "  <tr>\n"
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getInt("codigo") + "</td>\n"
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("descripcion") + "</td>\n"
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getDate("fecha_inicio") + "</td>\n"
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getDate("fecha_fin") + "</td>\n"
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
