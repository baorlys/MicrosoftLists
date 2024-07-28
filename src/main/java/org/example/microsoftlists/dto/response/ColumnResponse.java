package org.example.microsoftlists.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.Parameter;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.Column;

import java.util.List;

@Getter
@Setter
public class ColumnResponse {
    private String id;
    private String name;
    private ColumnType type;

    private Object defaultValue;

    private List<Parameter> configs;

    private boolean isHidden;
    public ColumnResponse(Column column) {
        this.id = column.getId().toString();
        this.name = column.getName();
        this.type = column.getType().getColumnType();
        this.defaultValue = column.getDefaultValue();
        this.isHidden = column.isHidden();
        this.configs = column.getConfig();
    }


}
