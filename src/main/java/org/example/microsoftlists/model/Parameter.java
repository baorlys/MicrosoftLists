package org.example.microsoftlists.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.microsoftlists.model.constants.ConfigParameter;
import org.example.microsoftlists.model.value.IValue;
import org.example.microsoftlists.model.value.ValueFactory;

@Setter
@Getter
@NoArgsConstructor
public class Parameter {
    private ConfigParameter name;

    private IValue value;

    public Parameter(ConfigParameter name, IValue value) {
        this.name = name;
        this.value = value;
    }


    public Parameter(ConfigParameter name, Object... value) {
        this.name = name;
        this.value = ValueFactory.create(value);
    }


    public static Parameter of(ConfigParameter defaultValue, Object... value) {
        return new Parameter(defaultValue, value);
    }

    public Object getValue() {
        return value.get();
    }

    public void setValue(Object... value) {
        this.value = ValueFactory.create(value);
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "name=" + name +
                ", value=" + value +
                '}';
    }
}
