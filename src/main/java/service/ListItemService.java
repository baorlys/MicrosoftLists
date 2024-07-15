package service;

import model.listitem.Column;
import model.listitem.Item;
import model.listitem.ListItem;

import java.util.Objects;
import java.util.UUID;


public class ListItemService {

    private ListItemService() {
        throw new IllegalStateException("Utility class");
    }

    public static long getColumnsCount(ListItem listItem) {
        return listItem.getColumns().stream().filter(column -> !column.isHidden()).count();
    }

    public static void addColumn(ListItem listItem, Column column) {
        listItem.addColumn(column);
    }

    public static Column getColumn(ListItem listItem, int i) {
        return listItem.getColumns().get(i);
    }

    public static void addItem(ListItem listItem) {
        listItem.addItem();
    }

    public static void updateCellData(ListItem listItem, Item item, UUID cellId, Object value) {
        Objects.requireNonNull(cellId);
        listItem.getItems().stream()
                .filter(i -> i.equals(item))
                .findFirst().flatMap(i -> i.getCells().stream()
                        .filter(c -> c.getId().equals(cellId))
                        .findFirst()).ifPresent(c -> c.setValue(value));
    }

    public static Item getItem(ListItem listItem, int index) {
        return listItem.getItems().get(index);
    }
}
