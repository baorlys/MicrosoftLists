package org.example.microsoftlists.service;

import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.dto.request.ColumnRequest;
import org.example.microsoftlists.dto.request.ParaRequest;
import org.example.microsoftlists.model.*;
import org.example.microsoftlists.model.value.SingleObject;
import org.example.microsoftlists.repository.ColumnConfigRepository;
import org.example.microsoftlists.repository.ColumnRepository;
import org.example.microsoftlists.repository.RowRepository;
import org.example.microsoftlists.service.builder.ColumnBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ColumnService {
    private static final String DIR_PATH = Configuration.DATA_PATH;
    private static final String COLS_PATH = Configuration.COLS_PATH;
    private static final String CONFIG_PATH = Configuration.COL_CONFIG_PATH;

    private static final String ROWS_PATH = Configuration.ROWS_PATH;

    private final ColumnRepository colRepository;

    private final ColumnConfigRepository columnConfigRepository;

    private final RowRepository rowRepository;

    public ColumnService() {
        this.colRepository = new ColumnRepository(DIR_PATH, COLS_PATH);
        this.columnConfigRepository = new ColumnConfigRepository(DIR_PATH, CONFIG_PATH);
        this.rowRepository = new RowRepository(DIR_PATH, ROWS_PATH);
    }

    public Column generateColumn(ColumnRequest column) {
        List<ParaRequest> config = column.getConfig();

        List<Parameter> paras = new ArrayList<>();
        for (ParaRequest para : config) {
            paras.add(new Parameter(para.getName(), para.getValue()));
        }

        return new ColumnBuilder(column.getType(), column.getName())
                .setDefaultValue(column.getDefaultValue())
                .configure(paras)
                .build();
    }

    public void save(List<Column> columns) throws IOException {
        for (Column column : columns) {
            save(column);
        }
    }
    public void save(Column column) throws IOException {


        colRepository.save(column);

        List<ColumnConfig> columnConfigs = new ArrayList<>();

        for (Parameter para : column.getConfig()) {
            ColumnConfig columnConfig = new ColumnConfig(column, para);
            columnConfigs.add(columnConfig);
        }

        List<Row> rows = rowRepository.findAllByListId(column.getList().getId().toString());
        for (Row row : rows) {
            Optional<Object> defaultVal = Optional.ofNullable(column.getDefaultValue());
            row.addCell(Cell.of(row, column, new SingleObject(defaultVal.orElse(""))));
            rowRepository.update(row.getId().toString(), row);
        }

        columnConfigRepository.saveAll(columnConfigs);
    }

    public List<Column> findAllOfList(String listId) throws IOException {
        List<Column> columns = colRepository.findAllByListId(listId);

        for (Column column : columns) {
            List<ColumnConfig> columnConfigs = columnConfigRepository.findAllByColumnId(column.getId().toString());
            List<Parameter> parameters = columnConfigs.stream()
                    .map(ColumnConfig::getParameter)
                    .collect(Collectors.toList());
            column.setConfig(parameters);
        }

        return columns;
    }

    public void deleteAllOfList(String listId) throws IOException {
        List<Column> columns = findAllOfList(listId);
        for (Column column : columns) {
            deleteColumn(column.getId().toString());
        }
    }

    public void deleteColumn(String id) throws IOException {
        colRepository.delete(id);

        columnConfigRepository.deleteAllByColumnId(id);

        rowRepository.deleteCellsByColumnId(id);
    }

    public void updateColumn(String id, Column column) throws IOException {
        colRepository.update(id, column);


        columnConfigRepository.deleteAllByColumnId(id);

        List<ColumnConfig> columnConfigs = new ArrayList<>();

        for (Parameter para : column.getConfig()) {
            ColumnConfig columnConfig = new ColumnConfig(column, para);
            columnConfigs.add(columnConfig);
        }

        columnConfigRepository.saveAll(columnConfigs);
    }

    public Column findColumnById(String id) throws IOException {
        Column column = colRepository.findById(id);

        List<ColumnConfig> columnConfigs = columnConfigRepository.findAllByColumnId(id);

        List<Parameter> parameters = columnConfigs.stream()
                .map(ColumnConfig::getParameter)
                .collect(Collectors.toList());

        column.setConfig(parameters);

        return column;
    }



}
