package org.example.microsoftlists.view.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ListResponse {
    private String id;
    private String name;
    private String description;

    List<ColumnResponse> columns = new ArrayList<>();

    List<RowResponse> rows = new ArrayList<>();

    List<ViewResponse> views = new ArrayList<>();



}
