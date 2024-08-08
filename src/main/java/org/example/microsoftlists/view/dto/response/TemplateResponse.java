package org.example.microsoftlists.view.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TemplateResponse {
    private String id;
    private String displayName;
    private List<ColumnResponse> columns;

}
