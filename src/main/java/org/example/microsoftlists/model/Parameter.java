package org.example.microsoftlists.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.microsoftlists.model.constants.ConfigParameter;
import org.example.microsoftlists.model.deserializer.ValueDeserializer;
import org.example.microsoftlists.model.serializer.ValueSerializer;
import org.example.microsoftlists.model.value.IValue;
import org.example.microsoftlists.model.value.ValueFactory;

@Setter
@Getter
@NoArgsConstructor
public class Parameter {
    private ConfigParameter name;

    @JsonSerialize(using = ValueSerializer.class)
    @JsonDeserialize(using = ValueDeserializer.class)
    private IValue value;

    public Parameter(ConfigParameter name, IValue value) {
        this.name = name;
        this.value = value;
    }


    public Parameter(ConfigParameter name, String value) {
        this.name = name;
        this.value = ValueFactory.create(value);
    }



    public void setValue(String value) {
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
