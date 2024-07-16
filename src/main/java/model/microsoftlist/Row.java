package model.microsoftlist;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Row {
    private MicrosoftList list;
    private List<Cell<Column, Object>> cells;

    public Row(MicrosoftList list) {
        this.list = list;
        this.cells = new ArrayList<>();
    }

    public void addCell(Cell<Column, Object> cell) {
        this.cells.add(cell);
    }

}
