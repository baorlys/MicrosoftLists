package org.example.microsoftlists.repository.json;

import org.example.microsoftlists.model.ColumnConfig;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnConfigRepository extends Repository<ColumnConfig> {
    public ColumnConfigRepository(String dirPath, String filePath) {
        super(dirPath, filePath);
    }

    public void deleteAllByColumnId(String columnId) throws IOException {
        List<ColumnConfig> configs = findAllByColumnId(columnId);

        for (ColumnConfig config : configs) {
            delete(config.getId().toString());
        }

    }

    public List<ColumnConfig> findAllByColumnId(String columnId) throws IOException {
        List<ColumnConfig> configs = findAll();
        return configs.stream()
                .filter(c -> c.getColumn().getId().toString().equals(columnId))
                .collect(Collectors.toList());
    }
}
