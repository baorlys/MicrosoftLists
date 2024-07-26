package org.example.microsoftlists.service;

import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.model.microsoft.list.Column;
import org.example.microsoftlists.repository.ColumnRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ColumnService {
    private static final String DIR_PATH = Configuration.DATA_PATH;
    private static final String COLS_PATH = Configuration.COLS_PATH;

    private final ColumnRepository colRepository;

    public ColumnService() {
        this.colRepository = new ColumnRepository(DIR_PATH, COLS_PATH);
    }

    public void saveColumns(List<Column> columns) throws IOException {
        for (Column column : columns) {
            colRepository.save(column);
        }
    }

    public List<Column> findAllOfList(String listId) throws IOException {
        return colRepository.findAllByListId(listId);
    }

    public void deleteAllOfList(String listId) throws IOException {
        List<Column> columns = colRepository.findAllByListId(listId);
        for (Column column : columns) {
            colRepository.delete(column.getId().toString());
        }
    }

    public void deleteColumn(String id) throws IOException {
        colRepository.delete(id);
    }

    public void updateColumn(String id, Column column) throws IOException {
        colRepository.update(id, column);
    }

    public Column findColumnById(String id) throws IOException {
        return colRepository.findById(id);
    }




}
