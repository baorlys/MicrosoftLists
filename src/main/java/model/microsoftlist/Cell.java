package model.microsoftlist;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Cell {
    private Row row;
    private Column column;
    private Object value;

    public Cell(Row row, Column column, Object value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }


    public static Cell of(Row row, Column column, Object value) {
        return new Cell(row, column, value);
    }
}
