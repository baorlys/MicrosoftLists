package org.example.microsoftlists.service.file;

import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.model.microsoft.list.MicrosoftList;
import org.example.microsoftlists.model.microsoft.list.Template;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class SaveService {
    private SaveService() {
        throw new IllegalStateException("Utility class");
    }
    private static final String DATA_PATH = Configuration.DATA_PATH;
    private static final String COLS_PATH = Configuration.COLS_PATH;

    private static final String VIEW_PATH = Configuration.VIEW_PATH;
    private static final String ROWS_PATH = Configuration.ROWS_PATH;

    public static void saveStructure(MicrosoftList list) throws IOException {
        String dirPath = DATA_PATH + list.getName();
        File directory = new File(dirPath);
        // Create directories if they do not exist
        boolean isDirCreated = directory.mkdirs();

        // Log if the directory was successfully created
        if (isDirCreated) {
            Logger.getAnonymousLogger().info("Success creating folder");
        }
        JsonService.toJsonFile(list.getColumns(),dirPath, COLS_PATH);

    }

    public static void saveView(MicrosoftList list) throws IOException {
        String dirPath = DATA_PATH + list.getName();
        JsonService.toJsonFile(list.getViews(),dirPath, VIEW_PATH);
    }

    public static void saveData(MicrosoftList list) throws IOException {
        String dirPath = DATA_PATH + list.getName();
        JsonService.toJsonFile(list.getRows(),dirPath, ROWS_PATH);
    }


    public static void saveTemplate(Template template) throws IOException {
        String dirPath = Configuration.DIR_TEMPLATE_PATH;
        JsonService.toJsonFile(template.getColumns(),dirPath, template.getDisplayName());
    }
}
