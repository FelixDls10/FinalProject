package com.sakila.model;

import com.sakila.data.Category;
import com.sakila.orm.DataContext;
import com.sakila.orm.iDatapost;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryModel extends DataContext implements iDatapost<Category> {

    @Override
    public boolean insert(Category entity) {
        String sql = "INSERT INTO category (name) VALUES (?)";
        // Al usar try(Connection...), se cierra al terminar este bloque.
        // Como DataContext da una nueva cada vez, no afecta a las demás.
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entity.getName());
            int rows = stmt.executeUpdate();
            setLastMessage("Categoría insertada. Filas afectadas: " + rows);
            return rows > 0;

        } catch (SQLException e) {
            setLastMessage("Error al insertar categoría: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Category entity) {
        String sql = "UPDATE category SET name = ? WHERE category_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entity.getName());
            stmt.setInt(2, entity.getCategoryId());
            int rows = stmt.executeUpdate();
            setLastMessage("Categoría actualizada. Filas afectadas: " + rows);
            return rows > 0; // Corrección lógica: true si actualizó algo

        } catch (SQLException e) {
            setLastMessage("Error al actualizar categoría: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM category WHERE category_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            setLastMessage("Categoría eliminada. Filas afectadas: " + rows);
            return rows > 0;

        } catch (SQLException e) {
            setLastMessage("Error al eliminar (Probable FK): " + e.getMessage());
            return false;
        }
    }

    @Override
    public Category getById(int id) {
        String sql = "SELECT category_id, name, last_update FROM category WHERE category_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
            setLastMessage("Categoría no encontrada.");
            return null;

        } catch (SQLException e) {
            setLastMessage("Error al buscar categoría: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Category> getAll() {
        String sql = "SELECT category_id, name, last_update FROM category ORDER BY name";
        List<Category> result = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                result.add(mapRow(rs));
            }
            setLastMessage("Categorías cargadas: " + result.size());
            return result;

        } catch (SQLException e) {
            setLastMessage("Error al listar categorías: " + e.getMessage());
            return result;
        }
    }

    @Override
    public List<Category> search(String text) {
        String sql = "SELECT category_id, name, last_update FROM category WHERE name LIKE ? ORDER BY name";
        List<Category> result = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + text + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
            }
            setLastMessage("Búsqueda finalizada. Resultados: " + result.size());
            return result;

        } catch (SQLException e) {
            setLastMessage("Error en búsqueda: " + e.getMessage());
            return result;
        }
    }

    private Category mapRow(ResultSet rs) throws SQLException {
        return new Category(
                rs.getInt("category_id"),
                rs.getString("name"),
                rs.getTimestamp("last_update")
        );
    }
}