package org.example.microsoftlists.view.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class RowRequest {
    Map<UUID,Object> values;
}
