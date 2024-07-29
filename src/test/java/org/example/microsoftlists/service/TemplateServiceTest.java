package org.example.microsoftlists.service;

import org.example.microsoftlists.model.Column;
import org.example.microsoftlists.model.Parameter;
import org.example.microsoftlists.model.Template;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.service.builder.ColumnBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;


class TemplateServiceTest {
    private TemplateService templateService;
    private final String dirPath = "src/test/mock_data";
    private final String templatesPath = "templates.json";
    @BeforeEach
    void setUp() {
        templateService = new TemplateService(dirPath, templatesPath);
    }

    @Test
    void loadTemplates() throws IOException {
        List<Template> templates = templateService.loadTemplates();
        assert (templates.size() == 2);

    }

    @Test
    void create() {
        Template template = new Template();
        template.setDisplayName("Test 1");
        Column column = new ColumnBuilder(ColumnType.TEXT, "Test Column 1")
                .build();

    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void find() {
    }

    @Test
    void findByName() {
    }
}