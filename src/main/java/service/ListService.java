package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.constants.ConfigParameter;
import model.constants.MessageType;
import model.microsoft.list.*;
import model.microsoft.list.view.AbstractView;
import model.responses.ResultMessage;
import service.file.SaveService;
import model.microsoft.list.value.IValue;
import util.JsonUtil;
import model.microsoft.list.value.IValueFactory;
import model.microsoft.list.value.SingleObject;

import javax.swing.*;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class ListService {

    private ListService() {
        throw new IllegalStateException("Utility class");
    }

    public static long getColumnsCount(MicrosoftList list) {
        return list.getColumns().stream().filter(column -> !column.isHidden()).count();
    }

    public static void addColumn(MicrosoftList list, Column column) {
        Optional<Column> existingColumn = Optional.ofNullable(getColumn(list, column.getName()));
        existingColumn.ifPresentOrElse(
                c -> {
                    throw new IllegalArgumentException("Column already exists");
                },
                () -> list.addColumn(column)
        );
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
        var cells = JsonUtil.fromJson(data, HashMap.class);

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
                .map(col -> col.getType().handle(col.getConfig()))
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

    public static void deleteColumn(MicrosoftList list, String name) {
        Column column = getColumn(list, name);
        List<Column> cols = list.getColumns();
        cols.remove(column);
        List<Row> rows = list.getRows().stream().filter(row -> row.getCells().containsKey(name))
                .map(row -> {
                    row.getCells().remove(name);
                    return row;
                })
                .collect(Collectors.toList());
        list.setColumns(cols);
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

    public static MicrosoftList loadList(MicrosoftList list) {
        // Filter out hidden columns
        List<Column> visibleColumns = list.getColumns().stream()
                .filter(column -> !column.isHidden())
                .collect(Collectors.toList());

        // Filter rows to include only cells corresponding to visible columns
        List<Row> filteredRows = list.getRows().stream()
                .map(row -> {
                    Map<String, IValue> filteredCells = row.getCells().entrySet().stream()
                            .filter(entry -> visibleColumns.stream()
                                    .anyMatch(column -> column.getName().equals(entry.getKey())))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

                    Row newRow = new Row(list);
                    newRow.setCells(filteredCells);
                    return newRow;
                })
                .collect(Collectors.toList());

        // Create a new MicrosoftList with the filtered columns and rows
        MicrosoftList filteredList = new MicrosoftList(visibleColumns, filteredRows);
        filteredList.setName(list.getName());

        try {
            SaveService.saveFilterList(filteredList);
            return filteredList;
        } catch (Exception e) {
            Logger.getAnonymousLogger().warning("Failed to save list: " + e.getMessage());
        }

        return null;
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
        return Optional.ofNullable(row.getCells().get(colName))
                .map(IValue::get)
                .orElse(null);
    }

    public static List<Row> search(MicrosoftList list, String query) {
        return list.getRows().stream()
                .filter(row -> row.getCells().values().stream()
                        .anyMatch(cell -> cell.get().toString().toLowerCase()
                                .contains(query.toLowerCase())))
                .collect(Collectors.toList());
    }

    public static List<Row> sort(MicrosoftList list, String colName, SortOrder order) {

        Column column = getColumn(list, colName);
        List<Row> rows = list.getRows();

        list.getRows().sort((row1, row2) -> {
            Object cell1 = row1.getCells().get(colName).get();
            Object cell2 = row2.getCells().get(colName).get();
            return Optional.of(order).filter(o -> o == SortOrder.ASCENDING)
                    .map(o -> column.compare(cell1, cell2))
                    .orElseGet(() -> column.compare(cell2, cell1));
        });

        return rows;
    }

    public static ResultMessage updateCellAtRow(MicrosoftList list, int rowIndex, String colName, Object... value) {
        List<Row> rows = list.getRows();
        IValue typeVal = IValueFactory.create(value.length == 1);
        typeVal.set(value);
        Row row = RowService.updateCell(rows.get(rowIndex), colName, typeVal);
        rows.set(rowIndex, row);
        list.setRows(rows);
        return new ResultMessage("Cell updated successfully", MessageType.SUCCESS);
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
