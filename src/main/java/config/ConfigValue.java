package config;

public enum ConfigValue {
    CONFIG_FILE("application.properties"), // default config file name
    DATETIME_FORMAT("datetime.format"),
    COLUMN_NAME("column.name"),
    // default values for file name to save columns and directory path
    COLUMNS_PATH("columns.path"),
    DIR_PATH("dir.path"),
    DATA_PATH("data.path"),
    PAGE_SIZE("page.size"),
    PAGE_NUMBER("page.number"), VIEW_PATH("view.path"),
    DIR_TEMPLATE_PATH("dir.template.path");



    private final String variableName;
    ConfigValue(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }
}
