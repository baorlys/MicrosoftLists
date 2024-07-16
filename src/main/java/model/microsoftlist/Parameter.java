package model.microsoftlist;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Parameter {
    private String name;
    private Object value;

    public Parameter(String name, Object value) {
        this.name = name;
        this.value = value;
    }



}
