package model.microsoft.list.value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class ListObject implements IValue {
    private List<Object> objects;
    @JsonIgnore
    private static final boolean IS_MULTIPLE = true;
    public ListObject() {
        this.objects = new ArrayList<>();
    }
    public ListObject(Object... objects) {
        this.objects = List.of(objects);
    }

    @Override
    public List<Object> get() {
        return objects;
    }

    @Override
    public void set(Object... object) {
        this.objects = List.of(object);
    }

    @Override
    public boolean isMultiple() {
        return IS_MULTIPLE;
    }

}
