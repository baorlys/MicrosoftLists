package org.example.microsoftlists.view.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.MicrosoftList;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ListResponse {
    private String id;
    private String name;
    private String description;

    List<ColumnResponse> columns;

    List<RowResponse> rows;

    public ListResponse(MicrosoftList list) {
        this.id = list.getId().toString();
        this.name = list.getName();
        this.description = list.getDescription();
        this.rows = list.getRows().stream().map(RowResponse::new).collect(Collectors.toList());
        this.columns = list.getColumns().stream().map(ColumnResponse::new).collect(Collectors.toList());
    }
}
