package model.microsoftlist;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MicrosoftList {
    private String name;
    private String description;
    private Color color;
    private String iconId;
    private List<Column> columns;
    private List<Row> rows;
    public MicrosoftList() {
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
    }

    public void addColumn(Column column) {
        this.columns.add(column);
    }

    public void addRow() {
        Row row = new Row(this);

        for (Column column : this.columns) {
            Cell<Column, Object> cell = new Cell<>(row, column, "");
            row.addCell(cell);
        }

        this.rows.add(row);
    }
}
