package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

public class JsonUtil {

    private JsonUtil() {
        throw new IllegalStateException("Utility class");
    }
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }

    public static void toJsonFile(Object object, String dirPath, String filePath) throws IOException {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(dirPath, filePath), object);
    }

    public static <T> T fromJsonFile(String dirPath, String filePath, Class<T> clazz) throws IOException {
        return objectMapper.readValue(new File(dirPath, filePath), clazz);
    }
}