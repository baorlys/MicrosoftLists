package org.example.microsoftlists.model.microsoft.list.deserializer;

import org.example.microsoftlists.model.microsoft.list.value.ValueFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.example.microsoftlists.model.microsoft.list.value.IValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


public class ValueDeserializer extends JsonDeserializer<IValue> {

    @Override
    public IValue deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode node = mapper.readTree(jsonParser);
        return ValueFactory.create(node.asText());
    }
}
