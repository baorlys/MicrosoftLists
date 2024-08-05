package org.example.microsoftlists.repository.json;

import org.example.microsoftlists.model.Row;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RowRepository extends Repository<Row> {
    public RowRepository(String dirPath, String filePath) {
        super(dirPath, filePath);
    }

    public void deleteCellsByColumnId(String columnId) throws IOException {
        List<Row> rows = findAll();

        for (Row row : rows) {
            row.getCells().removeIf(cell -> cell.getColumn().getId().equals(UUID.fromString(columnId)));
            update(row.getId().toString(), row);
        }

    }

    public List<Row> findAllByListId(String listId) throws IOException {
        return findAll().stream()
                .filter(row -> row.getList().getId().equals(UUID.fromString(listId)))
                .collect(Collectors.toList());
    }
}
