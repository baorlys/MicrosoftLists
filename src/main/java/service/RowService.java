package service;

import model.microsoft.list.value.IValue;
import model.microsoft.list.Row;

import java.util.Map;

public class RowService {
    private RowService() {
        throw new IllegalStateException("Utility class");
    }
    public static Object getCell(Row row, String colName) {
        return row.getCells().get(colName).get();
    }

    public static Map<String, IValue> getCells(Row row) {
        return row.getCells();
    }

    public static Row updateCell(Row row, String colName, IValue value) {
        row.getCells().put(colName, value);
        return row;
    }
}
