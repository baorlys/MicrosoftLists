package org.example.microsoftlists.view.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.constants.ViewType;

import java.util.Map;

@Getter
@Setter
public class ViewRequest {
    private String name;
    private ViewType type;
    private Map<String, String> data;
}
