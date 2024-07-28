package org.example.microsoftlists.model.value;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SingleObject implements IValue {
    private Object object;


    public SingleObject(Object object) {
        this.object = object;
    }
    public SingleObject() {
        this.object = "";
    }

    @Override
    public Object get() {
        return object;
    }

    @Override
    public void set(Object... object) {
        this.object = object[0];
    }

    @Override
    public void set(IValue value) {
        this.object = value.get();
    }


}
