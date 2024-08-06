package org.example.microsoftlists.service;

import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.view.dto.response.CellResponse;
import org.example.microsoftlists.view.dto.response.ListResponse;
import org.example.microsoftlists.view.dto.response.RowResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class GroupService {
    @Autowired
    private MicrosoftListService listsService;

    public Map<String, List<RowResponse>> groupBy(String id, String colId) {
        ListResponse list = listsService.findById(id);
        List<RowResponse> rows = list.getRows();

        return rows.stream()
                .collect(Collectors.groupingBy(row ->
                                row.getCells().stream()
                                        .filter(cell -> cell.getColumn().equals(colId))
                                        .map(CellResponse::getValue)
                                        .findFirst()
                                        .filter(value -> !value.isBlank())
                                        .orElse(Configuration.DEFAULT_GROUP_NAME),
                        Collectors.toList()));
    }
}
