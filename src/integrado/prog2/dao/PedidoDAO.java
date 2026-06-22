package integrado.prog2.dao;

import integrado.prog2.config.ConexionDB;
import integrado.prog2.entities.DetallePedido;
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.EstadoPedido;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.enums.Rol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public void guardarPedidoConDetalles(Pedido pedido) throws Exception {
        String sqlPedido = "INSERT INTO pedidos (usuario_id, estado, forma_pago, total, eliminado, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalles_pedido (pedido_id, producto_id, cantidad, precio_unitario, subtotal, eliminado, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement psPedido = null;
        PreparedStatement psDetalle = null;

        try {
            con = ConexionDB.getConexion();
            con.setAutoCommit(false);

            psPedido = con.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            psPedido.setLong(1, pedido.getUsuario().getId());
            psPedido.setString(2, pedido.getEstado().name());
            psPedido.setString(3, pedido.getFormaPago().name());
            psPedido.setDouble(4, pedido.getTotal());
            psPedido.setBoolean(5, pedido.isEliminado());
            psPedido.setTimestamp(6, Timestamp.valueOf(pedido.getCreatedAt()));
            psPedido.executeUpdate();

            ResultSet rsKeys = psPedido.getGeneratedKeys();
            if (rsKeys.next()) {
                pedido.setId(rsKeys.getLong(1));
            }

            psDetalle = con.prepareStatement(sqlDetalle);
            for (DetallePedido detalle : pedido.getDetalles()) {
                psDetalle.setLong(1, pedido.getId());
                psDetalle.setLong(2, detalle.getProducto().getId());
                psDetalle.setInt(3, detalle.getCantidad());
                psDetalle.setDouble(4, detalle.getPrecioUnitario());
                psDetalle.setDouble(5, detalle.getSubtotal());
                psDetalle.setBoolean(6, detalle.isEliminado());
                psDetalle.setTimestamp(7, Timestamp.valueOf(detalle.getCreatedAt()));
                psDetalle.addBatch();
            }
            psDetalle.executeBatch();
            con.commit();
        } catch (Exception e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) con.setAutoCommit(true);
            if (psPedido != null) psPedido.close();
            if (psDetalle != null) psDetalle.close();
        }
    }

    public List<Pedido> listarTodos() throws Exception {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT p.*, u.nombre as usr_nombre, u.email as usr_email " +
                "FROM pedidos p " +
                "LEFT JOIN usuarios u ON p.usuario_id = u.id " +
                "WHERE p.eliminado = 0";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario usr = new Usuario(
                        rs.getLong("usuario_id"),
                        false,
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getString("usr_nombre"),
                        rs.getString("usr_email"),
                        "",
                        Rol.USUARIO
                );

                Pedido ped = new Pedido(usr, FormaPago.valueOf(rs.getString("forma_pago")));
                ped.setId(rs.getLong("id"));
                ped.setEstado(EstadoPedido.valueOf(rs.getString("estado")));
                ped.setTotal(rs.getDouble("total"));
                ped.setEliminado(rs.getBoolean("eliminado"));

                lista.add(ped);
            }
        }
        return lista;
    }

    public void actualizarEstadoYPago(Long id, EstadoPedido estado, FormaPago pago) throws Exception {
        String sql = "UPDATE pedidos SET estado = ?, forma_pago = ? WHERE id = ? AND eliminado = false";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado.name());
            ps.setString(2, pago.name());
            ps.setLong(3, id);
            ps.executeUpdate();
        }
    }

    public void eliminarLogico(Long id) throws Exception {
        String sql = "UPDATE pedidos SET eliminado = true WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}