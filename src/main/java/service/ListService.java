package service;

import model.microsoftlist.Column;
import model.microsoftlist.Row;
import model.microsoftlist.MicrosoftList;

import java.util.Objects;
import java.util.UUID;


public class ListService {

    private ListService() {
        throw new IllegalStateException("Utility class");
    }

    public static long getColumnsCount(MicrosoftList list) {
        return list.getColumns().stream().filter(column -> !column.isHidden()).count();
    }

    public static void addColumn(MicrosoftList list, Column column) {
        list.addColumn(column);
    }

    public static Column getColumn(MicrosoftList list, String name) {
        return list.getColumns().stream()
                .filter(column -> column.getName().equals(name))
                .findFirst().orElse(null);
    }

    public static void addNewRow(MicrosoftList list) {
        list.addRow();
    }

    public static void updateCellData(MicrosoftList list, Row row, UUID cellId, Object value) {
        Objects.requireNonNull(cellId);
        list.getRows().stream()
                .filter(i -> i.equals(row))
                .findFirst().flatMap(i -> i.getCells().stream()
                        .filter(c -> c.getId().equals(cellId))
                        .findFirst()).ifPresent(c -> c.setValue(value));
    }

    public static Row getRow(MicrosoftList list, int index) {
        return list.getRows().get(index);
    }

    public static long getRowsCount(MicrosoftList list) {
        return list.getRows().size();
    }

    public static String getColumnConfig(MicrosoftList list, String colName) {
        return list.getColumns().stream()
                .filter(col -> col.getName().equals(colName))
                .findFirst().map(column -> column.getType().toJson()).orElse(null);
    }
}
