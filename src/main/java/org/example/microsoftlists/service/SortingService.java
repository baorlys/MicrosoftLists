package org.example.microsoftlists.service;

import org.example.microsoftlists.model.Column;
import org.example.microsoftlists.view.dto.request.SortRequest;
import org.example.microsoftlists.view.dto.response.ListResponse;
import org.example.microsoftlists.view.dto.response.RowResponse;

import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class SortingService {
    private final MicrosoftListService listsService;
    private final ListService listService;

    public SortingService() {
        this.listsService = new MicrosoftListService();
        this.listService = new ListService();
    }

    public ListResponse sort(String id, SortRequest sortReq) throws IOException {
        ListResponse list = listsService.findById(id);
        List<RowResponse> rows = list.getRows();

        String columnId = sortReq.getColumnId();
        Column column = listService.findColumnById(columnId);

        SortOrder order = sortReq.getOrder();

        rows.sort((r1, r2) -> {
            String cell1 = listService.getValue(r1, columnId);
            String cell2 = listService.getValue(r2, columnId);

            return compare(cell1, cell2, column, order);
        });

        list.setRows(rows);
        return list;
    }



    private int compare(String cell1, String cell2, Column column, SortOrder order) {
        return Optional.of(order)
                .filter(o -> o == SortOrder.ASCENDING)
                .map(o -> column.compare(cell1, cell2))
                .orElseGet(() -> column.compare(cell2, cell1));
    }
}
