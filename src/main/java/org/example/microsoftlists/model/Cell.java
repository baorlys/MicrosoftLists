package org.example.microsoftlists.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.microsoftlists.model.deserializer.ValueDeserializer;
import org.example.microsoftlists.model.value.IValue;


@Getter
@Setter
@NoArgsConstructor
public class Cell {
    @JsonIgnore
    private Row row;

    private Column column;


    @JsonDeserialize(using = ValueDeserializer.class)
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
