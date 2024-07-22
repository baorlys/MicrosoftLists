package service.file;

import config.Configuration;
import model.microsoft.list.MicrosoftList;
import util.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class SaveService {
    private SaveService() {
        throw new IllegalStateException("Utility class");
    }
    private static final String DIR_PATH = Configuration.DIR_PATH;
    private static final String COLS_PATH = Configuration.COLS_PATH;

    private static final String VIEW_PATH = Configuration.VIEW_PATH;
    private static final String DATA_PATH = Configuration.DATA_PATH;

    public static void saveStructure(MicrosoftList list) throws IOException {
        String dirPath = DIR_PATH + list.getName().replace(" ","_");
        File directory = new File(dirPath);
        // Create directories if they do not exist
        boolean isDirCreated = directory.mkdirs();

        // Log if the directory was successfully created
        if (isDirCreated) {
            Logger.getAnonymousLogger().info("Success creating folder");
        }
        JsonUtil.toJsonFile(list.getColumns(),dirPath, COLS_PATH);

    }

    public static void saveView(MicrosoftList list) throws IOException {
        String dirPath = DIR_PATH + list.getName().replace(" ","_");
        JsonUtil.toJsonFile(list.getViews(),dirPath, VIEW_PATH);
    }

    public static void saveData(MicrosoftList list) throws IOException {
        String dirPath = DIR_PATH + list.getName().replace(" ","_");
        JsonUtil.toJsonFile(list.getRows(),dirPath, DATA_PATH);
    }

    public static void saveFilterList(MicrosoftList list) throws IOException {
        String dirPath = DIR_PATH + list.getName().replace(" ","_") + "/filter";
        File directory = new File(dirPath);
        // Create directories if they do not exist
        boolean isDirCreated = directory.mkdirs();

        // Log if the directory was successfully created
        if (isDirCreated) {
            Logger.getAnonymousLogger().info("Success creating folder");
        }
        JsonUtil.toJsonFile(list.getColumns(),dirPath, COLS_PATH);
        JsonUtil.toJsonFile(list.getRows(),dirPath, DATA_PATH);

    }
}
