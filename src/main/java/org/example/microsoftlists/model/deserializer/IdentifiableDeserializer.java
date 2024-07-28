package org.example.microsoftlists.model.deserializer;

import org.example.microsoftlists.model.*;
import org.example.microsoftlists.model.constants.IdentifyModel;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.EnumMap;

public class IdentifiableDeserializer extends JsonDeserializer<Identifiable> {
    private static final EnumMap<IdentifyModel, Class<? extends Identifiable>> typeMap = new EnumMap<>(IdentifyModel.class);

    static {
        typeMap.put(IdentifyModel.LIST, MicrosoftList.class);
        typeMap.put(IdentifyModel.COLUMN, Column.class);
        typeMap.put(IdentifyModel.COLUMN_CONFIG, ColumnConfig.class);
        typeMap.put(IdentifyModel.ROW, Row.class);
    }
    @Override
    public Identifiable deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode node = mapper.readTree(jsonParser);

        // Get the type field
        IdentifyModel type = IdentifyModel.valueOf(node.get("typeIdentify").asText());
        Class<? extends Identifiable> clazz = typeMap.get(type);

        if (clazz == null) {
            throw new IllegalArgumentException("Unknown type: " + type);
        }

        // Deserialize into the specific class
        return mapper.treeToValue(node, clazz);
    }
}
