
package Modelo.DAO;

/**
 *
 * @author Enrique
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Modelo.Entidades.*;

public class CategoriaDAO {


    private Connection conn;

    //Constructora que obtiene la conexion a la base de datos
    public CategoriaDAO(Connection conn) {
        this.conn = conn;
    }

    public int insertarCategoria(Categoria categoria) {
        String sql = "INSERT INTO categoria (categorias, descripcion) VALUES (?, ?) RETURNING id_cat";
        try (
             PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getCategorias());
            stmt.setString(2, categoria.getDescripcion());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_cat"); // Devuelve el id generado automáticamente
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String obtenerCategoriaPorId(int idCat,Categoria categorias) {
        String sql = "SELECT categorias FROM restaurante.categoria WHERE id_cat = ?";
        try (
             PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, idCat);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                 categorias.setCategorias(rs.getString("categorias"));
            }
            return categorias.getCategorias();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Categoria> obtenerTodasLasCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria";
        try (
             Statement stmt = this.conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                categorias.add(new Categoria(
                        rs.getInt("id_cat"),
                        rs.getString("categorias"),
                        rs.getString("descripcion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }

    public boolean actualizarCategoria(Categoria categoria) {
        String sql = "UPDATE categoria SET categorias = ?, descripcion = ? WHERE id_cat = ?";
        try (
             PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getCategorias());
            stmt.setString(2, categoria.getDescripcion());
            stmt.setInt(3, categoria.getIdCat());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarCategoria(int idCat) {
        String sql = "DELETE FROM categoria WHERE id_cat = ?";
        try (
             PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, idCat);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

