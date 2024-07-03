package bo.com.titodev.Dato;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import bo.com.titodev.Services.ConexionDB;

public class docenteDato {
    private String ci;
    private String nombre;
    private String apellido_pat;
    private String apellido_mat;
    private String kardex;
    private String currilculum;
    private String email; // Añadido campo 'email'
    private Integer usuario_id; // Cambio de int a Integer para permitir valores nulos

    public docenteDato(String ci, String nombre, String apellido_pat, String apellido_mat, String kardex,
            String currilculum, String email, Integer usuario_id) {
        this.ci = ci;
        this.nombre = nombre;
        this.apellido_pat = apellido_pat;
        this.apellido_mat = apellido_mat;
        this.kardex = kardex;
        this.currilculum = currilculum;
        this.email = email;
        this.usuario_id = usuario_id;
    }

    // Constructores
    public docenteDato() {
    }

    // Getters y Setters
    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_pat() {
        return apellido_pat;
    }

    public void setApellido_pat(String apellido_pat) {
        this.apellido_pat = apellido_pat;
    }

    public String getApellido_mat() {
        return apellido_mat;
    }

    public void setApellido_mat(String apellido_mat) {
        this.apellido_mat = apellido_mat;
    }

    public String getKardex() {
        return kardex;
    }

    public void setKardex(String kardex) {
        this.kardex = kardex;
    }

    public String getCurrilculum() {
        return currilculum;
    }

    public void setCurrilculum(String currilculum) {
        this.currilculum = currilculum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }

    // CRUD
       public boolean create() {
        String sql = "{call insertar_docente(?, ?, ?, ?, ?, ?, ?)}";
        try (Connection con = ConexionDB.getInstance().connect();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setString(1, this.ci);
            cs.setString(2, this.nombre);
            cs.setString(3, this.apellido_pat);
            cs.setString(4, this.apellido_mat);
            cs.setString(5, this.email);
            cs.setString(6, this.kardex);
            cs.setString(7, this.currilculum);
            int result = cs.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Eliminar un docente por CI
public boolean delete(String ci) {
    String sql = "DELETE FROM docentes WHERE ci = ?";
    try (Connection con = ConexionDB.getInstance().connect();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, ci);
        int result = ps.executeUpdate();
        return result > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
// Actualizar un docente por CI
public boolean update(String ci) {
    String sql = "UPDATE docentes SET nombre = ?, apellido_pat = ?, apellido_mat = ?, email = ?, kardex = ?, currilculum = ? WHERE ci = ?";
    try (Connection con = ConexionDB.getInstance().connect();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, this.nombre);
        ps.setString(2, this.apellido_pat);
        ps.setString(3, this.apellido_mat);
        ps.setString(4, this.email);
        ps.setString(5, this.kardex);
        ps.setString(6, this.currilculum);
        ps.setString(7, ci);
        int result = ps.executeUpdate();
        return result > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    public boolean exist(String ci) {
        String sql = "SELECT * FROM docentes WHERE ci = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ci);
            try (ResultSet resultado = ps.executeQuery()) {
                return resultado.next(); // Devuelve true si hay un registro, false si no
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getAll(LinkedList<String> params) {
        String tabla = "<h1>Lista de docentes</h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "  <tr>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">CI</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">NOMBRE</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">APELLIDO PAT</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">APELLIDO MAT</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; border: 1px solid black;\">KARDEX</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; border: 1px solid black;\">CURRICULUM</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; border: 1px solid black;\">EMAIL</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; border: 1px solid black;\">USUARIO ID</th>\n"
                + "  </tr>\n";

        try {
            String query;
            if (params.size() == 0) {
                query = "SELECT * FROM docentes";
            } else {
                query = "SELECT * FROM docentes WHERE " + params.get(0) + " LIKE ?";
            }

            try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(query)) {
                if (params.size() > 0) {
                    ps.setString(1, "%" + params.get(1) + "%");
                }

                try (ResultSet resultado = ps.executeQuery()) {
                    while (resultado.next()) {
                        tabla += "  <tr>\n"
                                + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("ci") + "</td>\n"
                                + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("nombre") + "</td>\n"
                                + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("apellido_pat") + "</td>\n"
                                + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("apellido_mat") + "</td>\n"
                                + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("kardex") + "</td>\n"
                                + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("currilculum") + "</td>\n"
                                + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("email") + "</td>\n"
                                + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getInt("usuario_id") + "</td>\n"
                                + "  </tr>\n";
                    }
                }
            }

            tabla += "</table>";
            return tabla;
        } catch (Exception e) {
            e.printStackTrace();
            return "No se pudieron listar los docentes";
        }
    }

    public String getById(String ci) {
        String tabla = "<h1>Detalles del docente</h1>";
        tabla += "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "  <tr>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">CI</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">NOMBRE</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">APELLIDO PAT</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">APELLIDO MAT</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; border: 1px solid black;\">KARDEX</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; border: 1px solid black;\">CURRICULUM</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; border: 1px solid black;\">EMAIL</th>\n"
                + "    <th style = \"text-align: left; padding: 8px; border: 1px solid black;\">USUARIO ID</th>\n"
                + "  </tr>\n";

        String query = "SELECT * FROM docentes WHERE ci = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, ci);
            try (ResultSet resultado = ps.executeQuery()) {
                if (resultado.next()) {
                    tabla += "  <tr>\n"
                            + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("ci") + "</td>\n"
                            + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("nombre") + "</td>\n"
                            + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("apellido_pat") + "</td>\n"
                            + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("apellido_mat") + "</td>\n"
                            + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("kardex") + "</td>\n"
                            + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("currilculum") + "</td>\n"
                            + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("email") + "</td>\n"
                            + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getInt("usuario_id") + "</td>\n"
                            + "  </tr>\n";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "No se pudo obtener el docente";
        }
        tabla += "</table>";
        return tabla;
    }
}
