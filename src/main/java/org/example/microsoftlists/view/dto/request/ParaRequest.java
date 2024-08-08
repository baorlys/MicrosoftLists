package org.example.microsoftlists.view.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.constants.ConfigParameter;


@Getter
@Setter
@AllArgsConstructor
public class ParaRequest {
    @NotNull
    ConfigParameter name;
    @NotNull
    String value;
}
