package service.file;

import config.Configuration;
import model.microsoftlist.MicrosoftList;
import util.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class SaveService {
    private SaveService() {
        throw new IllegalStateException("Utility class");
    }

    public static void saveStructure(MicrosoftList list) throws IOException {
        String dirPath = Configuration.DIR_PATH + list.getName().replace(" ","_");
        String filePath = Configuration.COLS_PATH ;
        File directory = new File(dirPath);
        // Create directories if they do not exist
        boolean isDirCreated = directory.mkdirs();

        // Log if the directory was successfully created
        if (isDirCreated) {
            Logger.getAnonymousLogger().info("Success creating folder");
        }
        JsonUtil.toJsonFile(list.getColumns(),dirPath,filePath);
    }

    public static void saveData(MicrosoftList list) throws IOException {
        String dirPath = Configuration.DIR_PATH + list.getName().replace(" ","_");
        String filePath = Configuration.DATA_PATH;
        JsonUtil.toJsonFile(list.getRows(),dirPath,filePath);
    }
}
