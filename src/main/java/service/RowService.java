package service;

import model.microsoftlist.Cell;
import model.microsoftlist.Column;
import model.microsoftlist.Row;

public class RowService {
    private RowService() {
        throw new IllegalStateException("Utility class");
    }
    public static Cell<Column, Object> getCell(Row item, int index) {
        return item.getCells().get(index);
    }
}
