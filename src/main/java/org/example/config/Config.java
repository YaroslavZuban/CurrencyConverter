package org.example.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new FileNotFoundException("Property file 'config.properties' not found in the classpath");
            }
            properties.load(input);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
