package integrado.prog2.entities;

import java.time.LocalDateTime;

public class DetallePedido {
    private Long id;
    private boolean eliminado;
    private LocalDateTime createdAt;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
    private Producto producto;

    public DetallePedido(Long id, boolean eliminado, LocalDateTime createdAt, int cantidad, double precioUnitario, double subtotal, Producto producto) {
        this.id = id;
        this.eliminado = eliminado;
        this.createdAt = createdAt;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.producto = producto;
    }

    public DetallePedido(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
        this.precioUnitario = producto.getPrecio();
        this.subtotal = cantidad * producto.getPrecio();
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getSubtotal() { return subtotal; }
    public Producto getProducto() { return producto; }
}
