package service;

import model.microsoftlist.Row;

import java.util.Map;

public class RowService {
    private RowService() {
        throw new IllegalStateException("Utility class");
    }
    public static Object getCell(Row row, String colName) {
        return row.getCells().get(colName);
    }

    public static Map<String, Object> getCells(Row row) {
        return row.getCells();
    }
}
