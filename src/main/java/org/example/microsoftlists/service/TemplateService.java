package org.example.microsoftlists.service;

import org.example.microsoftlists.model.Column;
import org.example.microsoftlists.model.Config;
import org.example.microsoftlists.model.Template;
import org.example.microsoftlists.repository.ColumnRepository;
import org.example.microsoftlists.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TemplateService {
    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private ColumnRepository columnRepository;



    public Template create(Template template) {
        templateRepository.save(template);

        return template;
    }



    public Template save(String displayName, List<Column> cols)  {
        Template template = new Template();

        template.setDisplayName(displayName);
        template.setColumns(cloneColumns(cols, template));

        templateRepository.save(template);
        return template;
    }

    private List<Column> cloneColumns(List<Column> cols, Template template) {
        List<Column> colsClone = new ArrayList<>();
        for (Column col : cols) {
            Column clone = col.copy();
            List<Config> configs = new ArrayList<>();
            for (Config config : col.getConfigs()) {
                Config configClone = config.copy(clone);
                configClone.setColumn(clone);
                configs.add(configClone);
            }
            clone.setConfigs(configs);
            clone.setTemplate(template);
            colsClone.add(clone);
        }
        return colsClone;
    }

    public boolean delete(String id) {
        columnRepository.deleteAllByTemplateId(id);
        templateRepository.deleteById(id);
        return true;
    }

    public Template find(String id)  {
        Template template = Optional.of(templateRepository.findById(id)).get().orElse(null);
        CommonService.throwIsNull(template, "Template not found");
        return template;
    }


}
