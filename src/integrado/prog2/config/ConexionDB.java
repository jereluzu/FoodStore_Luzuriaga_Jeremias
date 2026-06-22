package integrado.prog2.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/food_store_db";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Si tenés contraseña en MySQL, ponela acá adentro

    private static Connection conexion = null;

    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                // Cargamos el driver de MySQL de forma explícita
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Intentamos conectar
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("¡Conexión exitosa a la base de datos!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de MySQL (JDBC). " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al intentar conectar con la Base de Datos: " + e.getMessage());
        }
        return conexion;
    }

    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                    System.out.println("Conexión cerrada correctamente.");
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}