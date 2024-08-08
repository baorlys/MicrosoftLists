package org.example.microsoftlists.model;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.microsoftlists.model.converter.IValueConverter;
import org.example.microsoftlists.model.value.IValue;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cell {
    @Id
    private String id;
    @ManyToOne
    private Row row;

    @ManyToOne
    private Column column;

    @Convert(converter  = IValueConverter.class)
    private IValue value;

    public Cell(Row row, Column column, IValue value) {
        this.id = UUID.randomUUID().toString();
        this.row = row;
        this.column = column;
        this.value = value;
    }


    public static Cell of(Row row, Column column, IValue value) {
        return new Cell(row, column, value);
    }
}
