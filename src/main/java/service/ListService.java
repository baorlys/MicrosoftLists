package service;

import model.constants.ConfigParameter;
import model.microsoftlist.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



public class ListService {

    private ListService() {
        throw new IllegalStateException("Utility class");
    }

    public static long getColumnsCount(MicrosoftList list) {
        return list.getColumns().stream().filter(column -> !column.isHidden()).count();
    }

    public static void addColumn(MicrosoftList list, Column column) {
        Optional<Column> existingColumn = Optional.ofNullable(getColumn(list, column.getName()));
        existingColumn.ifPresentOrElse(
                c -> {
                    throw new IllegalArgumentException("Column already exists");
                },
                () -> list.addColumn(column)
        );
    }

    public static Column getColumn(MicrosoftList list, String name) {
        return list.getColumns().stream()
                .filter(column -> column.getName().equals(name))
                .findFirst().orElse(null);
    }

    public static void addRow(MicrosoftList list) {
        list.addRow();
    }



    public static Row getRow(MicrosoftList list, int index) {
        return list.getRows().get(index);
    }

    public static long getRowsCount(MicrosoftList list) {
        return list.getRows().size();
    }

    public static List<Object> getColConfigValue(MicrosoftList list, String colName, ConfigParameter para) {
        List<Parameter> config = getColConfig(list, colName);
        Objects.requireNonNull(config);
        return config.stream()
                .filter(p -> p.getName().equals(para))
                .findFirst()
                .map(Parameter::getValue)
                .orElse(null);
    }

    public static List<Parameter> getColConfig(MicrosoftList list, String colName) {
        return list.getColumns().stream()
                .filter(col -> col.getName().equals(colName))
                .findFirst()
                .map(col -> col.getType().handle(col.getConfig()))
                .orElse(null);
    }

}
