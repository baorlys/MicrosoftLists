package org.example.microsoftlists.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;

import com.fasterxml.jackson.databind.SerializerProvider;
import org.example.microsoftlists.model.Template;

import java.io.IOException;


public class TemplateSerializer extends JsonSerializer<Template> {

    @Override
    public void serialize(Template template, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("typeIdentify", template.getTypeIdentify());
        jsonGenerator.writeStringField("id", template.getId().toString());
        jsonGenerator.writeStringField("displayName", template.getDisplayName());
        jsonGenerator.writeArrayFieldStart("columns");
        template.getColumns().forEach(column -> {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("name", column.getName());
                jsonGenerator.writeStringField("type", column.getType().getColumnType().toString());
                jsonGenerator.writeStringField("defaultValue", column.getDefaultValue().toString());
                    jsonGenerator.writeObjectFieldStart("config");
                        column.getConfigs().forEach(parameter -> {
                            try {
                                jsonGenerator.writeStringField("name", parameter.getName().toString());
                                jsonGenerator.writeStringField("value", parameter.getValue().toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    jsonGenerator.writeEndObject();
                jsonGenerator.writeEndObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
