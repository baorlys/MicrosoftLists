package org.example.microsoftlists.model.serializer;

import org.example.microsoftlists.model.MicrosoftList;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class MicrosoftListSerializer extends JsonSerializer<MicrosoftList> {
    @Override
    public void serialize(MicrosoftList microsoftList, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", microsoftList.getId().toString());
        jsonGenerator.writeStringField("name", microsoftList.getName());
        jsonGenerator.writeEndObject();
    }
}
