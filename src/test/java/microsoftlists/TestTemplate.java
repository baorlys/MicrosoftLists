package microsoftlists;

import model.constants.ColumnType;
import model.microsoft.list.Column;
import model.microsoft.list.Template;
import org.junit.jupiter.api.Test;
import service.TemplateService;
import service.builder.ColumnBuilder;
import service.file.SaveService;

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
