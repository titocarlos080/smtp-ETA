 package bo.com.titodev.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import bo.com.titodev.Services.ConexionDB;

public class pagoModel {
    private int id;
    private double monto;
    private java.sql.Date fecha;
    private String concepto;
    private int inscripcionId;

    public pagoModel() {
    }

    public pagoModel(int id, double monto, java.sql.Date fecha, String concepto, int inscripcionId) {
        this.id = id;
        this.monto = monto;
        this.fecha = fecha;
        this.concepto = concepto;
        this.inscripcionId = inscripcionId;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public java.sql.Date getFecha() {
        return fecha;
    }

    public void setFecha(java.sql.Date fecha) {
        this.fecha = fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getInscripcionId() {
        return inscripcionId;
    }

    public void setInscripcionId(int inscripcionId) {
        this.inscripcionId = inscripcionId;
    }

    // Métodos CRUD
    public boolean create() {
        String sql = "INSERT INTO pagos (monto, fecha, concepto, inscripcion_id) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, monto);
            ps.setDate(2, fecha);
            ps.setString(3, concepto);
            ps.setInt(4, inscripcionId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la inserción", e);
        }
    }

    public boolean delete() {
        String sql = "DELETE FROM pagos WHERE id = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
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
    PreparedStatement ps = null;
    ResultSet resultado = null;
    tabla = "<h1>Lista de pagos</h1>"
            + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
            + "  <tr>\n"
            + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
            + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Monto</th>\n"
            + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Fecha</th>\n"
            + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Concepto</th>\n"
            + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Inscripcion ID</th>\n"
            + "  </tr>\n";

    try {
        String query;
        Connection con = ConexionDB.getInstance().connect();
        if (params.size() == 0) {
            query = "SELECT * FROM pagos";
            ps = con.prepareStatement(query);
        } else {
            query = "SELECT * FROM pagos WHERE " + params.get(0) + " LIKE ?";
            ps = con.prepareStatement(query);
            ps.setString(1, "%" + params.get(1) + "%");
        }

        resultado = ps.executeQuery();
        while (resultado.next()) {
            tabla += "  <tr>\n"
                    + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getInt("id") + "</td>\n"
                    + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getDouble("monto") + "</td>\n"
                    + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getDate("fecha") + "</td>\n"
                    + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("concepto") + "</td>\n"
                    + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getInt("inscripcion_id") + "</td>\n"
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