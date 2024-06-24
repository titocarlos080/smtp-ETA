package bo.com.titodev.Dato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import bo.com.titodev.Services.ConexionDB;
 
  
public class rolPermisoDato {
    private int rol_id;
    private int permiso_id;

 
    public rolPermisoDato() {
     }

    public rolPermisoDato(int rol_id, int permiso_id ) {
         this.rol_id = rol_id;
        this.permiso_id = permiso_id;
    
    }
    // Getters y setters
  
    public int getRol_id() {
        return rol_id;
    }

    public void setRol_id(int rol_id) {
        this.rol_id = rol_id;
    }

    public int getPermiso_id() {
        return permiso_id;
    }

    public void setPermiso_id(int permiso_id) {
        this.permiso_id = permiso_id;
    }


    public boolean create() {
        String sql = "INSERT INTO roles_permisos (rol_id,permiso_id) VALUES ( ?,?)";
        ConexionDB.getInstance();
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, rol_id);
            ps.setInt(2, permiso_id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la inserción", e);
        }
    }


    public boolean delete() {
        String sql = "DELETE FROM roles_permisos WHERE rol_id = ? and permiso_id= ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, rol_id);
            ps.setInt(2, permiso_id);
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
        tabla = "<h1>Lista de Roles Permisos </h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "\n"
                + "  <tr>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ROL ID</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">PERMISO ID</th>\n"
               + "\n";

        try {
            String query;
            if (params.size() == 0)
                query = "SELECT id, nombre FROM roles_permisos";
            else
                query = "SELECT id, nombre FROM roles_permisos WHERE " + params.get(0) + " LIKE '%" + params.get(1)
                        + "%'";

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
