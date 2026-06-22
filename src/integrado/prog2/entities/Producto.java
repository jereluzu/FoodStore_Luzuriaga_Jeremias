package integrado.prog2.entities;

import java.time.LocalDateTime;

public class Producto extends Base {
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private Categoria categoria; // Relación con la clase Categoria

    public Producto() {
        super();
    }

    public Producto(String nombre, String descripcion, double precio, int stock, Categoria categoria) {
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    public Producto(Long id, boolean eliminado, LocalDateTime createdAt, String nombre, String descripcion, double precio, int stock, Categoria categoria) {
        super(id, eliminado, createdAt);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        String catNombre = (categoria != null) ? categoria.getNombre() : "Sin Categoría";
        return String.format("ID: %d | Producto: %s | Precio: $%.2f | Stock: %d | Cat: %s", getId(), nombre, precio, stock, catNombre);
    }
}
