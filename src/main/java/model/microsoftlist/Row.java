package model.microsoftlist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Row {
    @JsonIgnore
    private MicrosoftList list;
    private Map<String, Object> cells; // Column, Value

    public Row(MicrosoftList list) {
        this.list = list;
        this.cells = new HashMap<>();
    }

    public void addCell(Cell cell) {
        this.cells.put(cell.getColumn().getName(), cell.getValue());
    }

}
