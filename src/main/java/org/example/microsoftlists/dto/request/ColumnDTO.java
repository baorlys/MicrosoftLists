package org.example.microsoftlists.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.constants.ColumnType;

@Getter
@Setter
public class ColumnDTO {
    private String name;
    private ColumnType type;

}
