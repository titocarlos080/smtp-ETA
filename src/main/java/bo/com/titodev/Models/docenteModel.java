package bo.com.titodev.Models;
import bo.com.titodev.Services.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

 
public class docenteModel {

    private int id;
    private String honorifico;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String ci;
    private String ci_expedicion;
    private String facturacion;
 
    public docenteModel() {
     }

    public docenteModel(int id, String honorifico, String nombre, String apellido, String correo, String telefono,
            String ci, String ci_expedicion, String facturacion) {
        this.id = id;
        this.honorifico = honorifico;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.ci = ci;
        this.ci_expedicion = ci_expedicion;
        this.facturacion = facturacion;
     }

    // Funciones
    public boolean create() {
        String sql = "INSERT INTO docente (honorifico, nombre, apellido, correo, telefono, ci, ci_expedicion, facturacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        ConexionDB.getInstance();
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.honorifico);
            ps.setString(2, this.nombre);
            ps.setString(3, this.apellido);
            ps.setString(4, this.correo);
            ps.setString(5, this.telefono);
            ps.setString(6, this.ci);
            ps.setString(7, this.ci_expedicion);
            ps.setString(8, this.facturacion);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean update() {
        String sql = "UPDATE docente SET honorifico = ?, nombre = ?, apellido = ?, correo = ?, telefono = ?, ci = ?, ci_expedicion = ?, facturacion = ? WHERE id = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, this.honorifico);
            ps.setString(2, this.nombre);
            ps.setString(3, this.apellido);
            ps.setString(4, this.correo);
            ps.setString(5, this.telefono);
            ps.setString(6, this.ci);
            ps.setString(7, this.ci_expedicion);
            ps.setString(8, this.facturacion);
            ps.setInt(9, this.id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean delete() {
        String sql = "DELETE FROM docente WHERE id = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, this.id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean exist(int id) {
        String sql = "SELECT * FROM docente WHERE id = ?";
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
        tabla = "<h1>Lista de docentes</h1>"
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
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">CORREO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">CI</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">CI EXPEDICION</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">TELEFONO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">FACTURACION</th>\n"
                + "\n";

        try {
            String query;
            if (params.size() == 0)
                query = "SELECT id, honorifico, nombre, apellido, correo, ci, ci_expedicion, telefono, facturacion FROM docente";
            else
                query = "SELECT id, honorifico, nombre, apellido, correo, ci, ci_expedicion, telefono, facturacion FROM docente WHERE "
                        + params.get(0) + " ILIKE '%" + params.get(1) + "%'";

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

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
