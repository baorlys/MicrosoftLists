package org.example.microsoftlists.view.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.constants.ColumnType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ColumnRequest {
    @NotNull
    private String name;
    @NotNull
    private ColumnType type;

    private List<ParaRequest> config = new ArrayList<>(); // Nullable
    private String defaultValue = ""; // Nullable
}
