package service;

import config.Configuration;
import model.microsoft.list.Column;
import model.microsoft.list.Template;
import service.file.OpenService;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TemplateService {
    private static final String DIR_TEMPLATE_PATH = Configuration.DIR_TEMPLATE_PATH;
    private static List<Template> templates = new ArrayList<>();

    public TemplateService() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(DIR_TEMPLATE_PATH))) {
            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    String fileName = entry.getFileName().toString();
                    List<Column> columns = OpenService.openFile(DIR_TEMPLATE_PATH, fileName, List.class);
                    Template template = new Template(columns, fileName);
                    templates.add(template);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Template> getTemplates() {
        return templates;
    }
}
