package com.sakila.model;

import com.sakila.data.Actor;
import com.sakila.orm.DataContext;
import com.sakila.orm.iDatapost;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActorModel extends DataContext implements iDatapost<Actor> {

    @Override
    public boolean insert(Actor entity) {
        String sql = "INSERT INTO actor (first_name, last_name, last_update) VALUES (?, ?, NOW())";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());

            int rows = stmt.executeUpdate();
            setLastMessage("Actor creado. Filas afectadas: " + rows);
            return rows > 0;

        } catch (SQLException e) {
            setLastMessage("Error al crear actor: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Actor entity) {
        String sql = "UPDATE actor SET first_name = ?, last_name = ?, last_update = NOW() WHERE actor_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setInt(3, entity.getActorId());

            int rows = stmt.executeUpdate();
            setLastMessage("Actor actualizado. Filas afectadas: " + rows);
            return rows > 0; // Si es 0, no actualizó nada (quizás ID incorrecto)

        } catch (SQLException e) {
            setLastMessage("Error al actualizar actor: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM actor WHERE actor_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            setLastMessage("Actor eliminado. Filas afectadas: " + rows);
            return rows > 0;

        } catch (SQLException e) {
            setLastMessage("Error al eliminar actor (Posible FK): " + e.getMessage());
            return false;
        }
    }

    @Override
    public Actor getById(int id) {
        String sql = "SELECT actor_id, first_name, last_name, last_update FROM actor WHERE actor_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
            setLastMessage("Actor no encontrado.");
            return null;

        } catch (SQLException e) {
            setLastMessage("Error al buscar actor: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Actor> getAll() {
        String sql = "SELECT actor_id, first_name, last_name, last_update FROM actor ORDER BY actor_id LIMIT 500"; // Limitamos a 500 por seguridad visual
        List<Actor> lista = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }
            setLastMessage("Actores cargados: " + lista.size());
            return lista;

        } catch (SQLException e) {
            setLastMessage("Error al listar actores: " + e.getMessage());
            return lista;
        }
    }

    @Override
    public List<Actor> search(String text) {
        String sql = "SELECT actor_id, first_name, last_name, last_update FROM actor " +
                "WHERE first_name LIKE ? OR last_name LIKE ? ORDER BY actor_id";
        List<Actor> lista = new ArrayList<>();
        String pattern = "%" + text + "%";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pattern);
            stmt.setString(2, pattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapRow(rs));
                }
            }
            setLastMessage("Búsqueda completada. Resultados: " + lista.size());
            return lista;

        } catch (SQLException e) {
            setLastMessage("Error en búsqueda: " + e.getMessage());
            return lista;
        }
    }

    // Método auxiliar privado para mapear el ResultSet a Objeto
    private Actor mapRow(ResultSet rs) throws SQLException {
        return new Actor(
                rs.getInt("actor_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getTimestamp("last_update")
        );
    }
}