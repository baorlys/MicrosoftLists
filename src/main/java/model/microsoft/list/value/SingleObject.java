package model.microsoft.list.value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SingleObject implements IValue{
    private Object object;

    @JsonIgnore
    private static final boolean IS_MULTIPLE = false;

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
    public boolean isMultiple() {
        return IS_MULTIPLE;
    }
}
