package org.example.microsoftlists.view.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.Column;
import org.example.microsoftlists.model.value.IValue;


@Getter
@Setter
public class CellResponse {
    private String column;
    private String value;


    public void setColumn(Column column) {
        this.column = column.getName();
    }

    public void setValue(IValue value) {
        this.value = value.get();
    }

}
