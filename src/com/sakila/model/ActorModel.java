package com.sakila.model;

import com.sakila.data.Actor;
import com.sakila.orm.DataContext;
import com.sakila.orm.iDatapost;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActorModel extends DataContext implements iDatapost<Actor> {

    private List<Actor> cache = new ArrayList<>();

    private Actor mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("actor_id");
        String first = rs.getString("first_name");
        String last = rs.getString("last_name");
        rs.getTimestamp("last_update");
        return new Actor(id, first, last);
    }

    @Override
    public boolean insert(Actor entity) {
        String sql = "INSERT INTO actor (first_name, last_name, last_update) VALUES (?, ?, NOW())";
        try {
            int rows = executeUpdate(sql, entity.getFirstName(), entity.getLastName());
            return rows == 1;
        } catch (SQLException e) {
            lastMessage = e.getMessage();
            return false;
        }
    }

    @Override
    public boolean update(Actor entity) {
        String sql = "UPDATE actor SET first_name = ?, last_name = ?, last_update = NOW() WHERE actor_id = ?";
        try {
            int rows = executeUpdate(sql,
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getActorId());
            return rows == 1;
        } catch (SQLException e) {
            lastMessage = e.getMessage();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM actor WHERE actor_id = ?";
        try {
            int rows = executeUpdate(sql, id);
            return rows == 1;
        } catch (SQLException e) {
            lastMessage = e.getMessage();
            return false;
        }
    }

    @Override
    public Actor getById(int id) {
        String sql = "SELECT actor_id, first_name, last_name, last_update FROM actor WHERE actor_id = ?";
        try (ResultSet rs = executeQuery(sql, id)) {
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            lastMessage = e.getMessage();
        }
        return null;
    }

    @Override
    public List<Actor> getAll() {
        cache = new ArrayList<>();
        String sql = "SELECT actor_id, first_name, last_name, last_update FROM actor ORDER BY actor_id";
        try (ResultSet rs = executeQuery(sql)) {
            while (rs.next()) {
                cache.add(mapRow(rs));
            }
        } catch (SQLException e) {
            lastMessage = e.getMessage();
        }
        return cache;
    }

    @Override
    public List<Actor> search(String text) {
        cache = new ArrayList<>();
        String pattern = "%" + text.trim() + "%";

        String sql = "SELECT actor_id, first_name, last_name, last_update " +
                "FROM actor " +
                "WHERE first_name LIKE ? " +
                "   OR last_name LIKE ? " +
                "ORDER BY actor_id";

        try (ResultSet rs = executeQuery(sql, pattern, pattern)) {
            while (rs.next()) {
                cache.add(mapRow(rs));
            }
        } catch (SQLException e) {
            lastMessage = e.getMessage();
            System.out.println("Error en búsqueda de actores: " + lastMessage);
        }
        return cache;
    }

    @Override
    public String getLastMessage() {
        return "";
    }
}
