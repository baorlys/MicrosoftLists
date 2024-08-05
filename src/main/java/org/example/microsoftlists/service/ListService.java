package org.example.microsoftlists.service;

import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.exception.NameExistsException;
import org.example.microsoftlists.exception.InvalidValueException;
import org.example.microsoftlists.model.value.IValue;
import org.example.microsoftlists.model.value.SingleObject;
import org.example.microsoftlists.model.value.ValueFactory;
import org.example.microsoftlists.repository.json.RowRepository;
import org.example.microsoftlists.view.dto.MapperUtil;
import org.example.microsoftlists.view.dto.request.ColumnRequest;
import org.example.microsoftlists.view.dto.request.RowRequest;
import org.example.microsoftlists.view.dto.response.CellResponse;
import org.example.microsoftlists.view.dto.response.ColumnResponse;
import org.example.microsoftlists.view.dto.response.ListResponse;
import org.example.microsoftlists.view.dto.response.RowResponse;
import org.example.microsoftlists.model.*;

import java.io.IOException;
import java.util.*;

public class ListService {
    private final MicrosoftListService listsService;

    private final RowRepository rowRepository;
    public ListService() {
        this.listsService = new MicrosoftListService();
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

        Column col = listsService.generateColumn(column);
        MicrosoftList listObj = MapperUtil.mapper.map(list, MicrosoftList.class);
        listObj.setId(UUID.fromString(id));
        col.setList(listObj);

        listsService.save(col);

        return listsService.findById(id);
    }

    public ListResponse updateColumn(String id, String columnId, ColumnRequest column) throws IOException {
        Column col = listsService.findColumnById(columnId);

        Column updatedCol = listsService.generateColumn(column);

        updatedCol.setId(UUID.fromString(columnId));
        updatedCol.setList(col.getList());

        listsService.updateColumn(columnId, updatedCol);

        return listsService.findById(id);

    }

    public ListResponse deleteColumn(String id, String columnId) throws IOException {
        listsService.deleteColumn(columnId);

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
        Column col = listsService.findColumnById(columnId);

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


