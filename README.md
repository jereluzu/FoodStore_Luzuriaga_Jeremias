# FoodStore - Proyecto Final Programación 2

## Descripción
Sistema de gestión de pedidos desarrollado en Java utilizando arquitectura en capas (DAO, Service, Entities) y persistencia con MySQL mediante JDBC.

## Requisitos Previos
- Tener instalado [XAMPP](https://www.apachefriends.org/es/index.html) (con Apache y MySQL).
- [IntelliJ IDEA](https://www.jetbrains.com/idea/) o cualquier IDE de Java.

## Configuración de la Base de Datos
1. Iniciar los servicios de MySQL desde el Panel de Control de XAMPP.
2. Ingresar a `http://localhost/phpmyadmin`.
3. Crear una base de datos llamada `food_store_db`.
4. Importar el archivo `food_store_db.sql` incluido en la raíz de este repositorio.

## Configuración de Persistencia
Asegurarse de que los datos de conexión en tu clase `ConexionDB.java` coincidan con tu configuración local:
- Usuario: `root`
- Contraseña: `""` (vacío por defecto en XAMPP)
- URL: `jdbc:mysql://localhost:3306/food_store_db`

## Ejecución
1. Abrir el proyecto en IntelliJ IDEA.
2. Ejecutar la clase `Main.java` ubicada en `src/Main.java`.

## Video
https://youtu.be/qeobYOMHhbg?si=mMX-gRlGf0DJ-7mb

## PDF
[Documentacion_Final (1).pdf](https://github.com/user-attachments/files/29202749/Documentacion_Final.1.pdf)
