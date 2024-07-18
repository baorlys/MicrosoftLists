package service.file;

import util.JsonUtil;

import java.io.IOException;

public class OpenService {
    private OpenService() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> T openFile(String dirPath, String filePath, Class<T> clazz) throws IOException {
        return JsonUtil.fromJsonFile(dirPath, filePath, clazz);
    }
}
