package org.example.microsoftlists.view.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.type.IType;

import java.util.List;

@Getter
@Setter
public class ColumnResponse {
    private String id;
    private String name;
    private ColumnType type;

    private String defaultValue;

    private List<ConfigResponse> configs;

    private boolean isHidden;

    public void setType(IType type) {
        this.type = type.getColumnType();
    }


}
