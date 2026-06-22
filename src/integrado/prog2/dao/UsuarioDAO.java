package integrado.prog2.dao;

import integrado.prog2.config.ConexionDB;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Rol;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements BaseDAO<Usuario> {

    @Override
    public List<Usuario> listarTodos() throws Exception {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE eliminado = false";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario user = new Usuario(
                        rs.getLong("id"),
                        rs.getBoolean("eliminado"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("password"),
                        Rol.valueOf(rs.getString("rol"))
                );
                lista.add(user);
            }
        }
        return lista;
    }

    @Override
    public Usuario buscarPorId(Long id) throws Exception {
        String sql = "SELECT * FROM usuarios WHERE id = ? AND eliminado = false";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getLong("id"),
                            rs.getBoolean("eliminado"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getString("password"),
                            Rol.valueOf(rs.getString("rol"))
                    );
                }
            }
        }
        return null;
    }

    @Override
    public void guardar(Usuario entidad) throws Exception {
        String sql = "INSERT INTO usuarios (nombre, email, password, rol, eliminado, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getEmail());
            ps.setString(3, entidad.getPassword());
            ps.setString(4, entidad.getRol().name());
            ps.setBoolean(5, entidad.isEliminado());
            ps.setTimestamp(6, Timestamp.valueOf(entidad.getCreatedAt()));
            ps.executeUpdate();
        }
    }

    @Override
    public void actualizar(Usuario entidad) throws Exception {
        String sql = "UPDATE usuarios SET nombre = ?, email = ?, password = ?, rol = ? WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getEmail());
            ps.setString(3, entidad.getPassword());
            ps.setString(4, entidad.getRol().name());
            ps.setLong(5, entidad.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        String sql = "UPDATE usuarios SET eliminado = true WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    public Usuario buscarPorEmail(String email) throws Exception {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND eliminado = false";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getLong("id"),
                            rs.getBoolean("eliminado"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getString("password"),
                            Rol.valueOf(rs.getString("rol"))
                    );
                }
            }
        }
        return null;
    }
}