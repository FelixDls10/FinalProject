package com.sakila.controller;

import com.sakila.data.Category;
import com.sakila.model.CategoryModel;
import java.util.List;
import java.util.Scanner;

public class CategoryController extends BaseController {

    private final CategoryModel model;

    public CategoryController(Scanner scanner) {
        super(scanner);
        this.model = new CategoryModel();
    }

    @Override
    protected void printMenu() {
        System.out.println("\n=== Gestión de Categorías ===");
        System.out.println("1. Listar categorías");
        System.out.println("2. Buscar categoría por nombre");
        System.out.println("3. Crear categoría");
        System.out.println("4. Editar categoría");
        System.out.println("5. Eliminar categoría");
        System.out.println("0. Volver al menú principal");
    }

    @Override
    protected void handleOption(int option) {
        switch (option) {
            case 1 -> listAll();
            case 2 -> search();
            case 3 -> create();
            case 4 -> update();
            case 5 -> delete();
            default -> System.out.println("Opción inválida");
        }
    }

    @Override
    protected void postHandle() {
        String lastMessage = model.getLastMessage();
        if (lastMessage != null && !lastMessage.isBlank()) {
            System.out.println(">> Mensaje del Sistema: " + lastMessage);
        }
    }

    private void listAll() {
        List<Category> categories = model.getAll();
        if (categories.isEmpty()) {
            System.out.println("No hay categorías.");
        } else {
            categories.forEach(System.out::println);
        }
    }

    private void search() {
        System.out.print("Nombre a buscar: ");
        String text = scanner.nextLine();
        List<Category> result = model.search(text);
        if (result.isEmpty()) {
            System.out.println("No se encontraron categorías.");
        } else {
            result.forEach(System.out::println);
        }
    }

    private void create() {
        System.out.print("Nombre de la categoría: ");
        String name = scanner.nextLine();
        Category category = new Category();
        category.setName(name);
        model.insert(category);
    }

    private void update() {
        System.out.print("ID de la categoría a editar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Category category = model.getById(id);
            if (category == null) return;

            System.out.println("Nombre actual: " + category.getName());
            System.out.print("Nuevo nombre (Enter para mantener): ");
            String name = scanner.nextLine();
            if (!name.isBlank()) {
                category.setName(name);
            }
            model.update(category);
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    private void delete() {
        System.out.print("ID de la categoría a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            model.delete(id);
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }
}