package org.example.microsoftlists.model.deserializer;

import org.example.microsoftlists.model.value.ValueFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.example.microsoftlists.model.value.IValue;

import java.io.IOException;


public class ValueDeserializer extends JsonDeserializer<IValue> {

    @Override
    public IValue deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return ValueFactory.create(jsonParser.getText());
    }
}
