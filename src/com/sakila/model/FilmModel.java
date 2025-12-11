package com.sakila.model;

import com.sakila.data.Film;
import com.sakila.orm.DataContext;
import com.sakila.orm.iDatapost;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmModel extends DataContext implements iDatapost<Film> {

    @Override
    public boolean insert(Film entity) {
        String sql = "INSERT INTO film " +
                "(title, description, release_year, language_id, length, rating) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, entity.getTitle());
            stmt.setString(2, entity.getDescription());

            if (entity.getReleaseYear() != null) {
                stmt.setInt(3, entity.getReleaseYear());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }

            stmt.setInt(4, entity.getLanguageId());

            if (entity.getLength() != null) {
                stmt.setInt(5, entity.getLength());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.setString(6, entity.getRating());

            int rows = stmt.executeUpdate();
            setLastMessage("Película creada correctamente (" + rows + " fila(s)).");
            return rows > 0;
        } catch (SQLException e) {
            setLastMessage("Error al crear película: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Film entity) {
        String sql = "UPDATE film SET " +
                "title = ?, " +
                "description = ?, " +
                "release_year = ?, " +
                "language_id = ?, " +
                "length = ?, " +
                "rating = ? " +
                "WHERE film_id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, entity.getTitle());
            stmt.setString(2, entity.getDescription());

            if (entity.getReleaseYear() != null) {
                stmt.setInt(3, entity.getReleaseYear());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }

            stmt.setInt(4, entity.getLanguageId());

            if (entity.getLength() != null) {
                stmt.setInt(5, entity.getLength());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.setString(6, entity.getRating());
            stmt.setInt(7, entity.getFilmId());

            int rows = stmt.executeUpdate();
            setLastMessage("Película actualizada (" + rows + " fila(s)).");
            return rows > 0;
        } catch (SQLException e) {
            setLastMessage("Error al actualizar película: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM film WHERE film_id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            setLastMessage("Película eliminada (" + rows + " fila(s)).");
            return rows > 0;
        } catch (SQLException e) {
            setLastMessage("Error al eliminar película: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Film getById(int id) {
        String sql = "SELECT film_id, title, description, release_year, " +
                "language_id, length, rating, last_update " +
                "FROM film WHERE film_id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            setLastMessage("Error al buscar película: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Film> getAll() {
        String sql = "SELECT film_id, title, description, release_year, " +
                "language_id, length, rating, last_update " +
                "FROM film LIMIT 100";

        List<Film> result = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                result.add(mapRow(rs));
            }
            setLastMessage("Películas cargadas correctamente (" + result.size() + ").");
        } catch (SQLException e) {
            setLastMessage("Error al obtener películas: " + e.getMessage());
        }

        return result;
    }

    public List<Film> search(String text) {
        String sql = "SELECT film_id, title, description, release_year, " +
                "language_id, length, rating, last_update " +
                "FROM film " +
                "WHERE title LIKE ? " +
                "ORDER BY title";

        List<Film> result = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, "%" + text + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
            }

            setLastMessage("Búsqueda de películas completada (" + result.size() + ").");
        } catch (SQLException e) {
            setLastMessage("Error al buscar películas: " + e.getMessage());
        }

        return result;
    }

    // ------------------ mapeo ResultSet -> Film ------------------

    private Film mapRow(ResultSet rs) throws SQLException {
        Film film = new Film();

        film.setFilmId(rs.getInt("film_id"));
        film.setTitle(rs.getString("title"));
        film.setDescription(rs.getString("description"));

        int year = rs.getInt("release_year");
        if (rs.wasNull()) {
            film.setReleaseYear(null);
        } else {
            film.setReleaseYear(year);
        }

        film.setLanguageId(rs.getInt("language_id"));

        int length = rs.getInt("length");
        if (rs.wasNull()) {
            film.setLength(null);
        } else {
            film.setLength(length);
        }

        film.setRating(rs.getString("rating"));
        // last_update es TIMESTAMP en sakila
        Timestamp ts = rs.getTimestamp("last_update");
        if (ts != null) {
            film.setLastUpdate();
        }

        return film;
    }
}
