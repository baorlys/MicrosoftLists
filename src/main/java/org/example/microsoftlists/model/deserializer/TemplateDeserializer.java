package org.example.microsoftlists.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.microsoftlists.model.Column;
import org.example.microsoftlists.model.Parameter;
import org.example.microsoftlists.model.Template;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.constants.ConfigParameter;
import org.example.microsoftlists.service.builder.ColumnBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TemplateDeserializer extends JsonDeserializer<Template>  {
    @Override
    public Template deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode rootNode = mapper.readTree(jsonParser);

        // Parse the Template fields
        String id = rootNode.get("id").asText();
        String displayName = rootNode.get("displayName").asText();
        // Parse columns
        List<Column> columns = new ArrayList<>();
        JsonNode columnsNode = rootNode.get("columns");
        if (columnsNode.isArray()) {
            for (JsonNode columnNode : columnsNode) {
                String name = columnNode.get("name").asText();
                String type = columnNode.get("type").asText();

                // Parse configs
                List<Parameter> configs = new ArrayList<>();
                JsonNode configsNode = columnNode.get("configs");
                if (configsNode.isArray()) {
                    for (JsonNode configNode : configsNode) {
                        String configName = configNode.get("name").asText();
                        String value = configNode.get("value").asText();
                        configs.add(new Parameter(ConfigParameter.valueOf(configName), value));
                    }
                }

                columns.add(new ColumnBuilder(ColumnType.valueOf(type), name)
                        .configure(configs)
                        .build());
            }
        }

        return new Template(UUID.fromString(id), displayName, columns);

    }
}
