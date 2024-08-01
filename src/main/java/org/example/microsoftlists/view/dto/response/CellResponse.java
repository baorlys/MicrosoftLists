package org.example.microsoftlists.view.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.Column;
import org.example.microsoftlists.model.value.IValue;

import java.util.UUID;

@Getter
@Setter
public class CellResponse {
    private UUID column;
    private IValue value;


    public void setColumn(Column column) {
        this.column = column.getId();
    }

}
