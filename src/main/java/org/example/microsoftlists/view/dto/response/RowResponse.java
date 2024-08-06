package org.example.microsoftlists.view.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RowResponse {
    private String id;
    private List<CellResponse> cells = new ArrayList<>();

}
