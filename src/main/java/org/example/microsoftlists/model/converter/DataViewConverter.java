package org.example.microsoftlists.model.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
@Converter(autoApply = true)
@Slf4j
public class DataViewConverter implements AttributeConverter<Map<String,String>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(Map<String, String> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert map to json", e);
        }
        return null;
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String s) {
        try {
            return objectMapper.readValue(s, new TypeReference<Map<String, String>>() {});
        } catch (JsonProcessingException e) {
            log.error("Failed to convert json to map", e);
        }
        return Collections.emptyMap();
    }
}
