/* Felix De los Santos | 100658633 */

package com.sakila.ui;

import com.sakila.controller.ActorController;
import com.sakila.controller.CategoryController;
import com.sakila.controller.FilmController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Inicializamos el Scanner una sola vez para toda la aplicación
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n========================================");
            System.out.println("      SAKILA ORM MANAGEMENT SYSTEM      ");
            System.out.println("========================================");
            System.out.println("1. Gestión de Actores (Actors)");
            System.out.println("2. Gestión de Categorías (Categories)");
            System.out.println("3. Gestión de Películas (Films)");
            System.out.println("0. Salir");
            System.out.println("========================================");
            System.out.print("Seleccione una opción: ");

            String input = scanner.nextLine();

            if (input.equals("0")) {
                System.out.println("Cerrando sistema... ¡Hasta luego!");
                break;
            }

            switch (input) {
                case "1":
                    // Lanza el módulo de Actores
                    new ActorController(scanner).run();
                    break;
                case "2":
                    // Lanza el módulo de Categorías
                    new CategoryController(scanner).run();
                    break;
                case "3":
                    // Lanza el módulo de Películas
                    new FilmController(scanner).run();
                    break;
                default:
                    System.out.println(">> Error: Opción no válida, intente de nuevo.");
            }
        }

        scanner.close();
    }
}