package org.example.microsoftlists.view.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.Cell;

@Getter
@Setter
public class CellResponse {
    private String columnId;
    private Object value;

    public CellResponse(Cell cell) {
        this.columnId = cell.getColumn().getId().toString();
        this.value = cell.getValue().get();
    }
}
