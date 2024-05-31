package bo.com.titodev.Models;
import bo.com.titodev.Services.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;


public class usuariosModel {

    private int id;
     private String email;
    private String password;
    private String password_resset;
     private int rol_id;

 
    public usuariosModel() {
     }

    public usuariosModel(int id, String email, String password, String password_resset, int rol_id) {
         this.id = id;
        this.email = email;
        this.password = password;
        this.password_resset = password_resset;
         this.rol_id = rol_id;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public String getpassword_resset() {
        return password_resset;
    }

    public void setpassword_resset(String password_resset) {
        this.password_resset = password_resset;
    }



    public int getRol_id() {
        return rol_id;
    }

    public void setRol_id(int rol_id) {
        this.rol_id = rol_id;
    }



    // Funciones
    public boolean create() {
        String sql = "INSERT INTO usuarios (email , password, password_resset, rol_id) VALUES (?, ?, ?,?)";
        ConexionDB.getInstance();
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, password_resset);
            ps.setInt(4, rol_id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la inserción", e);
        }
    }

    public boolean update() {

        String sql = "UPDATE usuarios SET email = ?, password = ?, password_resset = ?,   rol_id = ? WHERE id = ?";
        ConexionDB.getInstance();
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, password_resset);
             ps.setInt(4, rol_id);
            ps.setInt(5, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la actualización", e);
        }
    }

    public boolean delete() {
        String sql = "DELETE FROM usuarios WHERE id = ?";
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
        String sql = "SELECT * FROM usuarios WHERE id = ?";
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

    public boolean emailExist(String email) {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet resultado = ps.executeQuery()) {
                return resultado.next(); // Devuelve true si hay un registro, false si no
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validateRol(String email, String rol) {
        String sql = "SELECT * FROM usuarios, roles WHERE roles.id = usuarios.rol_id AND usuarios.email = ? AND rol.nombre = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, password);
            ps.setString(2, rol);
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
        tabla = "<h1>Lista de usuarioss</h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "\n"
                + "  <tr>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">email</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">password</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ROL</th>\n"
                + "\n";

        try {
            String query;
            if (params.size() == 0) {
                query = "SELECT usuarios.id, usuarios.email, usuarios.password, roles.nombre as rol FROM usuarios, roles WHERE usuarios.rol_id = roles.id";
            } else {
                query = "SELECT usuarios.id, usuarios.email, usuarios.password, usuarios.area, rol.email as rol FROM usuarios, rol WHERE usuarios.rol_id = roles.id AND "
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

}
