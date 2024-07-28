package org.example.microsoftlists.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.Row;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RowResponse {
    private String id;
    private List<CellResponse> cells = new ArrayList<>();

    public RowResponse(Row row) {
        this.id = row.getId().toString();
        row.getCells().forEach(cell -> cells.add(new CellResponse(cell)));
    }
}
