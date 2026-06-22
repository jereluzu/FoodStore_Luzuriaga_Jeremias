package integrado.prog2.dao;

import java.util.List;

public interface BaseDAO<T> {
    List<T> listarTodos() throws Exception;
    T buscarPorId(Long id) throws Exception;
    void guardar(T entidad) throws Exception;
    void actualizar(T entidad) throws Exception;
    void eliminar(Long id) throws Exception;
}