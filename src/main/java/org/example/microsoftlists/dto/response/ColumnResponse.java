package org.example.microsoftlists.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.microsoft.list.Column;

@Getter
@Setter
public class ColumnResponse {
    private String id;
    private String name;
    private ColumnType type;

    private Object defaultValue;
    private boolean isRequired;
    private boolean isHidden;
    public ColumnResponse(Column column) {
        this.name = column.getName();
        this.type = column.getType().getColumnType();
    }


}
