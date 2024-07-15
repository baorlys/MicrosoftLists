package model.listitem;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ListItem {
    private String name;
    private String description;
    private Color color;
    private String iconId;
    private List<Column> columns;
    private List<Item> items;
    public ListItem() {
        this.columns = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    public void addColumn(Column column) {
        this.columns.add(column);
    }

    public void addItem() {
        Item item = new Item(this);

        for (Column column : this.columns) {
            Cell<Column, Object> cell = new Cell<>(item, column, "");
            item.addCell(cell);
        }

        this.items.add(item);
    }
}
