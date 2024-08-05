package org.example.microsoftlists.service;

import org.example.microsoftlists.view.dto.response.ListResponse;
import org.example.microsoftlists.view.dto.response.RowResponse;

import java.io.IOException;
import java.util.List;

public class SearchingService {
    private final MicrosoftListService listsService;

    public SearchingService() {
        this.listsService = new MicrosoftListService();
    }

    public ListResponse search(String id, String key) throws IOException {
        ListResponse list = listsService.findById(id);
        List<RowResponse> rows = list.getRows();

        rows.removeIf(row -> row.getCells().stream()
                .noneMatch(cell -> cell.getValue().contains(key)));

        list.setRows(rows);
        return list;
    }
}
