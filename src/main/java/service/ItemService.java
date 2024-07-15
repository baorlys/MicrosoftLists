package service;

import model.listitem.Cell;
import model.listitem.Column;
import model.listitem.Item;

public class ItemService {
    private ItemService() {
        throw new IllegalStateException("Utility class");
    }
    public static Cell<Column, Object> getCell(Item item, int index) {
        return item.getCells().get(index);
    }
}
