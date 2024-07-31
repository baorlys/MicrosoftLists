package org.example.microsoftlists.service;

import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.model.value.IValue;
import org.example.microsoftlists.model.value.SingleObject;
import org.example.microsoftlists.model.value.ValueFactory;
import org.example.microsoftlists.repository.ColumnConfigRepository;
import org.example.microsoftlists.repository.ColumnRepository;
import org.example.microsoftlists.repository.RowRepository;
import org.example.microsoftlists.service.builder.ColumnBuilder;
import org.example.microsoftlists.view.dto.MapperUtil;
import org.example.microsoftlists.view.dto.request.ColumnRequest;
import org.example.microsoftlists.view.dto.request.ParaRequest;
import org.example.microsoftlists.view.dto.request.RowRequest;
import org.example.microsoftlists.view.dto.response.CellResponse;
import org.example.microsoftlists.view.dto.response.ColumnResponse;
import org.example.microsoftlists.view.dto.response.ListResponse;
import org.example.microsoftlists.view.dto.response.RowResponse;
import org.example.microsoftlists.model.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class ListService {
    private final MicrosoftListService listsService;

    private final ColumnRepository colRepository;

    private final ColumnConfigRepository columnConfigRepository;

    private final RowRepository rowRepository;
    public ListService() {
        this.listsService = new MicrosoftListService();
        this.colRepository = new ColumnRepository(Configuration.DATA_PATH, Configuration.COLS_PATH);
        this.columnConfigRepository = new ColumnConfigRepository(Configuration.DATA_PATH, Configuration.COL_CONFIG_PATH);
        this.rowRepository = new RowRepository(Configuration.DATA_PATH, Configuration.ROWS_PATH);
    }


    public boolean columnExists(String id, String colName) throws IOException {
        ListResponse list = listsService.findById(id);
        return list.getColumns().stream()
                .anyMatch(existingColumn -> existingColumn.getName().equals(colName));
    }

    public ListResponse createColumn(String id, ColumnRequest column) throws IOException {
        ListResponse list = listsService.findById(id);

        if(columnExists(id, column.getName())) {
            throw new IllegalArgumentException("Column already exists");
        }

        Column col = generateColumn(column);
        MicrosoftList listObj = MapperUtil.mapper.map(list, MicrosoftList.class);
        col.setList(listObj);

        save(col);
        return list;
    }

    public ListResponse updateColumn(String id, String columnId, ColumnRequest column) throws IOException {
        Column col = findColumnById(columnId);
        ListResponse list = listsService.findById(id);

        Column updatedCol = generateColumn(column);

        updatedCol.setId(UUID.fromString(columnId));
        updatedCol.setList(col.getList());

        updateColumn(columnId, updatedCol);

        return list;

    }

    public ListResponse deleteColumn(String id, String columnId) throws IOException {
        deleteColumn(columnId);

        return listsService.findById(id);
    }


    public ListResponse createRow(String id) throws IOException {
        return createRow(id, null);
    }

    public ListResponse createRow(String id, RowRequest rowRequest) throws IOException {
        ListResponse list = listsService.findById(id);
        List<RowResponse> rows = list.getRows();

        Row row = generateRow(list, rowRequest);
        save(row);

        List<CellResponse> cells = row.getCells().stream()
                .map(c -> MapperUtil.mapper.map(c, CellResponse.class))
                .collect(Collectors.toList());

        RowResponse rowRes = new RowResponse();
        rowRes.setId(row.getId().toString());
        rowRes.setCells(cells);

        rows.add(rowRes);

        list.setRows(rows);
        return list;
    }
    public ListResponse updateRow(String id, String rowId, String columnId, Object value) throws IOException {
        updateCell(rowId, columnId, value);
        return listsService.findById(id);
    }


    public ListResponse deleteRow(String id, String rowId) throws IOException {
        deleteRow(rowId);
        return listsService.findById(id);
    }

    public Object getValue(RowResponse rowRes, String columnId) {
        return rowRes.getCells().stream()
                .filter(cell -> cell.getColumnId().equals(columnId))
                .findFirst()
                .map(CellResponse::getValue)
                .orElse(null);
    }


    //region Column
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

    public void deleteAllColumns(String listId) throws IOException {
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
    //endregion


    //region Row

    public Row generateRow(ListResponse list, RowRequest rowRequest) {
        List<ColumnResponse> columns = list.getColumns();

        Row row = new Row();

        row.setList(MicrosoftList.of(list));

        for (ColumnResponse column : columns) {
            Optional<Object> value = Optional.ofNullable(rowRequest.getValues().get(column.getId()));
            Optional<Object> defaultVal = Optional.ofNullable(column.getDefaultValue());

            row.addCell(Cell.of(row, Column.of(column) , new SingleObject(value.orElse(defaultVal.orElse("")))));
        }

        return row;
    }

    public void save(Row row) throws IOException {
        rowRepository.save(row);
    }

    public Row findRowById(String rowId) throws IOException {
        return rowRepository.findById(rowId);
    }


    public void updateCell(String rowId, String columnId, Object value) throws IOException {
        Row row = findRowById(rowId);

        List<Cell> cells = row.getCells();

        cells.stream()
                .filter(cell -> cell.getColumn().getId().equals(UUID.fromString(columnId)))
                .findFirst()
                .ifPresent(cell -> {
                    IValue val = ValueFactory.create(value);
                    cell.setValue(val);
                });

        row.setCells(cells);

        rowRepository.update(rowId, row);

    }

    public void deleteRow(String rowId) throws IOException {
        rowRepository.delete(rowId);
    }
    //endregion
}


