/* Felix De los Santos | 100658633 */

package com.sakila.ui;

import com.sakila.controller.ActorController;
import com.sakila.data.Actor;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ActorController actorController = new ActorController();

    public static void main(String[] args) {

        int option;
        do {
            System.out.println("===== MENÚ SAKILA - ACTOR =====");
            System.out.println("1. Listar actores");
            System.out.println("2. Buscar actores por nombre");
            System.out.println("3. Crear actor");
            System.out.println("4. Actualizar actor");
            System.out.println("5. Eliminar actor");
            System.out.println("0. Salir");
            System.out.print("Seleccione opción: ");

            option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> listarActores();
                case 2 -> buscarActores();
                case 3 -> crearActor();
                case 4 -> actualizarActor();
                case 5 -> eliminarActor();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }

        } while (option != 0);
    }

    private static void listarActores() {
        List<Actor> actores = actorController.listarTodos();
        actores.forEach(System.out::println);
    }

    private static void buscarActores() {
        System.out.print("Texto a buscar: ");
        String texto = scanner.nextLine();
        List<Actor> actores = actorController.buscarPorNombre(texto);
        if (actores.isEmpty()) {
            System.out.println("No se encontraron actores con ese criterio.");
        } else {
            actores.forEach(System.out::println);
        }
    }

    private static void crearActor() {
        System.out.print("Nombre: ");
        String first = scanner.nextLine();
        System.out.print("Apellido: ");
        String last = scanner.nextLine();

        boolean ok = actorController.crearActor(first, last);
        System.out.println(ok ? "Actor creado correctamente." : "Error creando actor.");
    }

    private static void actualizarActor() {
        System.out.print("ID del actor a actualizar: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Nuevo nombre: ");
        String first = scanner.nextLine();
        System.out.print("Nuevo apellido: ");
        String last = scanner.nextLine();

        boolean ok = actorController.actualizarActor(id, first, last);
        System.out.println(ok ? "Actor actualizado." : "No se pudo actualizar (ID no encontrado).");
    }

    private static void eliminarActor() {
        System.out.print("ID del actor a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());

        boolean ok = actorController.eliminarActor(id);

        if (ok) {
            System.out.println("Actor eliminado.");
        } else {
            System.out.println("No se pudo eliminar el actor.");
            String detalle = actorController.getLastError();
            if (detalle != null && !detalle.isBlank()) {
                System.out.println("Detalle técnico: " + detalle);
                System.out.println("Nota: en la BD Sakila es probable que el actor tenga películas asociadas, " +
                        "y MySQL bloquea el DELETE por restricciones de Foreign Key.");
            }
        }
    }
}
