package org.example.microsoftlists.service;

import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.dto.request.ColumnRequest;
import org.example.microsoftlists.dto.request.RowRequest;
import org.example.microsoftlists.dto.request.SortRequest;
import org.example.microsoftlists.dto.response.CellResponse;
import org.example.microsoftlists.dto.response.ListResponse;
import org.example.microsoftlists.dto.response.RowResponse;
import org.example.microsoftlists.model.*;

import javax.swing.*;
import java.io.IOException;
import java.util.*;


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


    public ListResponse createRow(String id, RowRequest rowRequest) throws IOException {
        ListResponse list = listsService.findById(id);
        List<RowResponse> rows = list.getRows();

        Row row = rowService.generateRow(list, rowRequest);
        rowService.save(row);

        rows.add(new RowResponse(row));
        return list;
    }
    public ListResponse updateRow(String id, String rowId, String columnId, Object value) throws IOException {
        rowService.updateCell(rowId, columnId, value);
        return listsService.findById(id);
    }


    public ListResponse deleteRow(String id, String rowId) throws IOException {
        rowService.deleteRow(rowId);
        return listsService.findById(id);
    }

    public ListResponse sort(String id, SortRequest sortReq) throws IOException {
        ListResponse list = listsService.findById(id);
        List<RowResponse> rows = list.getRows();

        String columnId = sortReq.getColumnId();
        Column column = colService.findColumnById(columnId);

        SortOrder order = sortReq.getOrder();

        rows.sort((r1, r2) -> {
            Object cell1 = getValue(r1, columnId);
            Object cell2 = getValue(r2, columnId);

            return compare(cell1, cell2, column, order);

        });

        list.setRows(rows);
        return list;
    }

    private Object getValue(RowResponse rowRes, String columnId) {
        return rowRes.getCells().stream()
                .filter(cell -> cell.getColumnId().equals(columnId))
                .findFirst()
                .map(CellResponse::getValue)
                .orElse(null);
    }

    private int compare(Object cell1, Object cell2, Column column, SortOrder order) {
        return Optional.of(order)
                .filter(o -> o == SortOrder.ASCENDING)
                .map(o -> column.compare(cell1, cell2))
                .orElseGet(() -> column.compare(cell2, cell1));

    }

    public ListResponse search(String id, String key) throws IOException {
        ListResponse list = listsService.findById(id);
        List<RowResponse> rows = list.getRows();

        rows.removeIf(row -> row.getCells().stream()
                .noneMatch(cell -> cell.getValue().toString().contains(key)));

        list.setRows(rows);
        return list;

    }


    public ListResponse getList(String id, int page) throws IOException {
        ListResponse list = listsService.findById(id);
        List<RowResponse> rows = list.getRows();

        int pageSize = Configuration.PAGE_SIZE;
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, rows.size());

        list.setRows(rows.subList(start, end));
        return list;

    }
}


