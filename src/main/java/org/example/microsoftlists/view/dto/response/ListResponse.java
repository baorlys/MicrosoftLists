package org.example.microsoftlists.view.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ListResponse {
    private UUID id;
    private String name;
    private String description;

    List<ColumnResponse> columns;

    List<RowResponse> rows;

}
