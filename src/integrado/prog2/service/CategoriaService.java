package integrado.prog2.service;

import integrado.prog2.dao.CategoriaDAO;
import integrado.prog2.entities.Categoria;
import java.util.List;

public class CategoriaService implements BaseService<Categoria> {

    private final CategoriaDAO categoriaDAO;

    public CategoriaService() {
        this.categoriaDAO = new CategoriaDAO();
    }

    @Override
    public List<Categoria> listarTodos() throws Exception {
        return categoriaDAO.listarTodos();
    }

    @Override
    public Categoria buscarPorId(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID de la categoría no es válido.");
        }
        return categoriaDAO.buscarPorId(id);
    }

    @Override
    public void guardar(Categoria entidad) throws Exception {
        if (entidad.getNombre() == null || entidad.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío.");
        }

        Categoria existente = categoriaDAO.buscarPorNombre(entidad.getNombre());
        if (existente != null) {
            throw new IllegalArgumentException("Ya existe una categoría con el nombre '" + entidad.getNombre() + "'.");
        }

        categoriaDAO.guardar(entidad);
    }

    @Override
    public void actualizar(Categoria entidad) throws Exception {
        if (entidad.getId() == null || entidad.getNombre() == null || entidad.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("Datos insuficientes para actualizar la categoría.");
        }
        categoriaDAO.actualizar(entidad);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID no válido para eliminar.");
        }
        categoriaDAO.eliminar(id);
    }
}