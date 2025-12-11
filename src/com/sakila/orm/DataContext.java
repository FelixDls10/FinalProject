package com.sakila.orm;

import com.sakila.util.PropertyFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DataContext {

    private String lastMessage = "";
    // Instanciamos tu clase utilitaria para leer el archivo
    private final PropertyFile config = new PropertyFile();

    protected Connection getConnection() throws SQLException {
        // 1. Cargamos el Driver leyendo la propiedad "db. driver"
        String driver = config.getPropValue("db.driver");

        try {
            // Si el driver viene nulo, usamos el default por seguridad
            if (driver == null || driver.isBlank()) {
                driver = "com.mysql.cj.jdbc.Driver";
            }
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver JDBC", e);
        }

        // 2. Leemos las credenciales EXACTAS de tu archivo .properties
        String url = config.getPropValue("db.url");
        String user = config.getPropValue("db.user");
        String pass = config.getPropValue("db.password");

        // 3. Validamos que no vengan vacíos para evitar errores raros
        if (url == null || user == null || pass == null) {
            throw new SQLException("Error: No se pudieron leer las credenciales del archivo dbconfig.properties");
        }

        return DriverManager.getConnection(url, user, pass);
    }

    public String getLastMessage() {
        return lastMessage;
    }

    protected void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}