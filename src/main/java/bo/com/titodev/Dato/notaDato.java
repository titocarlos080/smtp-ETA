package bo.com.titodev.Dato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import bo.com.titodev.Services.ConexionDB;

public class notaDato {
    private int id;
    private int estudianteId;
    private int docenteId;
    private String grupoSigla;
    private double notaFinal;

    public notaDato() {
    }

    public notaDato(int id, int estudianteId, int docenteId, String grupoSigla, double notaFinal) {
        this.id = id;
        this.estudianteId = estudianteId;
        this.docenteId = docenteId;
        this.grupoSigla = grupoSigla;
        this.notaFinal = notaFinal;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(int estudianteId) {
        this.estudianteId = estudianteId;
    }

    public int getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(int docenteId) {
        this.docenteId = docenteId;
    }

    public String getGrupoSigla() {
        return grupoSigla;
    }

    public void setGrupoSigla(String grupoSigla) {
        this.grupoSigla = grupoSigla;
    }

    public double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(double notaFinal) {
        this.notaFinal = notaFinal;
    }

    // Métodos CRUD
    public boolean create() {
        String sql = "INSERT INTO notas (estudiante_id, docente_id, grupo_sigla, nota_final) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, estudianteId);
            ps.setInt(2, docenteId);
            ps.setString(3, grupoSigla);
            ps.setDouble(4, notaFinal);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la inserción", e);
        }
    }

    public boolean update() {
        String sql = "UPDATE notas SET nota_final = ? WHERE id = ?";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, notaFinal);
            ps.setInt(2, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la actualización", e);
        }
    }

    public boolean delete() {
        String sql = "DELETE FROM notas WHERE id = ?";
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
        String sql = "SELECT * FROM notas WHERE id = ?";
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
    PreparedStatement ps = null;
    ResultSet resultado = null;
    tabla = "<h1>Lista de notas</h1>"
            + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
            + "  <tr>\n"
            + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
            + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Estudiante ID</th>\n"
            + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Docente ID</th>\n"
            + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Grupo Sigla</th>\n"
            + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Nota Final</th>\n"
            + "  </tr>\n";

    try {
        String query;
        Connection con = ConexionDB.getInstance().connect();
        if (params.size() == 0) {
            query = "SELECT * FROM notas";
            ps = con.prepareStatement(query);
        } else {
            query = "SELECT * FROM notas WHERE " + params.get(0) + " LIKE ?";
            ps = con.prepareStatement(query);
            ps.setString(1, "%" + params.get(1) + "%");
        }

        resultado = ps.executeQuery();
        while (resultado.next()) {
            tabla += "  <tr>\n"
                    + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getInt("id") + "</td>\n"
                    + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getInt("estudiante_id") + "</td>\n"
                    + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getInt("docente_id") + "</td>\n"
                    + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getString("grupo_sigla") + "</td>\n"
                    + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">" + resultado.getDouble("nota_final") + "</td>\n"
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
