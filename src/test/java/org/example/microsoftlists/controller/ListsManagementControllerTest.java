package org.example.microsoftlists.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.microsoftlists.model.MicrosoftList;
import org.example.microsoftlists.model.Template;
import org.example.microsoftlists.service.ListsManagementService;
import org.example.microsoftlists.view.dto.MapperUtil;
import org.example.microsoftlists.view.dto.request.ListRequest;
import org.example.microsoftlists.view.dto.request.TemplateRequest;
import org.example.microsoftlists.view.dto.response.ListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(ListsManagementController.class)
class ListsManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ListsManagementService listsService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetLists() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        when(listsService.findAll(pageable)).thenReturn(new PageImpl<>(Collections.emptyList(), pageable, 0));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/lists-management/lists")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Lists loaded successfully"));
    }

    @Test
    void testCreateList() throws Exception {
        ListRequest request = new ListRequest();
        request.setName("name");
        request.setDescription("description");

        when(listsService.create(any(ListRequest.class))).thenReturn(MapperUtil.mapper.map(new MicrosoftList(), ListResponse.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/lists-management/lists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("List created successfully"));
    }

    @Test
    void testCreateListWithTemplate() throws Exception {
        ListRequest request = new ListRequest();
        request.setName("name");
        request.setDescription("description");

        when(listsService.create(anyString(), any(ListRequest.class))).thenReturn(MapperUtil.mapper.map(new MicrosoftList(), ListResponse.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/lists-management/lists/{templateId}", "templateId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("List created successfully"));
    }

    @Test
    void testUpdateList() throws Exception {
        MicrosoftList list = new MicrosoftList();
        // Set properties of MicrosoftList if needed

        mockMvc.perform(MockMvcRequestBuilders.put("/api/lists-management/lists/{id}", "listId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(list)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("List updated successfully"));
    }

    @Test
    void testDeleteList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/lists-management/lists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("listId")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("List deleted successfully"));
    }

    @Test
    void testGetAllTemplates() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        when(listsService.findAllTemplate(pageable)).thenReturn(new PageImpl<>(Collections.emptyList(), pageable, 0));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/lists-management/templates")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Templates load successfully"));
    }

    @Test
    void testSaveTemplate() throws Exception {
        TemplateRequest templateRequest = new TemplateRequest();

        when(listsService.saveTemplate(anyString(), anyList())).thenReturn(new Template());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/lists-management/templates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(templateRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Template saved successfully"));
    }

    @Test
    void testDeleteTemplate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/lists-management/templates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("templateId")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Template deleted successfully"));
    }
}
