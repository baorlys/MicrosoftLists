package org.example.microsoftlists.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.example.microsoftlists.model.value.IValue;

import java.io.IOException;

public class ValueSerializer extends JsonSerializer<IValue> {
    @Override
    public void serialize(IValue iValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(iValue.get());
    }
}
