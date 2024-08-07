package org.example.microsoftlists.service;

import org.example.microsoftlists.model.Column;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.service.builder.ColumnBuilder;
import org.example.microsoftlists.view.dto.MapperUtil;
import org.example.microsoftlists.view.dto.response.CellResponse;
import org.example.microsoftlists.view.dto.response.ColumnResponse;
import org.example.microsoftlists.view.dto.response.ListResponse;
import org.example.microsoftlists.view.dto.response.RowResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilterServiceTest {
    @Mock
    private ListsManagementService listsService;

    @Mock
    private ListService listService;

    @InjectMocks
    private FilterService filterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGroupBy() {
        String id = "list1";
        String colName = "column name";

        CellResponse cellResponse1 = new CellResponse();
        cellResponse1.setValue("value1");
        cellResponse1.setColumn(colName);

        RowResponse rowResponse1 = new RowResponse();
        rowResponse1.setCells(List.of(cellResponse1));

        CellResponse cellResponse2 = new CellResponse();
        cellResponse2.setValue("value2");
        cellResponse2.setColumn(colName);

        RowResponse rowResponse2 = new RowResponse();
        rowResponse2.setCells(List.of(cellResponse2));

        ListResponse listResponse = new ListResponse();
        listResponse.setRows(List.of(rowResponse1, rowResponse2));

        when(listsService.findById(id)).thenReturn(listResponse);

        Map<String, List<RowResponse>> expectedMap = new HashMap<>();
        expectedMap.put("value1", List.of(rowResponse1));
        expectedMap.put("value2", List.of(rowResponse2));

        Map<String, List<RowResponse>> result = filterService.groupBy(id, colName);

        assertEquals(expectedMap, result);
    }

    @Test
    void testSort() {
        String id = "list1";
        String columnId = "col1";
        SortOrder order = SortOrder.ASCENDING;

        // Create mock Column
        Column column = new ColumnBuilder(ColumnType.TEXT, "column1").build();
        column.setId(columnId);
        ColumnResponse columnResponse = MapperUtil.mapper.map(column, ColumnResponse.class);
        // Create mock Rows
        CellResponse cellResponse1 = new CellResponse();
        cellResponse1.setValue("b");
        cellResponse1.setColumn(column);

        RowResponse rowResponse1 = new RowResponse();
        rowResponse1.setCells(List.of(cellResponse1));

        CellResponse cellResponse2 = new CellResponse();
        cellResponse2.setValue("a");
        cellResponse2.setColumn(column);

        RowResponse rowResponse2 = new RowResponse();
        rowResponse2.setCells(List.of(cellResponse2));
        // Create ListResponse and set rows
        ListResponse listResponse = new ListResponse();

        List<RowResponse> rows = new ArrayList<>();
        rows.add(rowResponse1);
        rows.add(rowResponse2);
        listResponse.setColumns(List.of(columnResponse));
        listResponse.setRows(rows);

        // Setup mock behavior
        when(listsService.findById(id)).thenReturn(listResponse);
        when(listsService.findColumnById(columnId)).thenReturn(column);
        when(listService.getValue(rowResponse1, column.getName())).thenReturn("b");
        when(listService.getValue(rowResponse2, column.getName())).thenReturn("a");

        // Execute sort
        ListResponse result = filterService.sort(id, columnId, order);

        // Verify interactions
        assertNotNull(result);
        assertEquals(List.of(rowResponse2, rowResponse1), result.getRows());
    }


    @Test
    void testSearch() {
        String id = "list1";
        String key = "1";

        CellResponse cellResponse1 = new CellResponse();
        cellResponse1.setValue("value1");

        RowResponse rowResponse1 = new RowResponse();
        rowResponse1.setCells(List.of(cellResponse1));

        CellResponse cellResponse2 = new CellResponse();
        cellResponse2.setValue("value2");

        RowResponse rowResponse2 = new RowResponse();
        rowResponse2.setCells(List.of(cellResponse2));

        ListResponse listResponse = new ListResponse();
        List<RowResponse> rows = new ArrayList<>();
        rows.add(rowResponse1);
        rows.add(rowResponse2);
        listResponse.setRows(rows);

        when(listsService.findById(id)).thenReturn(listResponse);

        ListResponse result = filterService.search(id, key);

        assertTrue(result.getRows().contains(rowResponse1));
        assertFalse(result.getRows().contains(rowResponse2));
    }
}
