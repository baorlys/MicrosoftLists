package model.microsoft.list;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import model.microsoft.list.value.IValue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Row {
    @JsonIgnore
    private MicrosoftList list;
    private Map<String, IValue> cells; // Column, Value

    private Date createdAt;


    public Row(MicrosoftList list) {
        this.list = list;
        this.cells = new HashMap<>();
        this.createdAt = new Date();
    }

    public void addCell(Cell cell) {
        this.cells.put(cell.getColumn().getName(), cell.getValue());
    }

}
