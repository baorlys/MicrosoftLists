package service;

import model.microsoft.list.Cell;
import model.microsoft.list.value.IValue;
import model.microsoft.list.Row;

import java.util.List;
import java.util.Map;

public class RowService {
    private RowService() {
        throw new IllegalStateException("Utility class");
    }
    public static Object getCell(Row row, String colName) {

        return row.getCells().stream()
                .filter(cell -> cell.getColumn().getName().equals(colName))
                .findFirst()
                .map(Cell::getValue)
                .orElse(null);
    }

    public static List<Cell> getCells(Row row) {
        return row.getCells();
    }

    public static Row updateCell(Row row, String colName, IValue value) {
        row.getCells().stream()
                .filter(cell -> cell.getColumn().getName().equals(colName))
                .findFirst()
                .ifPresent(cell -> cell.setValue(value));
        return row;
    }
}
