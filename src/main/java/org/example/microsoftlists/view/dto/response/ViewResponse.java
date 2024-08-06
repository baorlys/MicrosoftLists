package org.example.microsoftlists.view.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ViewResponse {
    private String id;
    private String name;
    private String type;
    private Map<String, String> data;

}
