package com.sakila.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFile {

    private final Properties props = new Properties();

    public PropertyFile() {
        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("dbconfig.properties")) {

            if (input == null) {
                throw new RuntimeException("No se encontró dbconfig.properties en el classpath");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error cargando propiedades de BD: " + e.getMessage(), e);
        }
    }

    public String getPropValue(String key) {
        return props.getProperty(key);
    }
}
