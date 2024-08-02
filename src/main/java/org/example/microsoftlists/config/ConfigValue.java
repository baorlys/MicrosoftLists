package org.example.microsoftlists.config;

public enum ConfigValue {
    CONFIG_FILE("application.properties"), // default config file name
    DATETIME_FORMAT("datetime.format"),
    COLUMN_NAME("column.name"),
    // default values for file name to save columns and directory path
    PAGE_SIZE("page.size"),
    PAGE_NUMBER("page.number"),

    //region file paths
    DATA_PATH("data.path"),
    LISTS_PATH("lists.path"),
    COL_CONFIG_PATH("column.config.path"),

    COLUMNS_PATH("columns.path"),
    ROWS_PATH("rows.path"),
    VIEWS_PATH("views.path"),
    TEMPLATES_PATH("templates.path"),
    //endregion

    DELIMITER("delimiter"); // default delimiter


    private final String variableName;
    ConfigValue(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }
}
