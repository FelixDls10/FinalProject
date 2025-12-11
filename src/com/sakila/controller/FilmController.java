package com.sakila.controller;

import com.sakila.data.Film;
import com.sakila.model.FilmModel;

import java.util.List;
import java.util.Scanner;

public class FilmController extends BaseController {

    private final FilmModel model;

    public FilmController(Scanner scanner) {
        super(scanner);
        this.model = new FilmModel();
    }

    @Override
    protected void printMenu() {
        System.out.println("\n=== Gestión de Películas ===");
        System.out.println("1. Listar películas");
        System.out.println("2. Buscar película por título");
        System.out.println("3. Crear película (básica)");
        System.out.println("4. Editar película");
        System.out.println("5. Eliminar película");
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
            case 0 -> System.out.println("Volviendo al menú principal...");
            default -> System.out.println("Opción inválida");
        }
    }

    @Override
    protected void postHandle() {
        String lastMessage = model.getLastMessage();
        if (lastMessage != null && !lastMessage.isBlank()) {
            System.out.println("Mensaje: " + lastMessage);
        }
    }

    // --------- Operaciones ---------

    private void listAll() {
        List<Film> films = model.getAll();
        if (films.isEmpty()) {
            System.out.println("No hay películas.");
            return;
        }

        for (Film film : films) {
            System.out.printf(
                    "%d | %s | Año: %d | Duración: %d | Rating: %s%n",
                    film.getFilmId(),
                    film.getTitle(),
                    film.getReleaseYear(),
                    film.getLength(),
                    film.getRating()
            );
        }
    }

    private void search() {
        System.out.print("Título a buscar: ");
        String text = scanner.nextLine();
        List<Film> result = model.search(text);
        if (result.isEmpty()) {
            System.out.println("No se encontraron películas.");
            return;
        }

        for (Film film : result) {
            System.out.printf(
                    "%d | %s | Año: %d | Duración: %d | Rating: %s%n",
                    film.getFilmId(),
                    film.getTitle(),
                    film.getReleaseYear(),
                    film.getLength(),
                    film.getRating()
            );
        }
    }

    private void create() {
        Film film = new Film();

        System.out.print("Título: ");
        film.setTitle(scanner.nextLine());

        System.out.print("Descripción: ");
        film.setDescription(scanner.nextLine());

        System.out.print("Año de lanzamiento (enter para N/A): ");
        String yearStr = scanner.nextLine();
        if (!yearStr.isBlank()) {
            film.setReleaseYear(Integer.parseInt(yearStr));
        }

        System.out.print("ID de idioma (language_id, ej. 1=English): ");
        film.setLanguageId(Integer.parseInt(scanner.nextLine()));

        System.out.print("Duración en minutos (enter para N/A): ");
        String lenStr = scanner.nextLine();
        if (!lenStr.isBlank()) {
            film.setLength(Integer.parseInt(lenStr));
        }

        // --- Rating controlado ---
        String rating = readRatingWithDefault();
        film.setRating(rating);

        if (!model.insert(film)) {
            System.out.println("No se pudo crear la película.");
        }
    }

    private void update() {
        System.out.print("ID de la película a editar: ");
        int id = Integer.parseInt(scanner.nextLine());

        Film film = model.getById(id);
        if (film == null) {
            System.out.println("Película no encontrada.");
            return;
        }

        System.out.println("Título actual: " + film.getTitle());
        System.out.print("Nuevo título (enter para mantener): ");
        String title = scanner.nextLine();
        if (!title.isBlank()) {
            film.setTitle(title);
        }

        System.out.println("Descripción actual: " + film.getDescription());
        System.out.print("Nueva descripción (enter para mantener): ");
        String desc = scanner.nextLine();
        if (!desc.isBlank()) {
            film.setDescription(desc);
        }

        System.out.println("Rating actual: " + film.getRating());
        System.out.print("Nuevo rating (G, PG, PG-13, R, NC-17) (enter para mantener): ");
        String newRating = scanner.nextLine().trim().toUpperCase();
        if (!newRating.isBlank()) {
            if (isValidRating(newRating)) {
                System.out.println("Rating inválido. Debe ser uno de: G, PG, PG-13, R, NC-17.");
                return;
            }
            film.setRating(newRating);
        }

        if (!model.update(film)) {
            System.out.println("No se pudo actualizar la película.");
        }
    }

    private void delete() {
        System.out.print("ID de la película a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());

        boolean deleted = model.delete(id);

        if (deleted) {
            System.out.println("Película eliminada correctamente.");
        } else {
            System.out.println("No se pudo eliminar la película. " +
                    "Verifique que exista o que no tenga registros en inventory/rental.");
        }
    }

    // --------- Helpers para rating ---------

    private String readRatingWithDefault() {
        System.out.print("Rating (G, PG, PG-13, R, NC-17) [G por defecto]: ");
        String rating = scanner.nextLine().trim().toUpperCase();
        if (rating.isEmpty()) {
            return "G";
        }
        if (isValidRating(rating)) {
            System.out.println("Rating inválido. Se usará G por defecto.");
            return "G";
        }
        return rating;
    }

    private boolean isValidRating(String rating) {
        return !rating.equals("G")
                && !rating.equals("PG")
                && !rating.equals("PG-13")
                && !rating.equals("R")
                && !rating.equals("NC-17");
    }
}
