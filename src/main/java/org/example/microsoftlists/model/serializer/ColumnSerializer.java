package org.example.microsoftlists.model.serializer;

import org.example.microsoftlists.model.Column;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ColumnSerializer extends JsonSerializer<Column> {
    @Override
    public void serialize(Column column, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id",column.getId().toString());
        jsonGenerator.writeEndObject();
    }
}
