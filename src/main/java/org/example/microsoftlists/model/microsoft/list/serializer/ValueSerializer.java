package org.example.microsoftlists.model.microsoft.list.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.example.microsoftlists.model.microsoft.list.value.IValue;

import java.io.IOException;

public class ValueSerializer extends JsonSerializer<IValue> {
    @Override
    public void serialize(IValue iValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(iValue.get());
    }
}
