package org.example.microsoftlists.config;



import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertiesLoader {
    private final Properties properties = new Properties();

    @SneakyThrows
    private PropertiesLoader(String propertiesFileName) {
        Objects.requireNonNull(propertiesFileName);
        InputStream input = getClass().getClassLoader().getResourceAsStream(propertiesFileName);
        properties.load(input);
    }

    public static PropertiesLoader load(String propertiesFileName) {
        return new PropertiesLoader(propertiesFileName);
    }

    public static PropertiesLoader load() {
        return new PropertiesLoader(ConfigValue.CONFIG_FILE.getVariableName());
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
