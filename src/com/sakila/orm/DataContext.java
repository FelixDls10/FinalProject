package com.sakila.orm;

import com.sakila.util.PropertyFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DataContext implements AutoCloseable {

    private static Connection sharedConnection;
    protected String lastMessage;

    protected DataContext() {
        initConnection();
    }

    private void initConnection() {
        try {
            if (sharedConnection != null && !sharedConnection.isClosed()) {
                return;
            }

            PropertyFile config = new PropertyFile();
            String driver = config.getPropValue("db.driver");
            String url = config.getPropValue("db.url");
            String user = config.getPropValue("db.user");
            String password = config.getPropValue("db.password");

            Class.forName(driver);
            sharedConnection = DriverManager.getConnection(url, user, password);

        } catch (Exception ex) {
            throw new RuntimeException("Error inicializando conexión a Sakila: " + ex.getMessage(), ex);
        }
    }

    protected ResultSet executeQuery(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = prepareStatement(sql, params);
        return stmt.executeQuery();
    }

    protected int executeUpdate(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = prepareStatement(sql, params);
        return stmt.executeUpdate();
    }

    private PreparedStatement prepareStatement(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = sharedConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
        }
        return stmt;
    }

    // 🔹 Ya NO es abstracto. Este es el que usa ActorController.getLastError()
    public String getLastMessage() {
        return lastMessage;
    }

    @Override
    public void close() {
        // Si quieres, aquí podrías cerrar la conexión al final de la app.
    }
}
