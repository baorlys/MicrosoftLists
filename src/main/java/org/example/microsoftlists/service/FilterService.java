package org.example.microsoftlists.service;

import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.model.Column;
import org.example.microsoftlists.model.value.IValue;
import org.example.microsoftlists.model.value.ValueFactory;
import org.example.microsoftlists.view.dto.response.CellResponse;
import org.example.microsoftlists.view.dto.response.ListResponse;
import org.example.microsoftlists.view.dto.response.RowResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class FilterService {
    private final ListsManagementService listsService;

    private final ListService listService;

    @Autowired
    public FilterService(ListsManagementService listsService,
                         ListService listService) {
        this.listsService = listsService;
        this.listService = listService;
    }

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






    public ListResponse sort(String id, String columnId, SortOrder order) {
        ListResponse list = listsService.findById(id);
        List<RowResponse> rows = list.getRows();

        Column column = listsService.findColumnById(columnId);


        rows.sort((r1, r2) -> {
            String cell1 = listService.getValue(r1, column.getName());
            String cell2 = listService.getValue(r2, column.getName());

            return compare(cell1, cell2, column, order);
        });

        list.setRows(rows);
        return list;
    }



    private int compare(String cell1, String cell2, Column column, SortOrder order) {
        IValue c1 = ValueFactory.create(cell1);
        IValue c2 = ValueFactory.create(cell2);
        return Optional.of(order)
                .filter(o -> o == SortOrder.ASCENDING)
                .map(o -> column.compare(c1, c2))
                .orElseGet(() -> column.compare(c2, c1));
    }

    public ListResponse search(String id, String key) {
        ListResponse list = listsService.findById(id);
        List<RowResponse> rows = list.getRows();

        rows.removeIf(row -> row.getCells().stream()
                .noneMatch(cell -> cell.getValue().toLowerCase().contains(key.toLowerCase())));

        list.setRows(rows);
        return list;
    }
}
