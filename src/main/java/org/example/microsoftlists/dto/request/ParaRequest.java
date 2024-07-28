package org.example.microsoftlists.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.constants.ConfigParameter;


@Getter
@Setter
public class ParaRequest {
    ConfigParameter name;
    Object value;
}
