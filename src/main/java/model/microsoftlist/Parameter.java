package model.microsoftlist;

import lombok.Getter;
import lombok.Setter;
import model.constants.ConfigParameter;

import java.util.List;

@Setter
@Getter
public class Parameter {
    private ConfigParameter name;
    private List<Object> value;

    public Parameter(ConfigParameter name, List<Object> value) {
        this.name = name;
        this.value = value;
    }


    public static Parameter of(ConfigParameter defaultValue, Object... values) {
        return new Parameter(defaultValue, List.of(values));
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "name=" + name +
                ", value=" + value +
                '}';
    }
}
