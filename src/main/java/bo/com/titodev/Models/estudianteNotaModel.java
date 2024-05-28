package bo.com.titodev.Models;
import bo.com.titodev.Services.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

 
public class estudianteNotaModel {

    private int id;
    private String nota;
    private String detalles;
    private int estudiante_id;
    private int programa_id;
    private int modulo_id;
    private conexionDB conexion;

    public estudianteNotaModel() {
        this.conexion = new conexionDB();
    }

    public estudianteNotaModel(int id, String nota, String detalles, int estudiante_id, int programa_id,
            int modulo_id) {
        this.id = id;
        this.nota = nota;
        this.detalles = detalles;
        this.estudiante_id = estudiante_id;
        this.programa_id = programa_id;
        this.modulo_id = modulo_id;
        this.conexion = new conexionDB();
    }

    // Funciones
    public boolean create() {
        String sql = "INSERT INTO estudiante_nota (nota, detalles, estudiante_id, programa_id, modulo_id) VALUES (?,?,?,?,?)";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nota);
            ps.setString(2, detalles);
            ps.setInt(3, estudiante_id);
            ps.setInt(4, programa_id);
            ps.setInt(5, modulo_id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la inserción", e);
        }
    }

    public boolean update() {
        String sql = "UPDATE estudiante_nota SET nota = ?, detalles = ?, estudiante_id = ?, programa_id = ?, modulo_id = ? WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nota);
            ps.setString(2, detalles);
            ps.setInt(3, estudiante_id);
            ps.setInt(4, programa_id);
            ps.setInt(5, modulo_id);
            ps.setInt(6, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la actualización", e);
        }
    }

    public boolean delete() {
        String sql = "DELETE FROM estudiante_nota WHERE id = ?";
        try (Connection con = conexion.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la eliminación", e);
        }
    }

    public String getAll(LinkedList<String> params) {
        String tabla = "";
        Statement consulta;
        ResultSet resultado = null;
        tabla = "<h1>Lista de notas</h1>"
                + "<table style=\"border-collapse: collapse; width: 100%; border: 1px solid black;\">\n"
                + "\n"
                + "  <tr>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ID</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">ESTUDIANTE</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">PROGRAMA</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">MODULO</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">NOTA</th>\n"
                + "\n"
                + "    <th style = \"text-align: left; padding: 8px; background-color: #3c4f76; color: white; border: 1px solid black;\">DETALLES</th>\n";
        try {
            String query;
            if (params.size() == 0) {
                query = "SELECT estudiante_nota.id, CONCAT(estudiante.nombre, ' ', estudiante.apellido) AS estudiante, programa.nombre AS programa, modulo.nombre AS modulo, estudiante_nota.nota, estudiante_nota.detalles FROM estudiante_nota INNER JOIN estudiante ON estudiante_nota.estudiante_id = estudiante.id INNER JOIN programa ON estudiante_nota.programa_id = programa.id INNER JOIN modulo ON estudiante_nota.modulo_id = modulo.id";
            } else {
                query = "SELECT estudiante_nota.id, CONCAT(estudiante.nombre, ' ', estudiante.apellido) AS estudiante, programa.nombre AS programa, modulo.nombre AS modulo, estudiante_nota.nota, estudiante_nota.detalles FROM estudiante_nota INNER JOIN estudiante ON estudiante_nota.estudiante_id = estudiante.id INNER JOIN programa ON estudiante_nota.programa_id = programa.id INNER JOIN modulo ON estudiante_nota.modulo_id = modulo.id WHERE "
                        + params.get(0) + " ILIKE '%" + params.get(1) + "%'";
            }

            Connection con = conexion.connect();
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
