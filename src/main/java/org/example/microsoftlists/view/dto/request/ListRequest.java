package org.example.microsoftlists.view.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListRequest {
    @NotNull
    private String name;
    private String description;

}
