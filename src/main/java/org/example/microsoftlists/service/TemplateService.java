package org.example.microsoftlists.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.microsoftlists.model.Column;
import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.model.Template;
import org.example.microsoftlists.service.file.OpenService;

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

    private TemplateService() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(DIR_TEMPLATE_PATH))) {
            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    String fileName = entry.getFileName().toString();
                    List<Column> columns = OpenService.openFile(DIR_TEMPLATE_PATH, fileName, new TypeReference<List<Column>>() {
                    });
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
