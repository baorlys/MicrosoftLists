package model.microsoft.list.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import model.microsoft.list.type.IType;

import java.io.IOException;

public class TypeSerializer extends JsonSerializer<IType> {
    @Override
    public void serialize(IType iType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(iType.getType().name());
    }
}
