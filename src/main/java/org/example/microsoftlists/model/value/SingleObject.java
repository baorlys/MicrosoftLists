package org.example.microsoftlists.model.value;

import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.config.Configuration;


@Getter
@Setter
public class SingleObject implements IValue {
    private String object;


    public SingleObject(String object) {
        this.object = object;
    }
    public SingleObject() {
        this.object = "";
    }

    @Override
    public String get() {
        return object;
    }

    @Override
    public void set(String object) {
        this.object = object.split((String) Configuration.DELIMITER)[0];
    }

    @Override
    public void set(IValue value) {
        this.object = value.get();
    }


}
