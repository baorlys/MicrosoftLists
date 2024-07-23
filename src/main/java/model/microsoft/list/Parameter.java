package model.microsoft.list;

import lombok.Getter;
import lombok.Setter;
import model.constants.ConfigParameter;
import model.microsoft.list.value.IValue;
import model.microsoft.list.value.IValueFactory;

@Setter
@Getter
public class Parameter {
    private ConfigParameter name;
    private IValue value;

    public Parameter(ConfigParameter name, IValue value) {
        this.name = name;
        this.value = value;
    }


    public static Parameter of(ConfigParameter defaultValue, Object... value) {
        IValue iValue = IValueFactory.create(value);
        return new Parameter(defaultValue, iValue);
    }

    public Object getValue() {
        return value.get();
    }

    public void setValue(Object... value) {
        this.value = IValueFactory.create(value);
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "name=" + name +
                ", value=" + value +
                '}';
    }
}
