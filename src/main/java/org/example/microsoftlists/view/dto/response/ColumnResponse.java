package org.example.microsoftlists.view.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.Parameter;
import org.example.microsoftlists.model.type.IType;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ColumnResponse {
    private UUID id;
    private String name;
    private IType type;

    private String defaultValue;

    private List<Parameter> configs;

    private boolean isHidden;



}
