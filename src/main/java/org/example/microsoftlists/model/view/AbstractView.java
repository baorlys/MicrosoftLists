package org.example.microsoftlists.model.view;

import org.example.microsoftlists.model.MicrosoftList;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.constants.ViewType;

@Getter
@Setter
public class AbstractView {
    @JsonIgnore
    private MicrosoftList list;
    private String name;
    private ViewType type;

    protected AbstractView(String name, ViewType type) {
        this.name = name;
        this.type = type;
    }
}
