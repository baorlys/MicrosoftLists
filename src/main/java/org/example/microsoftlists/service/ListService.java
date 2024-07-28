package org.example.microsoftlists.service;

import org.example.microsoftlists.dto.request.ColumnRequest;
import org.example.microsoftlists.dto.response.ListResponse;
import org.example.microsoftlists.dto.response.RowResponse;
import org.example.microsoftlists.model.*;

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

    public ListResponse updateRow(String id, String rowId, String columnId, Object value) throws IOException {
        rowService.updateCell(rowId, columnId, value);
        return listsService.findById(id);
    }















}
