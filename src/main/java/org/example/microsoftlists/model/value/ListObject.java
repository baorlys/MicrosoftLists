package org.example.microsoftlists.model.value;

import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.config.Configuration;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class ListObject implements IValue {

    private List<String> objects;
    public ListObject() {
        this.objects = new ArrayList<>();
    }
    @Override
    public String get() {
        return String.join(Configuration.DELIMITER, objects);
    }

    @Override
    public void set(String object) {
        this.objects = List.of(object.split((String) Configuration.DELIMITER));
    }

    @Override
    public void set(IValue value) {
        this.objects = List.of(value.get());
    }


}
