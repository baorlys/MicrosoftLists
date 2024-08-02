package org.example.microsoftlists.service;

import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.exception.NameExistsException;
import org.example.microsoftlists.exception.InvalidValueException;
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

    private final ColumnConfigRepository colConfigRepository;

    private final RowRepository rowRepository;
    public ListService() {
        this.listsService = new MicrosoftListService();
        this.colRepository = new ColumnRepository(Configuration.DATA_PATH, Configuration.COLS_PATH);
        this.colConfigRepository = new ColumnConfigRepository(Configuration.DATA_PATH, Configuration.COL_CONFIG_PATH);
        this.rowRepository = new RowRepository(Configuration.DATA_PATH, Configuration.ROWS_PATH);
    }

    public boolean isColumnExists(String id, String colName) throws IOException {
        ListResponse list = listsService.findById(id);
        return list.getColumns().stream()
                .anyMatch(column -> column.getName().equals(colName));
    }


    public ListResponse createColumn(String id, ColumnRequest column) throws IOException, NameExistsException {
        ListResponse list = listsService.findById(id);

        boolean isExists = isColumnExists(id, column.getName());
        CommonService.throwIsExists(isExists, "Column name already exists");

        Column col = generateColumn(column);
        MicrosoftList listObj = MapperUtil.mapper.map(list, MicrosoftList.class);
        listObj.setId(UUID.fromString(id));
        col.setList(listObj);

        save(col);

        return listsService.findById(id);
    }

    public ListResponse updateColumn(String id, String columnId, ColumnRequest column) throws IOException {
        Column col = findColumnById(columnId);

        Column updatedCol = generateColumn(column);

        updatedCol.setId(UUID.fromString(columnId));
        updatedCol.setList(col.getList());

        updateColumn(columnId, updatedCol);

        return listsService.findById(id);

    }

    public ListResponse deleteColumn(String id, String columnId) throws IOException {
        deleteColumn(columnId);

        return listsService.findById(id);
    }


    public ListResponse createRow(String id) throws IOException, InvalidValueException {
        return createRow(id, null);
    }

    public ListResponse createRow(String id, RowRequest rowRequest) throws IOException, InvalidValueException {
        ListResponse list = listsService.findById(id);
        Row row = generateRow(list, rowRequest);
        save(row);
        return listsService.findById(id);
    }
    public ListResponse updateRow(String id, String rowId, String columnId, String value)
            throws IOException, InvalidValueException {
        updateCell(rowId, columnId, value);
        return listsService.findById(id);
    }


    public ListResponse deleteRow(String id, String rowId) throws IOException {
        deleteRow(rowId);
        return listsService.findById(id);
    }

    public String getValue(RowResponse rowRes, String columnId) {
        return rowRes.getCells().stream()
                .filter(cell -> cell.getColumn().equals(UUID.fromString(columnId)))
                .findFirst()
                .map(CellResponse::getValue)
                .orElse(null);
    }


    //region Column
    public Column generateColumn(ColumnRequest column) {
        List<ParaRequest> configs = column.getConfigs();

        List<Parameter> paras = new ArrayList<>();
        for (ParaRequest para : configs) {
            paras.add(new Parameter(para.getName(), para.getValue()));
        }

        return new ColumnBuilder(column.getType(), column.getName())
                .configure(paras)
                .build();
    }

    public void save(Column column) throws IOException {


        colRepository.save(column);

        List<ColumnConfig> columnConfigs = new ArrayList<>();

        for (Parameter para : column.getConfigs()) {
            ColumnConfig columnConfig = new ColumnConfig(column, para);
            columnConfigs.add(columnConfig);
        }

        List<Row> rows = rowRepository.findAllByListId(column.getList().getId().toString());
        for (Row row : rows) {
            Optional<String> defaultVal = Optional.ofNullable(column.getDefaultValue());
            row.addCell(Cell.of(row, column, new SingleObject(defaultVal.orElse(""))));
            rowRepository.update(row.getId().toString(), row);
        }

        colConfigRepository.saveAll(columnConfigs);
    }

    public void deleteColumn(String id) throws IOException {
        colRepository.delete(id);

        colConfigRepository.deleteAllByColumnId(id);

        rowRepository.deleteCellsByColumnId(id);
    }

    public void updateColumn(String id, Column column) throws IOException {
        colRepository.update(id, column);


        colConfigRepository.deleteAllByColumnId(id);

        List<ColumnConfig> columnConfigs = new ArrayList<>();

        for (Parameter para : column.getConfigs()) {
            ColumnConfig columnConfig = new ColumnConfig(column, para);
            columnConfigs.add(columnConfig);
        }

        colConfigRepository.saveAll(columnConfigs);
    }

    public Column findColumnById(String id) throws IOException {
        Column column = colRepository.findById(id);

        List<ColumnConfig> columnConfigs = colConfigRepository.findAllByColumnId(id);

        List<Parameter> parameters = columnConfigs.stream()
                .map(ColumnConfig::getParameter)
                .collect(Collectors.toList());

        column.setConfigs(parameters);

        return column;
    }
    //endregion


    //region Row

    public Row generateRow(ListResponse list, RowRequest rowRequest) throws InvalidValueException {
        List<ColumnResponse> columns = list.getColumns();

        Row row = new Row();

        row.setList(MapperUtil.mapper.map(list, MicrosoftList.class));

        for (ColumnResponse column : columns) {
            Optional<String> value = Optional.ofNullable(rowRequest)
                    .map(r -> r.getValues().get(column.getId()));

            Optional<String> defaultVal = Optional.ofNullable(column.getDefaultValue());

            Column col = MapperUtil.mapper.map(column, Column.class);

            boolean isInvalid = isValueInvalid(col, ValueFactory.create(value.orElse(defaultVal.orElse(""))));
            CommonService.throwIsInvalidValue(isInvalid, "Invalid value");

            row.addCell(Cell.of(row, col, new SingleObject(value.orElse(defaultVal.orElse("")))));
        }

        return row;
    }

    public void save(Row row) throws IOException {
        rowRepository.save(row);
    }

    public Row findRowById(String rowId) throws IOException {
        return rowRepository.findById(rowId);
    }

    public boolean isValueInvalid(Column col, IValue value) {
        return !col.isValidValue(value);
    }

    public void updateCell(String rowId, String columnId, String value) throws IOException, InvalidValueException {
        IValue val = ValueFactory.create(value);
        Column col = findColumnById(columnId);

        boolean isInvalid = isValueInvalid(col, val);
        CommonService.throwIsInvalidValue(isInvalid, "Invalid value");

        Row row = findRowById(rowId);
        List<Cell> cells = row.getCells();

        cells.stream()
                .filter(cell -> cell.getColumn().getId().equals(UUID.fromString(columnId)))
                .findFirst()
                .ifPresent(cell -> cell.setValue(val));

        row.setCells(cells);

        rowRepository.update(rowId, row);

    }

    public void deleteRow(String rowId) throws IOException {
        rowRepository.delete(rowId);
    }
    //endregion
}


