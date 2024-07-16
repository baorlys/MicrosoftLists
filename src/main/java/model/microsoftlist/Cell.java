package model.microsoftlist;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Cell<T, K> {
    private UUID id;
    private Row item;
    private T column;
    private K value;

    public Cell(Row item, T column, K value) {
        this.id = UUID.randomUUID();
        this.item = item;
        this.column = column;
        this.value = value;
    }


}
