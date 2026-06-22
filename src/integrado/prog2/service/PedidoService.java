package integrado.prog2.service;

import integrado.prog2.dao.PedidoDAO;
import integrado.prog2.entities.Pedido;
import integrado.prog2.enums.EstadoPedido;
import integrado.prog2.enums.FormaPago;

import java.util.List;

public class PedidoService {
    private final PedidoDAO pedidoDAO;

    public PedidoService() {
        this.pedidoDAO = new PedidoDAO();
    }

    public void guardar(Pedido pedido) throws Exception {
        if (pedido.getUsuario() == null) {
            throw new IllegalArgumentException("El pedido debe tener un usuario asignado obligatoriamente.");
        }
        if (pedido.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("No se puede registrar un pedido sin productos cargados.");
        }
        pedido.calcularTotal();
        pedidoDAO.guardarPedidoConDetalles(pedido);
    }

    public List<Pedido> listarTodos() throws Exception {
        return pedidoDAO.listarTodos();
    }

    public void actualizarEstadoYPago(Long id, EstadoPedido estado, FormaPago pago) throws Exception {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del pedido no es válido.");
        }
        pedidoDAO.actualizarEstadoYPago(id, estado, pago);
    }

    public void eliminar(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID no es válido para dar de baja.");
        }
        pedidoDAO.eliminarLogico(id);
    }
}
