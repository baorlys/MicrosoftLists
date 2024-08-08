package org.example.microsoftlists.view.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.value.IValue;

@Setter
@Getter
public class ConfigResponse {
    private String name;
    private String value;

    public void setValue(IValue value) {
        this.value = value.get();
    }
}
