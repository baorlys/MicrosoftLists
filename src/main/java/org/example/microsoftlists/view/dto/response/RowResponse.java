package org.example.microsoftlists.view.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class RowResponse {
    private UUID id;
    private List<CellResponse> cells = new ArrayList<>();

}
