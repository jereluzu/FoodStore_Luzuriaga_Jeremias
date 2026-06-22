package integrado.prog2.entities;

import integrado.prog2.interfaces.Calculable;
import integrado.prog2.enums.EstadoPedido;
import integrado.prog2.enums.FormaPago;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Calculable {
    private Long id;
    private boolean eliminado;
    private LocalDateTime createdAt;
    private EstadoPedido estado;
    private FormaPago formaPago;
    private double total;
    private Usuario usuario;
    private List<DetallePedido> detalles;

    public Pedido(Usuario usuario, FormaPago formaPago) {
        this.usuario = usuario;
        this.formaPago = formaPago;
        this.estado = EstadoPedido.PENDIENTE;
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
        this.detalles = new ArrayList<>();
        this.total = 0.0;
    }

    public void addDetallePedido(DetallePedido detalle) {
        if (detalle != null) {
            this.detalles.add(detalle);
        }
    }

    @Override
    public double calcularTotal() {
        this.total = 0.0;
        for (DetallePedido de : detalles) {
            this.total += de.getSubtotal();
        }
        return this.total;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public EstadoPedido getEstado() { return estado; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }
    public FormaPago getFormaPago() { return formaPago; }
    public void setFormaPago(FormaPago formaPago) { this.formaPago = formaPago; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public Usuario getUsuario() { return usuario; }
    public List<DetallePedido> getDetalles() { return detalles; }
}