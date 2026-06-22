import integrado.prog2.config.ConexionDB;
import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Rol;
import integrado.prog2.service.CategoriaService;
import integrado.prog2.service.PedidoService;
import integrado.prog2.service.ProductoService;
import integrado.prog2.service.UsuarioService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CategoriaService categoriaService = new CategoriaService();
    private static final ProductoService productoService = new ProductoService();
    private static final UsuarioService usuarioService = new UsuarioService();
    private static final PedidoService pedidoService = new PedidoService();

    public static void main(String[] args) {
        int opcion = -1;

        System.out.println("=============================================");
        System.out.println("  SISTEMA FOOD STORE - GESTIÓN INTEGRAL      ");
        System.out.println("=============================================");

        while (opcion != 0) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Gestión de Categorías");
            System.out.println("2. Gestión de Productos");
            System.out.println("3. Gestión de Usuarios");
            System.out.println("0. Salir del Sistema");
            System.out.print("Seleccione una opción: ");

            try {
                String entrada = scanner.nextLine().trim();
                if (entrada.isEmpty()) continue;
                opcion = Integer.parseInt(entrada);

                switch (opcion) {
                    case 1:
                        menuCategorias();
                        break;
                    case 2:
                        menuProductos();
                        break;
                    case 3:
                        menuUsuarios();
                        break;
                    case 4:
                        menuPedidos();
                        break;
                    case 0:
                        System.out.println("\nCerrando recursos y saliendo... ¡Éxitos en la entrega!");
                        break;
                    default:
                        System.out.println(" Opción inválida en el Menú Principal.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Error: Ingrese un número válido.");
            } catch (Exception e) {
                System.out.println(" Ocurrió un error: " + e.getMessage());
            }
        }
        ConexionDB.cerrarConexion();
        scanner.close();
    }

    // =========================================================================
    // ÉPICA 1: GESTIÓN DE CATEGORÍAS
    // =========================================================================
    private static void menuCategorias() throws Exception {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- GESTIÓN DE CATEGORÍAS ---");
            System.out.println("1. Listar Categorías");
            System.out.println("2. Crear Categoría");
            System.out.println("3. Editar Categoría");
            System.out.println("4. Eliminar Categoría");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) continue;
            op = Integer.parseInt(entrada);

            switch (op) {
                case 1:
                    System.out.println("\n--- LISTADO DE CATEGORÍAS ---");
                    List<Categoria> lista = categoriaService.listarTodos();
                    if (lista.isEmpty()) {
                        System.out.println("No hay categorías cargadas.");
                    } else {
                        for (Categoria c : lista) {
                            System.out.println("ID: " + c.getId() + " | Nombre: " + c.getNombre() + " | Descripción: " + c.getDescripcion());
                        }
                    }
                    break;

                case 2:
                    System.out.println("\n--- CREAR CATEGORÍA ---");
                    System.out.print("Nombre: ");
                    String nom = scanner.nextLine().trim();
                    System.out.print("Descripción: ");
                    String desc = scanner.nextLine().trim();

                    Categoria nueva = new Categoria(nom, desc);
                    categoriaService.guardar(nueva);
                    System.out.println("¡Categoría creada exitosamente!");
                    break;

                case 3:
                    System.out.println("\n--- EDITAR CATEGORÍA ---");
                    System.out.print("Ingrese el ID de la categoría a editar: ");
                    Long idEdit = Long.parseLong(scanner.nextLine().trim());
                    Categoria catEdit = categoriaService.buscarPorId(idEdit);

                    if (catEdit == null) {
                        System.out.println(" El ID no existe o la categoría está eliminada.");
                    } else {
                        System.out.print("Nuevo Nombre (" + catEdit.getNombre() + "): ");
                        String nuevoNom = scanner.nextLine().trim();
                        System.out.print("Nueva Descripción (" + catEdit.getDescripcion() + "): ");
                        String nuevaDesc = scanner.nextLine().trim();

                        if (!nuevoNom.isEmpty()) catEdit.setNombre(nuevoNom);
                        if (!nuevaDesc.isEmpty()) catEdit.setDescripcion(nuevaDesc);

                        categoriaService.actualizar(catEdit);
                        System.out.println("¡Categoría actualizada con éxito!");
                    }
                    break;

                case 4:
                    System.out.println("\n--- ELIMINAR CATEGORÍA (BAJA LÓGICA) ---");
                    System.out.print("Ingrese el ID de la categoría a eliminar: ");
                    Long idEli = Long.parseLong(scanner.nextLine().trim());
                    Categoria catEli = categoriaService.buscarPorId(idEli);

                    if (catEli == null) {
                        System.out.println(" La categoría no existe o ya está eliminada.");
                    } else {
                        System.out.print("¿Está seguro de eliminar lógicamente la categoría '" + catEli.getNombre() + "'? (S/N): ");
                        String confirma = scanner.nextLine().trim().toUpperCase();
                        if (confirma.equals("S")) {
                            categoriaService.eliminar(idEli);
                            System.out.println("¡Categoría dada de baja de forma lógica!");
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                    }
                    break;
            }
        }
    }

    // =========================================================================
    // ÉPICA 2: GESTIÓN DE PRODUCTOS
    // =========================================================================
    private static void menuProductos() throws Exception {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Listar Productos (HU-PROD-01)");
            System.out.println("2. Crear Producto (HU-PROD-02)");
            System.out.println("3. Editar Producto (HU-PROD-03)");
            System.out.println("4. Eliminar Producto (Baja Lógica - HU-PROD-04)");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) continue;
            op = Integer.parseInt(entrada);

            switch (op) {
                case 1:
                    System.out.println("\n--- LISTADO DE PRODUCTOS ---");
                    List<Producto> lista = productoService.listarTodos();
                    if (lista.isEmpty()) {
                        System.out.println("No hay productos cargados.");
                    } else {
                        for (Producto p : lista) {
                            System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre() + " | $" + p.getPrecio() + " | Stock: " + p.getStock() + " | Categoría: " + p.getCategoria().getNombre());
                        }
                    }
                    break;

                case 2:
                    System.out.println("\n--- CREAR PRODUCTO ---");
                    System.out.print("Nombre: ");
                    String nom = scanner.nextLine().trim();
                    System.out.print("Descripción: ");
                    String desc = scanner.nextLine().trim();
                    System.out.print("Precio: ");
                    double pre = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Stock Inicial: ");
                    int st = Integer.parseInt(scanner.nextLine().trim());

                    System.out.println("Categorías disponibles:");
                    List<Categoria> cats = categoriaService.listarTodos();
                    for (Categoria c : cats) {
                        System.out.println(" -> ID [" + c.getId() + "] - " + c.getNombre());
                    }
                    System.out.print("Ingrese el ID de la Categoría: ");
                    Long idCat = Long.parseLong(scanner.nextLine().trim());
                    Categoria asociada = categoriaService.buscarPorId(idCat);

                    if (asociada == null) {
                        System.out.println(" Error: La categoría seleccionada no existe o fue eliminada.");
                    } else {
                        Producto nuevo = new Producto(nom, desc, pre, st, asociada);
                        productoService.guardar(nuevo);
                        System.out.println("¡Producto guardado exitosamente!");
                    }
                    break;

                case 3:
                    System.out.println("\n--- EDITAR PRODUCTO ---");
                    System.out.print("Ingrese el ID del producto a editar: ");
                    Long idEdit = Long.parseLong(scanner.nextLine().trim());
                    Producto prodEdit = productoService.buscarPorId(idEdit);

                    if (prodEdit == null) {
                        System.out.println(" El producto no existe o está eliminado.");
                    } else {
                        System.out.print("Nuevo Nombre (" + prodEdit.getNombre() + "): ");
                        String nuevoNom = scanner.nextLine().trim();
                        System.out.print("Nuevo Precio (" + prodEdit.getPrecio() + "): ");
                        String nuevoPreStr = scanner.nextLine().trim();
                        System.out.print("Nuevo Stock (" + prodEdit.getStock() + "): ");
                        String nuevoStStr = scanner.nextLine().trim();

                        if (!nuevoNom.isEmpty()) prodEdit.setNombre(nuevoNom);
                        if (!nuevoPreStr.isEmpty()) prodEdit.setPrecio(Double.parseDouble(nuevoPreStr));
                        if (!nuevoStStr.isEmpty()) prodEdit.setStock(Integer.parseInt(nuevoStStr));

                        productoService.actualizar(prodEdit);
                        System.out.println("¡Producto actualizado con éxito!");
                    }
                    break;

                case 4:
                    System.out.println("\n--- ELIMINAR PRODUCTO (BAJA LÓGICA) ---");
                    System.out.print("Ingrese el ID del producto a eliminar: ");
                    Long idEli = Long.parseLong(scanner.nextLine().trim());
                    Producto prodEli = productoService.buscarPorId(idEli);

                    if (prodEli == null) {
                        System.out.println(" El producto no existe o ya está eliminado.");
                    } else {
                        System.out.print("¿Seguro que quiere eliminar el producto '" + prodEli.getNombre() + "'? (S/N): ");
                        String confirma = scanner.nextLine().trim().toUpperCase();
                        if (confirma.equals("S")) {
                            productoService.eliminar(idEli);
                            System.out.println("¡Producto eliminado lógicamente!");
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                    }
                    break;
            }
        }
    }

    // =========================================================================
    // ÉPICA 3: GESTIÓN DE USUARIOS
    // =========================================================================
    private static void menuUsuarios() throws Exception {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- GESTIÓN DE USUARIOS ---");
            System.out.println("1. Listar Usuarios (HU-USR-01)");
            System.out.println("2. Registrar Usuario (HU-USR-02)");
            System.out.println("3. Editar Usuario (HU-USR-03)");
            System.out.println("4. Eliminar Usuario (Baja Lógica - HU-USR-04)");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) continue;
            op = Integer.parseInt(entrada);

            switch (op) {
                case 1:
                    System.out.println("\n--- LISTADO DE USUARIOS ---");
                    List<Usuario> lista = usuarioService.listarTodos();
                    if (lista.isEmpty()) {
                        System.out.println("No hay usuarios registrados.");
                    } else {
                        for (Usuario u : lista) {
                            System.out.println("ID: " + u.getId() + " | Nombre: " + u.getNombre() + " | Email: " + u.getEmail() + " | Rol: " + u.getRol());
                        }
                    }
                    break;

                case 2:
                    System.out.println("\n--- REGISTRAR USUARIO ---");
                    System.out.print("Nombre completo: ");
                    String nom = scanner.nextLine().trim();
                    System.out.print("Email: ");
                    String mail = scanner.nextLine().trim();
                    System.out.print("Contraseña: ");
                    String pass = scanner.nextLine().trim();
                    System.out.print("Rol (1 para ADMIN, 2 para USUARIO): ");
                    int r = Integer.parseInt(scanner.nextLine().trim());
                    Rol rolFinal = (r == 1) ? Rol.ADMIN : Rol.USUARIO;

                    Usuario nuevo = new Usuario(nom, mail, pass, rolFinal);
                    usuarioService.guardar(nuevo);
                    System.out.println("¡Usuario guardado con éxito!");
                    break;

                case 3:
                    System.out.println("\n--- EDITAR USUARIO ---");
                    System.out.print("Ingrese el ID del usuario a editar: ");
                    Long idEdit = Long.parseLong(scanner.nextLine().trim());
                    Usuario userEdit = usuarioService.buscarPorId(idEdit);

                    if (userEdit == null) {
                        System.out.println(" El usuario no existe o está eliminado.");
                    } else {
                        System.out.print("Nuevo Nombre (" + userEdit.getNombre() + "): ");
                        String nuevoNom = scanner.nextLine().trim();
                        System.out.print("Nuevo Email (" + userEdit.getEmail() + "): ");
                        String nuevoMail = scanner.nextLine().trim();

                        if (!nuevoNom.isEmpty()) userEdit.setNombre(nuevoNom);
                        if (!nuevoMail.isEmpty()) userEdit.setEmail(nuevoMail);

                        usuarioService.actualizar(userEdit);
                        System.out.println("¡Usuario actualizado con éxito!");
                    }
                    break;

                case 4:
                    System.out.println("\n--- ELIMINAR USUARIO ---");
                    System.out.print("Ingrese el ID del usuario a eliminar: ");
                    Long idEli = Long.parseLong(scanner.nextLine().trim());
                    Usuario userEli = usuarioService.buscarPorId(idEli);

                    if (userEli == null) {
                        System.out.println(" El usuario no existe o ya está eliminado.");
                    } else {
                        System.out.print("¿Seguro que quiere eliminar a '" + userEli.getNombre() + "'? (S/N): ");
                        String confirma = scanner.nextLine().trim().toUpperCase();
                        if (confirma.equals("S")) {
                            usuarioService.eliminar(idEli);
                            System.out.println("¡Usuario dado de baja lógicamente!");
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                    }
                    break;
            }
        }
    }
    // =========================================================================
    // ÉPICA 4: GESTIÓN DE PEDIDOS
    // =========================================================================
    private static void menuPedidos() throws Exception {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- GESTIÓN DE PEDIDOS ---");
            System.out.println("1. Listar Pedidos");
            System.out.println("2. Crear Pedido");
            System.out.println("3. Actualizar Estado/Pago");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) continue;
            op = Integer.parseInt(entrada);

            switch (op) {
                case 1:
                    System.out.println("\n--- LISTADO DE PEDIDOS ---");
                    for (integrado.prog2.entities.Pedido p : pedidoService.listarTodos()) {
                        System.out.println("ID: " + p.getId() + " | Usuario: " + p.getUsuario().getNombre() + " | Estado: " + p.getEstado() + " | Total: $" + p.getTotal());
                    }
                    break;
                case 2:
                    System.out.println("\n--- NUEVO PEDIDO ---");
                    System.out.print("ID de Usuario: ");
                    Long idUsr = Long.parseLong(scanner.nextLine().trim());
                    integrado.prog2.entities.Usuario u = usuarioService.buscarPorId(idUsr);

                    if (u != null) {
                        integrado.prog2.entities.Pedido nuevoPedido = new integrado.prog2.entities.Pedido(u, integrado.prog2.enums.FormaPago.EFECTIVO);
                        boolean agregar = true;
                        while(agregar) {
                            System.out.print("ID Producto: ");
                            Long idProd = Long.parseLong(scanner.nextLine().trim());
                            integrado.prog2.entities.Producto prod = productoService.buscarPorId(idProd);
                            System.out.print("Cantidad: ");
                            int cant = Integer.parseInt(scanner.nextLine().trim());

                            nuevoPedido.addDetallePedido(new integrado.prog2.entities.DetallePedido(cant, prod));
                            System.out.print("¿Agregar otro producto? (S/N): ");
                            agregar = scanner.nextLine().equalsIgnoreCase("S");
                        }
                        pedidoService.guardar(nuevoPedido);
                        System.out.println("¡Pedido registrado exitosamente!");
                    }
                    break;
                case 3:
                    System.out.println("Funcionalidad de actualización disponible.");
                    break;
            }
        }
    }}