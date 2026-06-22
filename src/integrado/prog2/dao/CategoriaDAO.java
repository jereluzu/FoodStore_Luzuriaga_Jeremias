package integrado.prog2.dao;

import integrado.prog2.config.ConexionDB;
import integrado.prog2.entities.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO implements BaseDAO<Categoria> {

    @Override
    public List<Categoria> listarTodos() throws Exception {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categorias WHERE eliminado = false";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categoria cat = new Categoria(
                        rs.getLong("id"),
                        rs.getBoolean("eliminado"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
                lista.add(cat);
            }
        }
        return lista;
    }

    @Override
    public Categoria buscarPorId(Long id) throws Exception {
        String sql = "SELECT * FROM categorias WHERE id = ? AND eliminado = false";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Categoria(
                            rs.getLong("id"),
                            rs.getBoolean("eliminado"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getString("nombre"),
                            rs.getString("descripcion")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public void guardar(Categoria entidad) throws Exception {
        String sql = "INSERT INTO categorias (nombre, descripcion, eliminado, created_at) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getDescripcion());
            ps.setBoolean(3, entidad.isEliminado());
            ps.setTimestamp(4, Timestamp.valueOf(entidad.getCreatedAt()));
            ps.executeUpdate();
        }
    }

    @Override
    public void actualizar(Categoria entidad) throws Exception {
        String sql = "UPDATE categorias SET nombre = ?, descripcion = ? WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getDescripcion());
            ps.setLong(3, entidad.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        String sql = "UPDATE categorias SET eliminado = true WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
    public Categoria buscarPorNombre(String nombre) throws Exception {
        String sql = "SELECT * FROM categorias WHERE nombre = ? AND eliminado = false";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Categoria(
                            rs.getLong("id"),
                            rs.getBoolean("eliminado"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getString("nombre"),
                            rs.getString("descripcion")
                    );
                }
            }
        }
        return null;
    }
}