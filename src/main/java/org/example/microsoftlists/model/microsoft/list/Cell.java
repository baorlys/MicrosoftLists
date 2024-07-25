package org.example.microsoftlists.model.microsoft.list;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.microsoftlists.model.microsoft.list.value.IValue;


@Getter
@Setter
@NoArgsConstructor
public class Cell {
    @JsonIgnore
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
