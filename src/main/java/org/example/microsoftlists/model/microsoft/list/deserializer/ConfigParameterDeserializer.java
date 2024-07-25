package org.example.microsoftlists.model.microsoft.list.deserializer;

import org.example.microsoftlists.model.constants.ConfigParameter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class ConfigParameterDeserializer extends JsonDeserializer<ConfigParameter>  {
    @Override
    public ConfigParameter deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return ConfigParameter.valueOf(jsonParser.getText());
    }
}
