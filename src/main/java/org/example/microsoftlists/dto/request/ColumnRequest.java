package org.example.microsoftlists.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.constants.ColumnType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ColumnRequest {
    private String name;
    private ColumnType type;

    private List<ParaRequest> config = new ArrayList<>(); // Nullable
    private String defaultValue = ""; // Nullable
}
