package model.microsoft.list.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import model.microsoft.list.Cell;
import model.microsoft.list.value.IValue;
import model.microsoft.list.value.SingleObject;
import model.microsoft.list.value.ValueFactory;

import java.io.IOException;
import java.util.List;

public class ValueDeserializer extends JsonDeserializer<IValue> {

    @Override
    public IValue deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        var cell = jsonParser.readValueAsTree();
        return new SingleObject();
    }
}
