package config;


import java.util.Optional;

public class Configuration {

    private static final PropertiesLoader propertyLoader = PropertiesLoader.load();

    public static final String DATETIME_FORMAT =
            Optional.ofNullable(getProperty(ConfigValue.DATETIME_FORMAT.getVariableName()))
                    .orElse("dd-MM-yyyy");

    public static final String COLUMN_NAME =
            Optional.ofNullable(getProperty(ConfigValue.COLUMN_NAME.getVariableName()))
                    .orElse("Temp column name");

    public static final String COLS_PATH =
            Optional.ofNullable(getProperty(ConfigValue.COLUMNS_PATH.getVariableName()))
                    .orElse("src/main/resources/cols.json");
    public static final String DIR_PATH =
            Optional.ofNullable(getProperty(ConfigValue.DIR_PATH.getVariableName()))
                    .orElse("src/main/resources/lists/");

    public static final String DATA_PATH =
            Optional.ofNullable(getProperty(ConfigValue.DATA_PATH.getVariableName()))
                    .orElse("data");

    private Configuration() {

    }


    private static String getProperty(String key) {
        return propertyLoader.getProperty(key);
    }




}
