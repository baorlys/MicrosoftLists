package org.example.microsoftlists.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.example.microsoftlists.model.Row;

import java.io.IOException;


public class RowSerializer extends JsonSerializer<Row> {

    @Override
    public void serialize(Row row, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("typeIdentify", row.getTypeIdentify());
        jsonGenerator.writeStringField("id", row.getId().toString());
        jsonGenerator.writeObjectFieldStart("list");
        jsonGenerator.writeStringField("id", row.getList().getId().toString());
        jsonGenerator.writeStringField("name", row.getList().getName());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeArrayFieldStart("cells");
        row.getCells().forEach(cell -> {
            try {
                jsonGenerator.writeStartObject();
                    jsonGenerator.writeObjectFieldStart("column");
                    jsonGenerator.writeStringField("id", cell.getColumn().getId().toString());
                    jsonGenerator.writeStringField("name", cell.getColumn().getName());
                    jsonGenerator.writeEndObject();
                jsonGenerator.writeStringField("value", cell.getValue().get().toString());
                jsonGenerator.writeEndObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        jsonGenerator.writeEndArray();
        jsonGenerator.writeStringField("createdAt", row.getCreatedAt().toString());
        jsonGenerator.writeEndObject();
    }
}
