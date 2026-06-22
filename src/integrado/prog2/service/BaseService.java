package integrado.prog2.service;

import java.util.List;

public interface BaseService<T> {
    List<T> listarTodos() throws Exception;
    T buscarPorId(Long id) throws Exception;
    void guardar(T entidad) throws Exception;
    void actualizar(T entidad) throws Exception;
    void eliminar(Long id) throws Exception;
}
