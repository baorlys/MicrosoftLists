package org.example.microsoftlists.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.microsoftlists.dto.request.ColumnRequest;
import org.example.microsoftlists.dto.response.ListResponse;
import org.example.microsoftlists.dto.response.RowResponse;
import org.example.microsoftlists.model.*;
import org.example.microsoftlists.model.constants.ConfigParameter;
import org.example.microsoftlists.model.constants.MessageType;
import org.example.microsoftlists.model.view.AbstractView;
import org.example.microsoftlists.dto.MessageFactory;
import org.example.microsoftlists.dto.ResultMessage;
import org.example.microsoftlists.service.file.JsonService;
import org.example.microsoftlists.model.value.SingleObject;

import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class ListService {


    private final MicrosoftListService listsService;
    private final ColumnService colService;

    private final RowService rowService;

    public ListService() {
        this.listsService = new MicrosoftListService();
        this.colService = new ColumnService();
        this.rowService = new RowService();
    }


    public ListResponse createColumn(String id, ColumnRequest column) throws IOException {
        ListResponse list = listsService.findById(id);

        Column col = colService.generateColumn(column);

        MicrosoftList listObj = new MicrosoftList();
        listObj.setId(UUID.fromString(list.getId()));
        listObj.setName(list.getName());
        col.setList(listObj);

        colService.save(col);

        return list;
    }

    public ListResponse updateColumn(String id, String columnId, ColumnRequest column) throws IOException {
        Column col = colService.findColumnById(columnId);
        ListResponse list = listsService.findById(id);

        Column updatedCol = colService.generateColumn(column);

        updatedCol.setId(UUID.fromString(columnId));
        updatedCol.setList(col.getList());

        colService.updateColumn(columnId, updatedCol);

        return list;

    }

    public ListResponse deleteColumn(String id, String columnId) throws IOException {
        colService.deleteColumn(columnId);

        return listsService.findById(id);
    }


    public ListResponse createRow(String id) throws IOException {
        ListResponse list = listsService.findById(id);
        List<RowResponse> rows = list.getRows();

        Row row = rowService.generateRow(list);
        rowService.save(row);

        rows.add(new RowResponse(row));

        return list;
    }

    public ListResponse updateRow(String id, String rowId, String columnId, Object value) throws IOException {
        rowService.updateCell(rowId, columnId, value);
        return listsService.findById(id);
    }



    public static long getColumnsCount(MicrosoftList list) {
        return list.getColumns().stream().filter(column -> !column.isHidden()).count();
    }

    public static ResultMessage addColumn(MicrosoftList list, Column column) {
        Optional<Column> existingColumn = Optional.ofNullable(getColumn(list, column.getName()));
        return existingColumn.map(c -> MessageFactory.getMessage(MessageType.ERROR,"Column already exists"))
                .orElseGet(() -> {
                    list.addColumn(column);
                    return MessageFactory.getMessage(MessageType.SUCCESS,"Column added successfully");
                });

    }



    public static Column getColumn(MicrosoftList list, String name) {
        return list.getColumns().stream()
                .filter(column -> column.getName().equals(name))
                .findFirst().orElse(null);
    }

    public static void addRow(MicrosoftList list) {
        list.addRow();
    }

    public static void addRow(MicrosoftList list, String data) throws JsonProcessingException {
        Row row = new Row(list);
        var cells = JsonService.fromJson(data, HashMap.class);

        list.getColumns().forEach(column -> {
            Optional<Object> value = Optional.ofNullable(cells.get(column.getName()));
            Optional<Object> defaultValue = Optional.ofNullable(column.getDefaultValue());
            Object val = value.orElse(defaultValue.orElse(""));
            row.addCell(Cell.of(row, column, new SingleObject(val)));
        });

        list.addRow(row);
    }

    public static Row getRow(MicrosoftList list, int index) {
        return list.getRows().get(index);
    }

    public static long getRowsCount(MicrosoftList list) {
        return list.getRows().size();
    }

    public static Object getColConfigValue(MicrosoftList list, String colName, ConfigParameter para) {
        List<Parameter> config = getColConfig(list, colName);
        Objects.requireNonNull(config);
        return config.stream()
                .filter(parameter -> parameter.getName().equals(para))
                .findFirst()
                .map(Parameter::getValue)
                .orElse(null);
    }

    public static List<Parameter> getColConfig(MicrosoftList list, String colName) {
        return list.getColumns().stream()
                .filter(col -> col.getName().equals(colName))
                .findFirst()
                .map(col -> col.getType().handleConfig(col.getConfig()))
                .orElse(null);
    }


    public static List<Row> getPagedRows(MicrosoftList list, int pageNumber, int pageSize) {
        Objects.requireNonNull(list);

        int fromIndex = pageNumber * pageSize;
        List<Row> rows = list.getRows();

        if (fromIndex >= rows.size()) {
            return new ArrayList<>(); // Return empty list if fromIndex is out of bounds
        }

        int toIndex = Math.min(fromIndex + pageSize, rows.size());
        return rows.subList(fromIndex, toIndex);
    }

    public static void deleteRow(MicrosoftList list, int index) {
        Row row = list.getRows().get(index);
        List<Row> rows = list.getRows();

        rows.remove(row);

        list.setRows(rows);
    }



    public static int getRowIndex(MicrosoftList list, Row row) {
        return list.getRows().indexOf(row);
    }

    public static void deleteRows(MicrosoftList list, List<Integer> indexRows) {
        List<Row> rows = list.getRows();

        indexRows.forEach(index -> rows.remove(index.intValue()));

        list.setRows(rows);
    }


    public static void settingColumn(MicrosoftList list, String colName, Column setting) {
        Column column = getColumn(list, colName);
        List<Column> cols = list.getColumns();
        int index = cols.indexOf(column);
        cols.set(index, setting);
        list.setColumns(cols);
    }

    public static Object getValue(MicrosoftList list, int rowIndex, String colName) {
        Row row = getRow(list, rowIndex);
        return Optional.of(row.getCells().stream().filter(cell -> cell.getColumn().getName().equals(colName)).findFirst())
                .map(cell -> cell.get().getValue())
                .orElse(null);
    }

    public static List<Row> search(MicrosoftList list, String query) {
        return list.getRows().stream()
                .filter(row -> row.getCells().stream().anyMatch(cell -> cell.getValue().toString().contains(query)))
                .collect(Collectors.toList());
    }

    public static List<Row> sort(MicrosoftList list, String colName, SortOrder order) {

        Column column = getColumn(list, colName);
        List<Row> rows = list.getRows();

        list.getRows().sort((row1, row2) -> {
            Object cell1 = row1.getCells().stream().filter(cell -> cell.getColumn().getName().equals(colName)).findFirst().get().getValue().get();
            Object cell2 = row2.getCells().stream().filter(cell -> cell.getColumn().getName().equals(colName)).findFirst().get().getValue().get();
            return Optional.of(order).filter(o -> o == SortOrder.ASCENDING)
                    .map(o -> column.compare(cell1, cell2))
                    .orElseGet(() -> column.compare(cell2, cell1));
        });

        return rows;
    }




    public static void addColumns(MicrosoftList list, List<Column> cols) {
        cols.forEach(col -> addColumn(list, col));
    }


    public static ResultMessage createView(MicrosoftList list, AbstractView view) {
        list.createView(view);
        return new ResultMessage("View created successfully", MessageType.SUCCESS);
    }

    public static AbstractView getView(MicrosoftList list,String viewName) {
        return list.getViews().stream()
                .filter(view -> view.getName().equals(viewName))
                .findFirst()
                .orElse(null);
    }



}
