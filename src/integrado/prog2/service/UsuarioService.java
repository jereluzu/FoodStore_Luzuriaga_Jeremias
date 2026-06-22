package integrado.prog2.service;

import integrado.prog2.dao.UsuarioDAO;
import integrado.prog2.entities.Usuario;
import java.util.List;

public class UsuarioService implements BaseService<Usuario> {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    @Override
    public List<Usuario> listarTodos() throws Exception {
        return usuarioDAO.listarTodos();
    }

    @Override
    public Usuario buscarPorId(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del usuario no es válido.");
        }
        return usuarioDAO.buscarPorId(id);
    }

    @Override
    public void guardar(Usuario entidad) throws Exception {
        if (entidad.getNombre() == null || entidad.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del usuario es obligatorio.");
        }
        if (entidad.getEmail() == null || !entidad.getEmail().contains("@")) {
            throw new IllegalArgumentException("El formato del email no es válido.");
        }
        if (entidad.getPassword() == null || entidad.getPassword().length() < 4) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 4 caracteres.");
        }

        Usuario usuarioExistente = usuarioDAO.buscarPorEmail(entidad.getEmail());
        if (usuarioExistente != null) {
            throw new IllegalArgumentException("Ya existe un usuario registrado con ese email.");
        }

        usuarioDAO.guardar(entidad);
    }

    @Override
    public void actualizar(Usuario entidad) throws Exception {
        if (entidad.getId() == null || entidad.getEmail() == null) {
            throw new IllegalArgumentException("Datos insuficientes para actualizar el usuario.");
        }
        usuarioDAO.actualizar(entidad);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID no válido para eliminar el usuario.");
        }
        usuarioDAO.eliminar(id);
    }

    public Usuario login(String email, String password) throws Exception {
        Usuario user = usuarioDAO.buscarPorEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Email o contraseña incorrectos.");
        }
        return user;
    }
}
