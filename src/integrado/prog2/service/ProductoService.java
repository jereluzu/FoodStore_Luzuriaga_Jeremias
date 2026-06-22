package integrado.prog2.service;

import integrado.prog2.dao.ProductoDAO;
import integrado.prog2.entities.Producto;
import java.util.List;

public class ProductoService implements BaseService<Producto> {

    private final ProductoDAO productoDAO;

    public ProductoService() {
        this.productoDAO = new ProductoDAO();
    }

    @Override
    public List<Producto> listarTodos() throws Exception {
        return productoDAO.listarTodos();
    }

    @Override
    public Producto buscarPorId(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del producto no es válido.");
        }
        return productoDAO.buscarPorId(id);
    }

    @Override
    public void guardar(Producto entidad) throws Exception {
        if (entidad.getNombre() == null || entidad.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }
        if (entidad.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio del producto no puede ser negativo.");
        }
        if (entidad.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser un número negativo.");
        }
        if (entidad.getCategoria() == null || entidad.getCategoria().getId() == null) {
            throw new IllegalArgumentException("El producto debe tener una categoría válida asignada.");
        }

        productoDAO.guardar(entidad);
    }

    @Override
    public void actualizar(Producto entidad) throws Exception {
        if (entidad.getId() == null || entidad.getNombre() == null || entidad.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("Datos insuficientes para actualizar el producto.");
        }
        if (entidad.getPrecio() < 0 || entidad.getStock() < 0) {
            throw new IllegalArgumentException("El precio o el stock no pueden ser negativos.");
        }

        productoDAO.actualizar(entidad);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID no válido para eliminar el producto.");
        }
        productoDAO.eliminar(id);
    }
}