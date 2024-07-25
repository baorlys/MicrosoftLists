package org.example.microsoftlists.model.microsoft.list;

import org.example.microsoftlists.model.microsoft.list.deserializer.ConfigParameterDeserializer;
import org.example.microsoftlists.model.microsoft.list.deserializer.ValueDeserializer;
import org.example.microsoftlists.model.microsoft.list.serializer.ValueSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.microsoftlists.model.constants.ConfigParameter;
import org.example.microsoftlists.model.microsoft.list.value.IValue;
import org.example.microsoftlists.model.microsoft.list.value.ValueFactory;

@Setter
@Getter
@NoArgsConstructor
public class Parameter {
    @JsonDeserialize(using = ConfigParameterDeserializer.class)
    private ConfigParameter name;

    @JsonDeserialize(using = ValueDeserializer.class)
    @JsonSerialize(using = ValueSerializer.class)
    private IValue value;


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
