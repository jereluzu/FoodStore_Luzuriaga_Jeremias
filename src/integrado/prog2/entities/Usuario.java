package integrado.prog2.entities;

import integrado.prog2.enums.Rol;
import java.time.LocalDateTime;

public class Usuario extends Base {
    private String nombre;
    private String email;
    private String password;
    private Rol rol; // Relación con el Enum Rol

    public Usuario() {
        super();
    }

    public Usuario(String nombre, String email, String password, Rol rol) {
        super();
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public Usuario(Long id, boolean eliminado, LocalDateTime createdAt, String nombre, String email, String password, Rol rol) {
        super(id, eliminado, createdAt);
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    @Override
    public String toString() {
        return String.format("ID: %d | Usuario: %s | Email: %s | Rol: %s", getId(), nombre, email, rol);
    }
}
