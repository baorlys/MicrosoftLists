package model.listitem;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Item {
    private ListItem listItem;
    private List<Cell<Column, Object>> cells;

    public Item(ListItem listItem) {
        this.listItem = listItem;
        this.cells = new ArrayList<>();
    }

    public void addCell(Cell<Column, Object> cell) {
        this.cells.add(cell);
    }

}
