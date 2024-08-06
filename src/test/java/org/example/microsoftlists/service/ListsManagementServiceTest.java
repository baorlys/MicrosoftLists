package org.example.microsoftlists.service;

import org.example.microsoftlists.exception.NameExistsException;
import org.example.microsoftlists.model.*;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.constants.ConfigParameter;
import org.example.microsoftlists.repository.*;
import org.example.microsoftlists.service.builder.ColumnBuilder;
import org.example.microsoftlists.view.dto.request.ListRequest;
import org.example.microsoftlists.view.dto.response.ListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ListsManagementServiceTest {

    @InjectMocks
    private ListsManagementService listsManagementService;

    @Mock
    private MicrosoftListRepository listRepository;
    @Mock
    private ColumnRepository colRepository;
    @Mock
    private ConfigRepository configRepository;
    @Mock
    private RowRepository rowRepository;
    @Mock
    private TemplateRepository templateRepository;
    @Mock
    private CellRepository cellRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test cases for List

    @Test
    void testFindById() {
        String listId = "1";
        MicrosoftList list = new MicrosoftList();
        list.setId(listId);

        when(listRepository.findById(listId)).thenReturn(Optional.of(list));
        when(colRepository.findAllByListId(listId)).thenReturn(Collections.emptyList());
        when(rowRepository.findAllByListId(listId)).thenReturn(Collections.emptyList());

        ListResponse response = listsManagementService.findById(listId);

        assertNotNull(response);
        assertEquals(listId, response.getId());
    }

    @Test
    void testCreate() throws NameExistsException {
        ListRequest listRequest = new ListRequest();
        listRequest.setName("New List");

        when(listRepository.save(any(MicrosoftList.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        when(listRepository.findAll()).thenReturn(Collections.emptyList());

        ListResponse response = listsManagementService.create(listRequest);

        assertNotNull(response);
        assertEquals("New List", response.getName());
    }

    @Test
    void testCreateWithTemplate() throws NameExistsException {
        String templateId = "template1";
        ListRequest listRequest = new ListRequest();
        listRequest.setName("New List");

        // Create a template with columns
        Template template = new Template();
        template.setId(templateId);
        Column column1 = new ColumnBuilder(ColumnType.TEXT, "textCol").build();
        column1.setList(new MicrosoftList()); // Ensure list is set


        Column column2 = new ColumnBuilder(ColumnType.NUMBER, "numCol").build();

        column2.setList(new MicrosoftList()); // Ensure list is set


        template.setColumns(Arrays.asList(column1, column2));

        // Prepare mock data
        MicrosoftList newList = new MicrosoftList();
        newList.setName("New List");

        when(listRepository.findAll()).thenReturn(Collections.emptyList()); // No existing lists
        when(templateRepository.findById(templateId)).thenReturn(Optional.of(template));
        when(listRepository.save(any(MicrosoftList.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        // Mock saving columns and configurations
        when(colRepository.save(any(Column.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        when(configRepository.saveAll(anyList())).thenReturn(Collections.emptyList());

        ListResponse response = listsManagementService.create(templateId, listRequest);

        // Verify interactions
        verify(templateRepository).findById(templateId);
        verify(listRepository).save(any(MicrosoftList.class));
        verify(colRepository, times(template.getColumns().size())).save(any(Column.class));

        // Assertions
        assertNotNull(response);
        assertEquals("New List", response.getName());
        assertEquals(template.getColumns().size(), response.getColumns().size());
    }

    @Test
    void testCreate_ThrowsNameExistsException() {
        ListRequest listRequest = new ListRequest();
        listRequest.setName("Existing List");

        MicrosoftList existingList = new MicrosoftList();
        existingList.setName("Existing List");

        when(listRepository.findAll()).thenReturn(Collections.singletonList(existingList));

        assertThrows(NameExistsException.class, () -> listsManagementService.create(listRequest));
    }

    @Test
    void testUpdate() {
        String listId = "1";
        MicrosoftList existingList = new MicrosoftList();
        existingList.setId(listId);

        MicrosoftList updatedList = new MicrosoftList();
        updatedList.setId(listId);

        when(listRepository.findById(listId)).thenReturn(Optional.of(existingList));
        when(listRepository.save(any(MicrosoftList.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        listsManagementService.update(listId, updatedList);

        verify(listRepository).save(updatedList);
    }

    @Test
    void testDelete() {
        String listId = "1";

        listsManagementService.delete(listId);

        verify(listRepository).deleteById(listId);
    }

    // Test cases for Column
    @Test
    void testFindColumnById() {
        String columnId = "1";
        Column column = new Column();
        column.setId(columnId);

        when(colRepository.findById(columnId)).thenReturn(Optional.of(column));
        when(configRepository.findAllByColumnId(columnId)).thenReturn(Collections.emptyList());

        Column result = listsManagementService.findColumnById(columnId);

        assertNotNull(result);
        assertEquals(columnId, result.getId());
    }

    @Test
    void testSaveColumn() {
        // Prepare test data
        String listId = "list1";
        MicrosoftList microsoftList = new MicrosoftList();
        microsoftList.setId(listId);

        Column column = new ColumnBuilder(ColumnType.TEXT, "Column1").build();
        column.setList(microsoftList);
        column.setConfigs(Arrays.asList(
                new Config(column, ConfigParameter.DEFAULT_VALUE, "value1"),
                new Config(column, ConfigParameter.DEFAULT_VALUE, "value2")
        ));

        Row row = new Row();
        row.setId("row1");
        row.setList(microsoftList);
        Cell cell = new Cell();
        cell.setColumn(column);
        row.addCell(cell);

        // Mocking repository methods
        when(colRepository.save(column)).thenReturn(column);
        when(rowRepository.findAllByListId(listId)).thenReturn(Collections.singletonList(row));
        when(cellRepository.save(any(Cell.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        // Call the method to test
        listsManagementService.saveColumn(column);

        // Verify interactions
        verify(colRepository).save(column);
        verify(configRepository).saveAll(column.getConfigs());
        verify(rowRepository).findAllByListId(listId);
        verify(cellRepository).save(any(Cell.class));

        // Additional assertions to verify behavior
        assertEquals(1, row.getCells().size());
        Cell savedCell = row.getCells().get(0);
        assertEquals(column, savedCell.getColumn());
        assertEquals(microsoftList, savedCell.getRow().getList());
    }

    @Test
    void testDeleteColumn() {
        String columnId = "col1";

        // Call the method to test
        listsManagementService.deleteColumn(columnId);

        // Verify interactions
        verify(cellRepository).deleteAllByColumnId(columnId);
        verify(configRepository).deleteAllByColumnId(columnId);
        verify(colRepository).deleteById(columnId);
    }

    @Test
    void testUpdateColumn() {
        String columnId = "col1";
        Column existingColumn = new Column();
        existingColumn.setId(columnId);
        existingColumn.setName("OldName");

        Column updatedColumn = new Column();
        updatedColumn.setName("NewName");

        // Mocking repository methods
        when(colRepository.findById(columnId)).thenReturn(Optional.of(existingColumn));
        when(colRepository.save(updatedColumn)).thenReturn(updatedColumn);

        // Call the method to test
        listsManagementService.updateColumn(columnId, updatedColumn);

        // Verify interactions
        verify(colRepository).findById(columnId);
        verify(colRepository).save(updatedColumn);

        // Assert that the updated column has the correct ID and new name
        assertEquals(columnId, updatedColumn.getId());
        assertEquals("NewName", updatedColumn.getName());
    }



    // Test cases for Template
    @Test
    void testFindAllTemplate() {
        // Prepare test data
        Template template1 = new Template();
        template1.setId("template1");
        template1.setDisplayName("Template 1");

        Template template2 = new Template();
        template2.setId("template2");
        template2.setDisplayName("Template 2");

        List<Template> templates = Arrays.asList(template1, template2);
        Page<Template> templatePage = new PageImpl<>(templates);

        Pageable pageable = PageRequest.of(0, 10);

        // Mocking repository method
        when(templateRepository.findAll(pageable)).thenReturn(templatePage);

        // Call the method to test
        Page<Template> result = listsManagementService.findAllTemplate(pageable);

        // Verify the result
        assertEquals(templates.size(), result.getContent().size());
        assertEquals(template1.getId(), result.getContent().get(0).getId());
        assertEquals(template2.getId(), result.getContent().get(1).getId());
    }

    @Test
    void testSaveTemplate() {
        Template template = new Template();
        template.setDisplayName("Template");

        List<Column> columns = new ArrayList<>();
        when(templateRepository.save(any(Template.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        Template savedTemplate = listsManagementService.saveTemplate("Template", columns);

        assertNotNull(savedTemplate);
        assertEquals("Template", savedTemplate.getDisplayName());
    }

    @Test
    void testDeleteTemplateById() {
        String templateId = "1";

        boolean result = listsManagementService.deleteTemplateById(templateId);

        verify(colRepository).deleteAllByTemplateId(templateId);
        verify(templateRepository).deleteById(templateId);
        assertTrue(result);
    }
}