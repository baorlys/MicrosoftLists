package org.example.microsoftlists.view.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class RowRequest {
    Map<String,Object> values;
}
