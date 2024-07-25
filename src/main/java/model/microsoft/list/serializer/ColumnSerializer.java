package model.microsoft.list.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import model.microsoft.list.Column;

import java.io.IOException;

public class ColumnSerializer extends JsonSerializer<Column> {
    @Override
    public void serialize(Column column, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeString(column.getName());
    }
}
