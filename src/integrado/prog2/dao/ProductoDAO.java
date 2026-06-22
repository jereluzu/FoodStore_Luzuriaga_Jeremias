package integrado.prog2.dao;

import integrado.prog2.config.ConexionDB;
import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO implements BaseDAO<Producto> {

    @Override
    public List<Producto> listarTodos() throws Exception {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre AS cat_nombre, c.descripcion AS cat_desc, c.created_at AS cat_created " +
                "FROM productos p " +
                "INNER JOIN categorias c ON p.categoria_id = c.id " +
                "WHERE p.eliminado = false";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categoria cat = new Categoria(
                        rs.getLong("categoria_id"),
                        false,
                        rs.getTimestamp("cat_created").toLocalDateTime(),
                        rs.getString("cat_nombre"),
                        rs.getString("cat_desc")
                );

                Producto prod = new Producto(
                        rs.getLong("id"),
                        rs.getBoolean("eliminado"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"),
                        cat
                );
                lista.add(prod);
            }
        }
        return lista;
    }

    @Override
    public Producto buscarPorId(Long id) throws Exception {
        String sql = "SELECT p.*, c.nombre AS cat_nombre, c.descripcion AS cat_desc, c.created_at AS cat_created " +
                "FROM productos p " +
                "INNER JOIN categorias c ON p.categoria_id = c.id " +
                "WHERE p.id = ? AND p.eliminado = false";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Categoria cat = new Categoria(
                            rs.getLong("categoria_id"),
                            false,
                            rs.getTimestamp("cat_created").toLocalDateTime(),
                            rs.getString("cat_nombre"),
                            rs.getString("cat_desc")
                    );

                    return new Producto(
                            rs.getLong("id"),
                            rs.getBoolean("eliminado"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio"),
                            rs.getInt("stock"),
                            cat
                    );
                }
            }
        }
        return null;
    }

    @Override
    public void guardar(Producto entidad) throws Exception {
        String sql = "INSERT INTO productos (nombre, descripcion, precio, stock, categoria_id, eliminado, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getDescripcion());
            ps.setDouble(3, entidad.getPrecio());
            ps.setInt(4, entidad.getStock());
            ps.setLong(5, entidad.getCategoria().getId());
            ps.setBoolean(6, entidad.isEliminado());
            ps.setTimestamp(7, Timestamp.valueOf(entidad.getCreatedAt()));
            ps.executeUpdate();
        }
    }

    @Override
    public void actualizar(Producto entidad) throws Exception {
        String sql = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, stock = ?, categoria_id = ? WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getDescripcion());
            ps.setDouble(3, entidad.getPrecio());
            ps.setInt(4, entidad.getStock());
            ps.setLong(5, entidad.getCategoria().getId());
            ps.setLong(6, entidad.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        String sql = "UPDATE productos SET eliminado = true WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}
