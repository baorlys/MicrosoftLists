package org.example.microsoftlists.config;


import java.util.Optional;

public class Configuration {



    private static final PropertiesLoader propertyLoader = PropertiesLoader.load();

    public static final String DATETIME_FORMAT =
            Optional.ofNullable(getProperty(ConfigValue.DATETIME_FORMAT.getVariableName()))
                    .orElse("dd-MM-yyyy");
    public static final String LISTS_PATH =
            Optional.ofNullable(getProperty(ConfigValue.LISTS_PATH.getVariableName()))
                    .orElse("src/main/resources/lists.json");
    public static final String COLUMN_NAME =
            Optional.ofNullable(getProperty(ConfigValue.COLUMN_NAME.getVariableName()))
                    .orElse("Temp column name");

    public static final String COLS_PATH =
            Optional.ofNullable(getProperty(ConfigValue.COLUMNS_PATH.getVariableName()))
                    .orElse("src/main/resources/columns.json");

    public static final String DIR_TEMPLATE_PATH =
            Optional.ofNullable(getProperty(ConfigValue.DIR_TEMPLATE_PATH.getVariableName()))
                    .orElse("src/main/resources/templates/");
    public static final String DATA_PATH =
            Optional.ofNullable(getProperty(ConfigValue.DATA_PATH.getVariableName()))
                    .orElse("data");

    public static final String ROWS_PATH =
            Optional.ofNullable(getProperty(ConfigValue.ROWS_PATH.getVariableName()))
                    .orElse("src/main/resources/rows.json");

    public static final String VIEW_PATH =
            Optional.ofNullable(getProperty(ConfigValue.VIEW_PATH.getVariableName()))
                    .orElse("src/main/resources/view.json");

    public static final int PAGE_SIZE =
            Integer.parseInt(Optional.ofNullable(getProperty(ConfigValue.PAGE_SIZE.getVariableName()))
                    .orElse("10"));

    public static final int PAGE_NUMBER =
            Integer.parseInt(Optional.ofNullable(getProperty(ConfigValue.PAGE_NUMBER.getVariableName()))
                    .orElse("1"));

    private Configuration() {

    }


    private static String getProperty(String key) {
        return propertyLoader.getProperty(key);
    }




}
