package org.example.microsoftlists.service;

import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.view.dto.response.CellResponse;
import org.example.microsoftlists.view.dto.response.ListResponse;
import org.example.microsoftlists.view.dto.response.RowResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class GroupService {
    private final MicrosoftListService listsService;

    public GroupService() {
        this.listsService = new MicrosoftListService();
    }

    public Map<String, List<RowResponse>> groupBy(String id, String colId) throws IOException {
        ListResponse list = listsService.findById(id);
        List<RowResponse> rows = list.getRows();

        return rows.stream()
                .collect(Collectors.groupingBy(row ->
                                row.getCells().stream()
                                        .filter(cell -> cell.getColumn().equals(UUID.fromString(colId)))
                                        .map(CellResponse::getValue)
                                        .findFirst()
                                        .orElse(Configuration.DEFAULT_GROUP_NAME),
                        Collectors.toList()));
    }
}
