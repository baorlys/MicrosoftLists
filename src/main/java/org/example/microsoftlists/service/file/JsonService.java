package org.example.microsoftlists.service.file;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.microsoftlists.model.Identifiable;
import org.example.microsoftlists.model.deserializer.IdentifiableDeserializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.IOException;

public class JsonService {

    private JsonService() {
        throw new IllegalStateException("Utility class");
    }
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());

        SimpleModule moduleId = new SimpleModule();
        moduleId.addDeserializer(Identifiable.class, new IdentifiableDeserializer());
        objectMapper.registerModule(moduleId);


    }

    public static String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }

    public static void toJsonFile(Object object, String dirPath, String filePath) throws IOException {
        objectMapper.writeValue(new File(dirPath, filePath), object);
    }

    public static <T> T fromJsonFile(String dirPath, String filePath, Class<T> clazz) throws IOException {
        return objectMapper.readValue(new File(dirPath, filePath), clazz);
    }

    public static <T> T fromJsonFile(String dirPath, String filePath, TypeReference<T> typeRef) throws IOException {
        return objectMapper.readValue(new File(dirPath, filePath), typeRef);
    }
}