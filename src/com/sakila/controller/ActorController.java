package com.sakila.controller;

import com.sakila.data.Actor;
import com.sakila.model.ActorModel;
import java.util.List;
import java.util.Scanner;

public class ActorController extends BaseController {

    private final ActorModel model;

    public ActorController(Scanner scanner) {
        super(scanner);
        this.model = new ActorModel();
    }

    @Override
    protected void printMenu() {
        System.out.println("\n=== Gestión de Actores ===");
        System.out.println("1. Listar actores");
        System.out.println("2. Buscar actor (Nombre/Apellido)");
        System.out.println("3. Crear actor");
        System.out.println("4. Editar actor");
        System.out.println("5. Eliminar actor");
        System.out.println("0. Volver");
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
        String msg = model.getLastMessage();
        if (msg != null && !msg.isBlank()) {
            System.out.println(">> Sistema: " + msg);
        }
    }

    private void listAll() {
        List<Actor> actors = model.getAll();
        actors.forEach(System.out::println);
    }

    private void search() {
        System.out.print("Texto a buscar: ");
        String text = scanner.nextLine();
        List<Actor> actors = model.search(text);
        if(actors.isEmpty()) System.out.println("No se encontraron actores.");
        else actors.forEach(System.out::println);
    }

    private void create() {
        System.out.print("Nombre: ");
        String fname = scanner.nextLine();
        System.out.print("Apellido: ");
        String lname = scanner.nextLine();

        Actor actor = new Actor(0, fname, lname);
        model.insert(actor);
    }

    private void update() {
        System.out.print("ID del Actor a editar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Actor actor = model.getById(id);

            if (actor == null) return;

            System.out.println("Editando a: " + actor);

            System.out.print("Nuevo Nombre (Enter para mantener): ");
            String fname = scanner.nextLine();
            if(!fname.isBlank()) actor.setFirstName(fname);

            System.out.print("Nuevo Apellido (Enter para mantener): ");
            String lname = scanner.nextLine();
            if(!lname.isBlank()) actor.setLastName(lname);

            model.update(actor);

        } catch(NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    private void delete() {
        System.out.print("ID del Actor a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            model.delete(id);
        } catch(NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }
}