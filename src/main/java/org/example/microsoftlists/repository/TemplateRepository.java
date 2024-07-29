package org.example.microsoftlists.repository;

import org.example.microsoftlists.model.Template;

public class TemplateRepository extends Repository<Template> {
    public TemplateRepository(String dirPath, String filePath) {
        super(dirPath, filePath);
    }
}
