package org.example.microsoftlists.service;

import org.example.microsoftlists.exception.InvalidValueException;
import org.example.microsoftlists.exception.NameExistsException;
import org.example.microsoftlists.model.Cell;
import org.example.microsoftlists.model.Column;
import org.example.microsoftlists.model.MicrosoftList;
import org.example.microsoftlists.model.Row;
import org.example.microsoftlists.model.type.TypeFactory;
import org.example.microsoftlists.model.value.IValue;
import org.example.microsoftlists.model.value.SingleObject;
import org.example.microsoftlists.model.value.ValueFactory;
import org.example.microsoftlists.model.view.View;
import org.example.microsoftlists.model.view.ViewFactory;
import org.example.microsoftlists.repository.CellRepository;
import org.example.microsoftlists.repository.RowRepository;
import org.example.microsoftlists.repository.ViewRepository;
import org.example.microsoftlists.view.dto.MapperUtil;
import org.example.microsoftlists.view.dto.request.ColumnRequest;
import org.example.microsoftlists.view.dto.request.RowRequest;
import org.example.microsoftlists.view.dto.request.ViewRequest;
import org.example.microsoftlists.view.dto.response.CellResponse;
import org.example.microsoftlists.view.dto.response.ColumnResponse;
import org.example.microsoftlists.view.dto.response.ListResponse;
import org.example.microsoftlists.view.dto.response.RowResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@Service
public class ListService {
    private final ListsManagementService listsService;

    private final RowRepository rowRepository;

    private final CellRepository cellRepository;

    private final ViewRepository viewRepository;
    @Autowired
    public ListService(ListsManagementService listsService,
                       RowRepository rowRepository,
                       CellRepository cellRepository,
                       ViewRepository viewRepository) {
        this.listsService = listsService;
        this.rowRepository = rowRepository;
        this.cellRepository = cellRepository;
        this.viewRepository = viewRepository;
    }

    public boolean isColumnExists(ListResponse list, String colName)  {
        return list.getColumns().stream()
                .anyMatch(column -> column.getName().equals(colName));
    }


    public ListResponse createColumn(String id, ColumnRequest column) throws NameExistsException {
        ListResponse list = listsService.findById(id);

        boolean isExists = isColumnExists(list, column.getName());
        CommonService.throwIsExists(isExists, "Column name already exists");

        Column col = listsService.generateColumn(column);
        MicrosoftList listObj = MapperUtil.mapper.map(list, MicrosoftList.class);
        listObj.setId(id);
        col.setList(listObj);

        listsService.saveColumn(col);

        return listsService.findById(id);
    }

    public ListResponse updateColumn(String id, String columnId, ColumnRequest column) {
        Column col = listsService.findColumnById(columnId);

        Column updatedCol = listsService.generateColumn(column);

        updatedCol.setId(columnId);
        updatedCol.setList(col.getList());

        listsService.updateColumn(columnId, updatedCol);

        return listsService.findById(id);

    }

    public ListResponse deleteColumn(String id, String columnId)  {
        listsService.deleteColumn(columnId);

        return listsService.findById(id);
    }


    public ListResponse createRow(String id) throws InvalidValueException {
        return createRow(id, null);
    }

    public ListResponse createRow(String id, RowRequest rowRequest) throws InvalidValueException {
        ListResponse list = listsService.findById(id);
        Row row = generateRow(list, rowRequest);
        save(row);
        return listsService.findById(id);
    }
    public ListResponse updateRow(String id, String rowId, String columnId, String value)
            throws InvalidValueException {
        updateCell(rowId, columnId, value);
        return listsService.findById(id);
    }


    public ListResponse deleteRow(String id, String rowId) {
        deleteRow(rowId);
        return listsService.findById(id);
    }

    public String getValue(RowResponse rowRes, String colName) {
        return rowRes.getCells().stream()
                .filter(cell -> cell.getColumn().equals(colName))
                .findFirst()
                .map(CellResponse::getValue)
                .orElse("");
    }




    //region Row

    public Row generateRow(ListResponse list, RowRequest rowRequest) throws InvalidValueException {
        List<ColumnResponse> columns = list.getColumns();

        Row row = new Row();

        row.setList(MapperUtil.mapper.map(list, MicrosoftList.class));

        for (ColumnResponse column : columns) {
            Column col = MapperUtil.mapper.map(column, Column.class);
            col.setType(TypeFactory.getType(column.getType()));

            Optional<String> value = Optional.ofNullable(rowRequest)
                    .map(r -> r.getValues().get(column.getId()));

            boolean isInvalid = isValueInvalid(col, ValueFactory.create(value.orElse("")));
            CommonService.throwIsInvalidValue(isInvalid, "Invalid value");

            Optional<String> defaultVal = Optional.ofNullable(column.getDefaultValue());

            row.addCell(Cell.of(row, col, new SingleObject(value.orElse(defaultVal.orElse("")))));
        }

        return row;
    }

    public void save(Row row)  {
        rowRepository.save(row);
    }

    public Row findRowById(String rowId) {
        return Optional.of(rowRepository.findById(rowId))
                .get()
                .orElseThrow(() -> new NoSuchElementException("Row not found"));
    }

    public boolean isValueInvalid(Column col, IValue value) {
        return !col.isValidValue(value);
    }

    public void updateCell(String rowId, String columnId, String value) throws InvalidValueException {
        IValue val = ValueFactory.create(value);
        Column col = listsService.findColumnById(columnId);

        boolean isInvalid = isValueInvalid(col, val);
        CommonService.throwIsInvalidValue(isInvalid, "Invalid value");

        Row row = findRowById(rowId);
        List<Cell> cells = row.getCells();

        cells.stream()
                .filter(cell -> cell.getColumn().getId().equals(columnId))
                .findFirst()
                .ifPresent(cell -> cell.setValue(val));

        row.setCells(cells);

        cellRepository.saveAll(cells);
    }


    public void deleteRow(String rowId) {
        cellRepository.deleteByRowId(rowId);
        rowRepository.deleteById(rowId);
    }
    //endregion

    //region View
    public ListResponse createView(String id, ViewRequest viewReq) {
        ListResponse list = listsService.findById(id);
        View view = new View();
        view.setName(viewReq.getName());
        view.setType(ViewFactory.create(viewReq.getType()));
        view.setList(MapperUtil.mapper.map(list, MicrosoftList.class));
        view.setConfigs(viewReq.getConfigs());
        save(view);
        return listsService.findById(id);
    }

    public ListResponse updateView(String id, String viewId, ViewRequest viewReq) {
        View view = findViewById(viewId);
        view.setName(viewReq.getName());
        view.setType(ViewFactory.create(viewReq.getType()));
        save(view);
        return listsService.findById(id);
    }

    public ListResponse deleteView(String id, String viewId) {
        viewRepository.deleteById(viewId);
        return listsService.findById(id);
    }


    public View findViewById(String viewId) {
        View view = Optional.of(viewRepository.findById(viewId)).get().orElse(null);
        CommonService.throwIsNull(view, "View not found");
        return view;
    }



    public void save(View view) {
        viewRepository.save(view);
    }
    //endregion
}


