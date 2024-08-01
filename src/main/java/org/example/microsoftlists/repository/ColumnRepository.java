package org.example.microsoftlists.repository;

import org.example.microsoftlists.model.Column;


import java.io.IOException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ColumnRepository extends Repository<Column> {
    public ColumnRepository(String dirPath, String filePath) {
        super(dirPath, filePath);
    }

    public List<Column> findAllByListId(String listId) throws IOException {
        List<Column> columns = findAll();
        columns = columns.stream()
                .filter(c -> Objects.equals(Optional.ofNullable(c.getList())
                        .map(t -> t.getId().toString())
                        .orElse(null), listId))
                .collect(Collectors.toList());
        return columns;
    }

    public void deleteAllOfList(String listId) throws IOException {
        List<Column> columns = findAll();
        columns = columns.stream()
                .filter(c -> Objects.equals(Optional.ofNullable(c.getList())
                        .map(t -> t.getId().toString())
                        .orElse(null), listId))
                .collect(Collectors.toList());
        for (Column column : columns) {
            delete(column.getId().toString());
        }
    }
}
