package org.example.microsoftlists.service;

import org.example.microsoftlists.model.Column;
import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.model.Template;
import org.example.microsoftlists.repository.json.TemplateRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TemplateService {
    private final TemplateRepository templateRepository;

    public TemplateService() {
        this.templateRepository = new TemplateRepository(Configuration.DATA_PATH,Configuration.TEMPLATES_PATH);
    }



    public Template create(Template template) throws IOException {
        templateRepository.save(template);

        return template;
    }



    public Template save(String displayName, List<Column> cols) throws IOException {
        Template template = new Template();

        template.setDisplayName(displayName);
        template.setColumns(cloneColumns(cols));

        templateRepository.save(template);
        return template;
    }

    private List<Column> cloneColumns(List<Column> cols)  {
        List<Column> colsClone = new ArrayList<>();
        for (Column col : cols) {
            Column clone = col.copy();
            colsClone.add(clone);
        }
        return colsClone;
    }

    public boolean delete(String id) throws IOException {
        templateRepository.delete(id);
        return true;
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
