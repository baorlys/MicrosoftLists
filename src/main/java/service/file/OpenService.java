package service.file;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;

public class OpenService {
    private OpenService() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> T openFile(String dirPath, String filePath, Class<T> clazz) throws IOException {
        return JsonService.fromJsonFile(dirPath, filePath, clazz);
    }

    public static <T> T openFile(String dirPath, String filePath, TypeReference<T> typeRef) throws IOException {
        return JsonService.fromJsonFile(dirPath, filePath, typeRef);
    }
}
