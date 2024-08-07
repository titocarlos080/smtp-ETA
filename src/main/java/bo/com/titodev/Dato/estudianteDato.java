package bo.com.titodev.Dato;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import bo.com.titodev.Services.ConexionDB;

public class estudianteDato {
    private String ci;
    private String nombre;
    private String apellido_pat;
    private String apellido_mat;
    private String telefono;
    private char sexo;
    private Date fecha_nacimiento;
    private int usuario_id;
    private String email;

    public estudianteDato() {
    }

    public estudianteDato(String ci, String nombre, String apellido_pat, String apellido_mat, String telefono,
            char sexo, Date fecha_nacimiento, int usuario_id, String email) {
        this.ci = ci;
        this.nombre = nombre;
        this.apellido_pat = apellido_pat;
        this.apellido_mat = apellido_mat;
        this.telefono = telefono;
        this.sexo = sexo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.usuario_id = usuario_id;
        this.email = email;
    }

    // Getters y setters
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    } 

    // Métodos CRUD
    public boolean create() {
    String sql = "{call public.insertar_estudiante(?, ?, ?, ?, ?, ?, ?, ?)}"; // Definir la llamada a la función almacenada
    try (Connection con = ConexionDB.getInstance().connect(); 
         CallableStatement cs = con.prepareCall(sql)) {
        
        // Establecer los parámetros de la función almacenada
        cs.setString(1, ci);
        cs.setString(2, nombre);
        cs.setString(3, apellido_pat);
        cs.setString(4, apellido_mat);
        cs.setString(5, email);
        cs.setString(6, telefono);
        cs.setString(7, String.valueOf(sexo));
        cs.setDate(8, fecha_nacimiento);

        // Ejecutar la función almacenada
        cs.execute();

        // La función almacenada no devuelve un valor, solo realiza la inserción
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Error al ejecutar la inserción", e);
    }
}


    public boolean update() {
        String sql = "UPDATE estudiantes SET nombre = ?, apellido_pat = ?, apellido_mat = ?, telefono = ?, sexo = ?, fecha_nacimiento = ?, usuario_id = ?, email = ? WHERE ci = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, apellido_pat);
            ps.setString(3, apellido_mat);
            ps.setString(4, telefono);
            ps.setString(5, String.valueOf(sexo));
            ps.setDate(6, fecha_nacimiento);
            ps.setInt(7, usuario_id);
            ps.setString(8, email);
            ps.setString(9, ci);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la actualización", e);
        }
    }

    public boolean delete() {
        String sql = "DELETE FROM estudiantes WHERE ci = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ci);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar el borrado", e);
        }
    }

    public boolean exist(String ci) {
        String sql = "SELECT * FROM estudiantes WHERE ci = ?";
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
    String tabla = "";
    PreparedStatement ps = null;
    ResultSet resultado = null;
    tabla = "<h1>Lista de Estudiantes</h1>"
            + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
            + "  <tr>\n"
            + "    <th style=\"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">CI</th>\n"
            + "    <th style=\"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Nombre</th>\n"
            + "    <th style=\"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Apellido Paterno</th>\n"
            + "    <th style=\"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Apellido Materno</th>\n"
            + "    <th style=\"text-align: left; padding: 8px; border: 1px solid black;\">Teléfono</th>\n"
            + "    <th style=\"text-align: left; padding: 8px; border: 1px solid black;\">Sexo</th>\n"
            + "    <th style=\"text-align: left; padding: 8px; border: 1px solid black;\">Fecha de Nacimiento</th>\n"
            + "    <th style=\"text-align: left; padding: 8px; border: 1px solid black;\">Usuario ID</th>\n"
            + "    <th style=\"text-align: left; padding: 8px; border: 1px solid black;\">Email</th>\n"
            + "  </tr>\n";

    try {
        String query;
        if (params.size() == 0) {
            query = "SELECT * FROM estudiantes";
        } else {
            query = "SELECT * FROM estudiantes WHERE " + params.get(0) + " LIKE ?";
        }

        Connection con = ConexionDB.getInstance().connect();
        ps = con.prepareStatement(query);

        if (params.size() > 0) {
            ps.setString(1, "%" + params.get(1) + "%");
        }

        resultado = ps.executeQuery();
        while (resultado.next()) {
            tabla += "  <tr>\n"
                    + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("ci") + "</td>\n"
                    + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("nombre") + "</td>\n"
                    + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("apellido_pat") + "</td>\n"
                    + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("apellido_mat") + "</td>\n"
                    + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("telefono") + "</td>\n"
                    + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("sexo") + "</td>\n"
                    + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getDate("fecha_nacimiento") + "</td>\n"
                    + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getInt("usuario_id") + "</td>\n"
                    + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("email") + "</td>\n"
                    + "  </tr>\n";
        }
        tabla += "</table>";
        ps.close();
        return tabla;
    } catch (Exception e) {
        e.printStackTrace();
        return "No se pudieron listar los estudiantes";
    }
}

    public String getById(String ci) {
        PreparedStatement ps = null;
        ResultSet resultado = null;
        String tabla = "<h1>Detalles del Estudiante</h1>";
        tabla += "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "  <tr>\n"
                + "    <th style=\"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">CI</th>\n"
                + "    <th style=\"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Nombre</th>\n"
                + "    <th style=\"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Apellido Paterno</th>\n"
                + "    <th style=\"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Apellido Materno</th>\n"
                + "    <th style=\"text-align: left; padding: 8px; border: 1px solid black;\">Teléfono</th>\n"
                + "    <th style=\"text-align: left; padding: 8px; border: 1px solid black;\">Sexo</th>\n"
                + "    <th style=\"text-align: left; padding: 8px; border: 1px solid black;\">Fecha de Nacimiento</th>\n"
                + "    <th style=\"text-align: left; padding: 8px; border: 1px solid black;\">Usuario ID</th>\n"
                + "    <th style=\"text-align: left; padding: 8px; border: 1px solid black;\">Email</th>\n"
                + "  </tr>\n";
    
        try {
            String query = "SELECT * FROM estudiantes WHERE ci = ?";
            Connection con = ConexionDB.getInstance().connect();
            ps = con.prepareStatement(query);
            ps.setString(1, ci);
            resultado = ps.executeQuery();
    
            while (resultado.next()) {
                tabla += "  <tr>\n"
                        + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("ci") + "</td>\n"
                        + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("nombre") + "</td>\n"
                        + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("apellido_pat") + "</td>\n"
                        + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("apellido_mat") + "</td>\n"
                        + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("telefono") + "</td>\n"
                        + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("sexo") + "</td>\n"
                        + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getDate("fecha_nacimiento") + "</td>\n"
                        + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getInt("usuario_id") + "</td>\n"
                        + "    <td style=\"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("email") + "</td>\n"
                        + "  </tr>\n";
            }
            tabla += "</table>";
            ps.close();
            return tabla;
        } catch (Exception e) {
            e.printStackTrace();
            return "No se pudo encontrar el estudiante con CI: " + ci;
        }
    }
    
}
