package model.microsoft.list;

import lombok.Getter;
import lombok.Setter;
import model.microsoft.list.value.IValue;


@Getter
@Setter
public class Cell {
    private Row row;
    private Column column;
    private IValue value;

    public Cell(Row row, Column column, IValue value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }


    public static Cell of(Row row, Column column, IValue value) {
        return new Cell(row, column, value);
    }
}
