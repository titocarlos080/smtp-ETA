package bo.com.titodev.Dato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import bo.com.titodev.Services.ConexionDB;

public class notaDato {
    private int id;
    private int estudianteMateriaId;
    private double notaFinal;

    public notaDato(int id, int estudianteMateriaId, double notaFinal) {
        this.id = id;
        this.estudianteMateriaId = estudianteMateriaId;
        this.notaFinal = notaFinal;
    }
    public notaDato() {
     }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstudianteMateriaId() {
        return estudianteMateriaId;
    }

    public void setEstudianteMateriaId(int estudianteMateriaId) {
        this.estudianteMateriaId = estudianteMateriaId;
    }

    public double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(double notaFinal) {
        this.notaFinal = notaFinal;
    }
    // Métodos CRUD
    public boolean create() {
        String sql = "INSERT INTO notas (estudiante_materia_id, nota_final) VALUES ( ?, ?)";
        try (Connection con = ConexionDB.getInstance().connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, estudianteMateriaId);
            ps.setDouble(2, notaFinal);
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
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">Materia Sigla</th>\n"
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
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">"
                        + resultado.getInt("id") + "</td>\n"
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">"
                        + resultado.getInt("estudiante_materia_id") + "</td>\n"
                        + "    <td style = \"text-align: left; padding: 8px; border: 1px solid black;\">"
                        + resultado.getDouble("nota_final") + "</td>\n"
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
