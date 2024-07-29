package org.example.microsoftlists.service;

import org.example.microsoftlists.model.Column;
import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.model.Template;
import org.example.microsoftlists.repository.TemplateRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TemplateService {
    private static final String DIR_PATH = Configuration.DATA_PATH;
    private static final String TEMPLATES_PATH = Configuration.TEMPLATES_PATH;
    private final TemplateRepository templateRepository;

    public TemplateService() {
        this.templateRepository = new TemplateRepository(DIR_PATH,TEMPLATES_PATH);
    }

    public TemplateService(String dirPath, String templatesPath) {
        this.templateRepository = new TemplateRepository(dirPath,templatesPath);
    }

    public List<Template> loadTemplates() throws IOException {
        List<Template> templates = templateRepository.findAll();

        for (Template template : templates) {
            List<Column> columns = template.getColumns();
            template.setColumns(columns);
        }

        return templates;
    }

    public Template create(Template template) throws IOException {
        templateRepository.save(template);

        return template;
    }

    public boolean delete(String id) throws IOException {
        templateRepository.delete(id);
        return true;
    }

    public void update(String id, Template template) throws IOException {
        templateRepository.update(id, template);
    }

    public Template find(String id) throws IOException {
        return templateRepository.findById(id);
    }

    public List<Template> findByName(String name) throws IOException {
        List<Template> templates = templateRepository.findAll();
        List<Template> result = new ArrayList<>();
        for (Template template : templates) {
            if (template.getDisplayName().equals(name)) {
                result.add(template);
            }
        }
        return result;
    }


}
