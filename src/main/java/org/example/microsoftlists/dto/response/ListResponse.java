package org.example.microsoftlists.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.microsoft.list.Column;
import org.example.microsoftlists.model.microsoft.list.MicrosoftList;

import java.util.List;

@Getter
@Setter
public class ListResponse {
    private String id;
    private String name;
    private String description;

    List<Column> columns;

    public ListResponse(MicrosoftList list) {
        this.id = list.getId().toString();
        this.name = list.getName();
        this.description = list.getDescription();
    }
}
