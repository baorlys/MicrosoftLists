package org.example.microsoftlists.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.service.FilterService;
import org.example.microsoftlists.service.ListService;
import org.example.microsoftlists.view.dto.request.ColumnRequest;
import org.example.microsoftlists.view.dto.request.RowRequest;
import org.example.microsoftlists.view.dto.request.ViewRequest;
import org.example.microsoftlists.view.dto.response.ListResponse;
import org.example.microsoftlists.view.dto.response.RowResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest(ListController.class)
class ListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ListService listService;

    @MockBean
    private FilterService filterService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateColumn() throws Exception {
        ColumnRequest columnRequest = new ColumnRequest();
        columnRequest.setName("columnName");
        columnRequest.setType(ColumnType.TEXT);
        ListResponse listResponse = new ListResponse();

        when(listService.createColumn(anyString(), any(ColumnRequest.class))).thenReturn(listResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/list/{id}", "listId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(columnRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Column created successfully"));
    }

    @Test
    void testUpdateColumn() throws Exception {
        ColumnRequest columnRequest = new ColumnRequest();
        columnRequest.setName("columnName");
        columnRequest.setType(ColumnType.TEXT);
        ListResponse listResponse = new ListResponse();

        when(listService.updateColumn(anyString(), anyString(), any(ColumnRequest.class))).thenReturn(listResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/list/{id}/column/{columnId}", "listId", "columnId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(columnRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Column updated successfully"));
    }

    @Test
    void testDeleteColumn() throws Exception {
        ListResponse listResponse = new ListResponse();

        when(listService.deleteColumn(anyString(), anyString())).thenReturn(listResponse);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/list/{id}/column/{columnId}", "listId", "columnId"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Column deleted successfully"));
    }

    @Test
    void testCreateRow() throws Exception {
        ListResponse listResponse = new ListResponse();

        when(listService.createRow(anyString())).thenReturn(listResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/list/{id}/row", "listId"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Row created successfully"));
    }

    @Test
    void testCreateRowWithValues() throws Exception {
        RowRequest rowRequest = new RowRequest();
        ListResponse listResponse = new ListResponse();

        when(listService.createRow(anyString(), any(RowRequest.class))).thenReturn(listResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/list/{id}/row/values", "listId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rowRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Row created successfully"));
    }

    @Test
    void testUpdateRow() throws Exception {
        ListResponse listResponse = new ListResponse();

        when(listService.updateRow(anyString(), anyString(), anyString(), anyString())).thenReturn(listResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/list/{id}/row/{rowId}/{columnId}", "listId", "rowId", "columnId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(listResponse)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Row updated successfully"));
    }

    @Test
    void testDeleteRow() throws Exception {
        ListResponse listResponse = new ListResponse();

        when(listService.deleteRow(anyString(), anyString())).thenReturn(listResponse);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/list/{id}/row/{rowId}", "listId", "rowId"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Row deleted successfully"));
    }

    @Test
    void testCreateView() throws Exception {
        ViewRequest viewRequest = new ViewRequest();
        ListResponse listResponse = new ListResponse();

        when(listService.createView(anyString(), any(ViewRequest.class))).thenReturn(listResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/list/{id}/view", "listId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(viewRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("View created successfully"));
    }

    @Test
    void testDeleteView() throws Exception {
        ListResponse listResponse = new ListResponse();

        when(listService.deleteView(anyString(), anyString())).thenReturn(listResponse);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/list/{id}/view", "listId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("viewId")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("View deleted successfully"));
    }

    @Test
    void testUpdateView() throws Exception {
        ViewRequest viewRequest = new ViewRequest();
        ListResponse listResponse = new ListResponse();

        when(listService.updateView(anyString(), anyString(), any(ViewRequest.class))).thenReturn(listResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/list/{id}/view/{viewId}", "listId", "viewId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(viewRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("View updated successfully"));
    }

    @Test
    void testSortList() throws Exception {
        ListResponse listResponse = new ListResponse();

        when(filterService.sort(anyString(), anyString(), any())).thenReturn(listResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/list/{id}/sort/{columnId}/{order}", "listId", "columnId", "ASCENDING"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("List sorted successfully"));
    }

    @Test
    void testGroupList() throws Exception {
        RowResponse rowResponse = new RowResponse();
        Map<String, List<RowResponse>> groupedRows = new HashMap<>();
        groupedRows.put("key", List.of(rowResponse));

        when(filterService.groupBy(anyString(), anyString())).thenReturn(groupedRows);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/list/{id}/group/{columnId}", "listId", "columnId"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("List grouped successfully"));
    }
//
    @Test
    void testSearchList() throws Exception {
        ListResponse listResponse = new ListResponse();

        when(filterService.search(anyString(), anyString())).thenReturn(listResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/list/{id}/search/{key}", "listId", "key"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("List searched successfully"));
    }
}
