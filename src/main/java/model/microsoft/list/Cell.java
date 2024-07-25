package model.microsoft.list;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.microsoft.list.deserializer.ValueDeserializer;
import model.microsoft.list.serializer.ColumnSerializer;
import model.microsoft.list.serializer.ValueSerializer;
import model.microsoft.list.value.IValue;
import model.microsoft.list.value.ValueFactory;


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
