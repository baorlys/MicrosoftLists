package org.example.microsoftlists.view.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.Config;
import org.example.microsoftlists.model.type.IType;

import java.util.List;

@Getter
@Setter
public class ColumnResponse {
    private String id;
    private String name;
    private IType type;

    private String defaultValue;

    private List<Config> configs;

    private boolean isHidden;



}
