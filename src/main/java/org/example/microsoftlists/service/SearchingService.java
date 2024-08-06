package org.example.microsoftlists.service;

import org.example.microsoftlists.view.dto.response.ListResponse;
import org.example.microsoftlists.view.dto.response.RowResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchingService {
    @Autowired
    private MicrosoftListService listsService;

    public ListResponse search(String id, String key) {
        ListResponse list = listsService.findById(id);
        List<RowResponse> rows = list.getRows();

        rows.removeIf(row -> row.getCells().stream()
                .noneMatch(cell -> cell.getValue().toLowerCase().contains(key.toLowerCase())));

        list.setRows(rows);
        return list;
    }
}
