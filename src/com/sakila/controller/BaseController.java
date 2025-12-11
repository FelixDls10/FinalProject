package com.sakila.controller;

import java.util.Scanner;

public abstract class BaseController {
    protected Scanner scanner;

    public BaseController(Scanner scanner) {
        this.scanner = scanner;
    }

    protected abstract void printMenu();
    protected abstract void handleOption(int option);

    // Método opcional para mensajes después de la acción
    protected void postHandle() {}

    public void run() {
        while (true) {
            printMenu();
            System.out.print("Opción: ");
            try {
                String input = scanner.nextLine();
                int option = Integer.parseInt(input);

                if (option == 0) break;

                handleOption(option);
                postHandle(); // Mostrar mensajes del modelo

            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido.");
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
            System.out.println("--------------------------------");
        }
    }
}