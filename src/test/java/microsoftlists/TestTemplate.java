package microsoftlists;

import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.microsoft.list.Column;
import org.example.microsoftlists.model.microsoft.list.Template;
import org.junit.jupiter.api.Test;
import org.example.microsoftlists.service.TemplateService;
import org.example.microsoftlists.service.builder.ColumnBuilder;
import org.example.microsoftlists.service.file.SaveService;

import java.io.IOException;
import java.util.List;

class TestTemplate {
    @Test
    void loadTemplates() {
        // Load all templates
        TemplateService templateService = new TemplateService();
    }

    @Test
    void saveTemplate() throws IOException {
        Template template = new Template("template-4");
        Column column = new ColumnBuilder(ColumnType.TEXT,"name").build();
        template.setColumns(List.of(column));
        SaveService.saveTemplate(template);
    }
}
