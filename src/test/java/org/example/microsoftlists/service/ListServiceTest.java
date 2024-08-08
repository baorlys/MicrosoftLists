package org.example.microsoftlists.service;

import org.example.microsoftlists.exception.InvalidValueException;
import org.example.microsoftlists.exception.NameExistsException;
import org.example.microsoftlists.model.Cell;
import org.example.microsoftlists.model.Column;
import org.example.microsoftlists.model.MicrosoftList;
import org.example.microsoftlists.model.Row;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.constants.ViewConfig;
import org.example.microsoftlists.model.constants.ViewType;
import org.example.microsoftlists.model.type.TypeFactory;
import org.example.microsoftlists.model.value.IValue;
import org.example.microsoftlists.model.value.SingleObject;
import org.example.microsoftlists.model.value.ValueFactory;
import org.example.microsoftlists.model.view.View;
import org.example.microsoftlists.model.view.ViewFactory;
import org.example.microsoftlists.repository.CellRepository;
import org.example.microsoftlists.repository.RowRepository;
import org.example.microsoftlists.repository.ViewRepository;
import org.example.microsoftlists.service.builder.ColumnBuilder;
import org.example.microsoftlists.view.dto.MapperUtil;
import org.example.microsoftlists.view.dto.request.ColumnRequest;
import org.example.microsoftlists.view.dto.request.RowRequest;
import org.example.microsoftlists.view.dto.request.ViewRequest;
import org.example.microsoftlists.view.dto.response.ColumnResponse;
import org.example.microsoftlists.view.dto.response.ListResponse;
import org.example.microsoftlists.view.dto.response.RowResponse;
import org.example.microsoftlists.view.dto.response.ViewResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListServiceTest {

    @Mock
    private ListsManagementService listsService;
    @Mock
    private RowRepository rowRepository;

    @Mock
    private CellRepository cellRepository;

    @Mock
    private ViewRepository viewRepository;
    @InjectMocks
    private ListService listService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test cases for Column

    @Test
    void testCreateColumn() throws NameExistsException {
        String listId = "list1";
        ColumnRequest columnRequest = new ColumnRequest();
        columnRequest.setName("NewColumn");

        // Mock responses
        ListResponse listResponseBefore = new ListResponse();
        listResponseBefore.setColumns(Collections.emptyList());

        ListResponse listResponseAfter = new ListResponse();
        ColumnResponse columnResponse = new ColumnResponse();
        columnResponse.setName("NewColumn");
        listResponseAfter.setColumns(Collections.singletonList(columnResponse));

        Column column = new Column();
        column.setName("NewColumn");
        column.setList(new MicrosoftList());

        // Mocking method calls
        when(listsService.findById(listId)).thenReturn(listResponseBefore).thenReturn(listResponseAfter);
        when(listsService.generateColumn(columnRequest)).thenReturn(column);

        // Call the method under test
        ListResponse result = listService.createColumn(listId, columnRequest);

        // Verify interactions
        verify(listsService, times(2)).findById(listId);  // Verify it is called twice
        verify(listsService).generateColumn(columnRequest);
        verify(listsService).saveColumn(column);

        // Assert results
        assertNotNull(result);
        assertTrue(result.getColumns().stream().anyMatch(c -> c.getName().equals("NewColumn")));
    }

    @Test
    void testUpdateColumn() {
        // Prepare test data
        String listId = "list1";
        String columnId = "column1";
        ColumnRequest columnRequest = new ColumnRequest();
        columnRequest.setName("Updated Column");

        ListResponse listResponse = new ListResponse();
        listResponse.setId(listId);

        Column existingColumn = new Column();
        existingColumn.setId(columnId);
        existingColumn.setList(new MicrosoftList());

        Column updatedColumn = new Column();
        updatedColumn.setId(columnId);

        when(listsService.findById(listId)).thenReturn(listResponse);
        when(listsService.findColumnById(columnId)).thenReturn(existingColumn);
        when(listsService.generateColumn(columnRequest)).thenReturn(updatedColumn);

        // Call the method under test
        ListResponse result = listService.updateColumn(listId, columnId, columnRequest);

        // Verify interactions
        verify(listsService).findById(listId);
        verify(listsService).findColumnById(columnId);
        verify(listsService).generateColumn(columnRequest);
        verify(listsService).updateColumn(columnId, updatedColumn);

        // Assert the result
        assertEquals(listResponse, result);
    }

    @Test
    void testDeleteColumn() {
        String listId = "list1";
        String columnId = "col1";

        when(listsService.findById(listId)).thenReturn(new ListResponse());

        ListResponse result = listService.deleteColumn(listId, columnId);

        verify(listsService).deleteColumn(columnId);
        assertNotNull(result);
    }


    // Test cases for Row
    @Test
    void createRow_withValidData_shouldCreateRowSuccessfully() throws InvalidValueException {
        // Given
        String listId = "list1";
        RowRequest rowRequest = new RowRequest();
        rowRequest.setValues(Collections.singletonMap("col1", "value1"));

        ListResponse listResponse = new ListResponse();
        listResponse.setId(listId);
        // Set up necessary fields for ListResponse and columns

        Row row = new Row();
        // Set up the expected Row object

        when(listsService.findById(listId)).thenReturn(listResponse);
        when(rowRepository.save(any(Row.class))).thenReturn(row);

        // When
        ListResponse result = listService.createRow(listId, rowRequest);

        // Then
        assertNotNull(result);
        assertEquals(listId, result.getId());
        // Add more assertions based on the expected outcome

        verify(listsService, times(2)).findById(listId);
        verify(rowRepository).save(any(Row.class));
    }

    @Test
    void createRow_withValidData_shouldCreateRowSuccessfullyAndCheckCellValues() throws InvalidValueException {
        // Given
        String listId = "list1";
        RowRequest rowRequest = new RowRequest();
        rowRequest.setValues(Collections.singletonMap("col1", "value1"));

        ColumnResponse columnResponse = new ColumnResponse();
        columnResponse.setId("col1");
        columnResponse.setName("Column 1");
        columnResponse.setType(TypeFactory.getType(ColumnType.TEXT));

        ListResponse listResponse = new ListResponse();
        listResponse.setId(listId);
        listResponse.setColumns(Collections.singletonList(columnResponse));

        Row row = new Row();
        row.setList(MapperUtil.mapper.map(listResponse, MicrosoftList.class));
        // Set up the Row object with expected values
        Column column = MapperUtil.mapper.map(columnResponse, Column.class);
        IValue cellValue = ValueFactory.create("value1");
        row.addCell(Cell.of(row, column, cellValue));
        listResponse.setRows(MapperUtil.mapList(Collections.singletonList(row), RowResponse.class));

        when(listsService.findById(listId)).thenReturn(listResponse);
        when(rowRepository.save(any(Row.class))).thenReturn(row);
        when(rowRepository.findById(row.getId())).thenReturn(Optional.of(row));

        // When
        ListResponse result = listService.createRow(listId, rowRequest);

        // Then
        assertNotNull(result);
        assertEquals(listId, result.getId());

        // Verify that the row was saved correctly
        verify(rowRepository).save(any(Row.class));

        // Verify cell values
        Row savedRow = rowRepository.findById(row.getId()).orElseThrow(() -> new AssertionError("Row not found"));
        assertNotNull(savedRow);
        assertEquals(1, savedRow.getCells().size());

        // Check the cell values
        savedRow.getCells().forEach(cell -> {
            assertEquals("col1", cell.getColumn().getId());
            assertEquals("value1", cell.getValue().get());
        });

        // Verify interactions
        verify(listsService,times(2)).findById(listId);
    }

    @Test
    void createRow_withInvalidValue_shouldThrowInvalidValueException() throws InvalidValueException {
        // Given
        String listId = "list1";
        RowRequest rowRequest = new RowRequest();
        rowRequest.setValues(Collections.singletonMap("col1", "invalidValue"));

        ListResponse listResponse = new ListResponse();
        // Set up necessary fields for ListResponse and columns

        when(listsService.findById(listId)).thenReturn(listResponse);

        // Mock the behavior of generateRow method to throw InvalidValueException
        ListService spyListService = spy(listService);
        doThrow(new InvalidValueException("Invalid value")).when(spyListService).generateRow(any(ListResponse.class), any(RowRequest.class));

        // When / Then
        InvalidValueException thrown = assertThrows(InvalidValueException.class, () -> spyListService.createRow(listId, rowRequest));
        assertEquals("Invalid value", thrown.getMessage());

        verify(listsService).findById(listId);
        verify(rowRepository, never()).save(any(Row.class));
    }

    @Test
    void updateRow_ShouldUpdateCellValue_WhenValueIsValid() throws InvalidValueException {
        // Arrange
        String listId = "listId";
        String rowId = "rowId";
        String columnId = "columnId";
        String newValue = "newValue";

        ListResponse listResponse = mock(ListResponse.class);
        Row row = new Row();
        Column column = new ColumnBuilder(ColumnType.TEXT, "column").build();

        when(listsService.findById(listId)).thenReturn(listResponse);
        when(listsService.findColumnById(columnId)).thenReturn(column);
        when(rowRepository.findById(rowId)).thenReturn(Optional.of(row));

        // Act
        listService.updateRow(listId, rowId, columnId, newValue);

        // Assert
        verify(cellRepository).saveAll(anyList());  // Verify that saveAll was called
    }

    @Test
    void testUpdateCell_Text() throws InvalidValueException {
        Column column = new Column();
        column.setId("col");
        column.setType(TypeFactory.getType(ColumnType.TEXT));

        Row row = new Row();
        row.setId("row");
        Cell cell = new Cell();
        cell.setRow(row);
        cell.setColumn(column);
        cell.setValue(new SingleObject("oldText"));
        row.setCells(Collections.singletonList(cell));

        when(listsService.findColumnById("col")).thenReturn(column);
        when(rowRepository.findById("row")).thenReturn(Optional.of(row));
        when(cellRepository.saveAll(any())).thenReturn(Collections.singletonList(cell));

        listService.updateCell("row", "col", "newText");

        verify(cellRepository, times(1)).saveAll(any());
        assertEquals("newText", row.getCells().get(0).getValue().get());
    }

    @Test
    void testUpdateCell_Number() throws InvalidValueException {
        Column column = new Column();
        column.setId("col");
        column.setType(TypeFactory.getType(ColumnType.NUMBER));

        Row row = new Row();
        row.setId("row");
        Cell cell = new Cell();
        cell.setRow(row);
        cell.setColumn(column);
        cell.setValue(new SingleObject("0"));
        row.setCells(Collections.singletonList(cell));

        when(listsService.findColumnById("col")).thenReturn(column);
        when(rowRepository.findById("row")).thenReturn(Optional.of(row));
        when(cellRepository.saveAll(any())).thenReturn(Collections.singletonList(cell));


        listService.updateCell("row", "col", "123");

        verify(cellRepository, times(1)).saveAll(any());
        assertEquals("123", row.getCells().get(0).getValue().get());
    }

    @Test
    void testUpdateCell_Checkbox() throws InvalidValueException {
        Column column = new Column();
        column.setId("col");
        column.setType(TypeFactory.getType(ColumnType.CHECKBOX));

        Row row = new Row();
        row.setId("row");
        Cell cell = new Cell();
        cell.setRow(row);
        cell.setColumn(column);
        cell.setValue(new SingleObject("false"));
        row.setCells(Collections.singletonList(cell));

        when(listsService.findColumnById("col")).thenReturn(column);
        when(rowRepository.findById("row")).thenReturn(Optional.of(row));
        when(cellRepository.saveAll(any())).thenReturn(Collections.singletonList(cell));


        listService.updateCell("row", "col", "true");

        verify(cellRepository, times(1)).saveAll(any());
        assertEquals("true", row.getCells().get(0).getValue().get());
    }

    @Test
    void testUpdateCell_Choice() throws InvalidValueException {
        Column column = new Column();
        column.setId("col");
        column.setType(TypeFactory.getType(ColumnType.CHOICE));

        Row row = new Row();
        row.setId("row");
        Cell cell = new Cell();
        cell.setRow(row);
        cell.setColumn(column);
        cell.setValue(new SingleObject("Choice 1"));
        row.setCells(Collections.singletonList(cell));

        when(listsService.findColumnById("col")).thenReturn(column);
        when(rowRepository.findById("row")).thenReturn(Optional.of(row));
        when(cellRepository.saveAll(any())).thenReturn(Collections.singletonList(cell));


        listService.updateCell("row", "col", "Choice 2");

        verify(cellRepository, times(1)).saveAll(any());
        assertEquals("Choice 2", row.getCells().get(0).getValue().get());
    }

    @Test
    void testUpdateCell_Hyperlink() throws InvalidValueException {
        Column column = new Column();
        column.setId("col");
        column.setType(TypeFactory.getType(ColumnType.HYPERLINK));

        Row row = new Row();
        row.setId("row");
        Cell cell = new Cell();
        cell.setRow(row);
        cell.setColumn(column);
        cell.setValue(new SingleObject("http://exampleold.com"));
        row.setCells(Collections.singletonList(cell));

        when(listsService.findColumnById("col")).thenReturn(column);
        when(rowRepository.findById("row")).thenReturn(Optional.of(row));
        when(cellRepository.saveAll(any())).thenReturn(Collections.singletonList(cell));


        listService.updateCell("row", "col", "http://example.com");

        verify(cellRepository, times(1)).saveAll(any());
        assertEquals("http://example.com", row.getCells().get(0).getValue().get());
    }

    @Test
    void testUpdateCell_Image() throws InvalidValueException {
        Column column = new Column();
        column.setId("col");
        column.setType(TypeFactory.getType(ColumnType.IMAGE));

        Row row = new Row();
        row.setId("row");
        Cell cell = new Cell();
        cell.setRow(row);
        cell.setColumn(column);
        cell.setValue(new SingleObject("path/to/oldImage"));
        row.setCells(Collections.singletonList(cell));

        when(listsService.findColumnById("col")).thenReturn(column);
        when(rowRepository.findById("row")).thenReturn(Optional.of(row));
        when(cellRepository.saveAll(any())).thenReturn(Collections.singletonList(cell));


        listService.updateCell("row", "col", "path/to/newImage");

        verify(cellRepository, times(1)).saveAll(any());
        assertEquals("path/to/newImage", row.getCells().get(0).getValue().get());
    }

    @Test
    void testUpdateCell_MultipleLinesOfText() throws InvalidValueException {
        Column column = new Column();
        column.setId("col");
        column.setType(TypeFactory.getType(ColumnType.MULTIPLE_LINES_OF_TEXT));

        Row row = new Row();
        row.setId("row");
        Cell cell = new Cell();
        cell.setRow(row);
        cell.setColumn(column);
        cell.setValue(new SingleObject("old\ntext"));
        row.setCells(Collections.singletonList(cell));

        when(listsService.findColumnById("col")).thenReturn(column);
        when(rowRepository.findById("row")).thenReturn(Optional.of(row));
        when(cellRepository.saveAll(any())).thenReturn(Collections.singletonList(cell));


        listService.updateCell("row", "col", "new\ntext");

        verify(cellRepository, times(1)).saveAll(any());
        assertEquals("new\ntext", row.getCells().get(0).getValue().get());
    }

    @Test
    void testUpdateCell_Person() throws InvalidValueException {
        Column column = new Column();
        column.setId("col");
        column.setType(TypeFactory.getType(ColumnType.PERSON));

        Row row = new Row();
        row.setId("row");
        Cell cell = new Cell();
        cell.setRow(row);
        cell.setColumn(column);
        cell.setValue(new SingleObject("oldperson@gmail.com"));
        row.setCells(Collections.singletonList(cell));

        when(listsService.findColumnById("col")).thenReturn(column);
        when(rowRepository.findById("row")).thenReturn(Optional.of(row));
        when(cellRepository.saveAll(any())).thenReturn(Collections.singletonList(cell));


        listService.updateCell("row", "col", "newperson@gmail.com");

        verify(cellRepository, times(1)).saveAll(any());
        assertEquals("newperson@gmail.com", row.getCells().get(0).getValue().get());
    }

    @Test
    void testUpdateCell_Lookup() throws InvalidValueException {
        Column column = new Column();
        column.setId("col");
        column.setType(TypeFactory.getType(ColumnType.LOOKUP));

        Row row = new Row();
        row.setId("row");
        Cell cell = new Cell();
        cell.setRow(row);
        cell.setColumn(column);
        cell.setValue(new SingleObject("old column id"));
        row.setCells(Collections.singletonList(cell));

        when(listsService.findColumnById("col")).thenReturn(column);
        when(rowRepository.findById("row")).thenReturn(Optional.of(row));
        when(cellRepository.saveAll(any())).thenReturn(Collections.singletonList(cell));


        listService.updateCell("row", "col", "new column id");

        verify(cellRepository, times(1)).saveAll(any());
        assertEquals("new column id", row.getCells().get(0).getValue().get());
    }

    @Test
    void testUpdateCell_AverageRating() throws InvalidValueException {
        Column column = new Column();
        column.setId("col");
        column.setType(TypeFactory.getType(ColumnType.AVERAGE_RATING));

        Row row = new Row();
        row.setId("row");
        Cell cell = new Cell();
        cell.setRow(row);
        cell.setColumn(column);
        cell.setValue(new SingleObject("3"));
        row.setCells(Collections.singletonList(cell));

        when(listsService.findColumnById("col")).thenReturn(column);
        when(rowRepository.findById("row")).thenReturn(Optional.of(row));
        when(cellRepository.saveAll(any())).thenReturn(Collections.singletonList(cell));


        listService.updateCell("row", "col", "5");

        verify(cellRepository, times(1)).saveAll(any());
        assertEquals("5", row.getCells().get(0).getValue().get());
    }

    @Test
    void testUpdateCell_Date() throws InvalidValueException {
        Column column = new Column();
        column.setId("col");
        column.setType(TypeFactory.getType(ColumnType.AVERAGE_RATING));

        Row row = new Row();
        row.setId("row");
        Cell cell = new Cell();
        cell.setRow(row);
        cell.setColumn(column);
        cell.setValue(new SingleObject("2024-08-05"));
        row.setCells(Collections.singletonList(cell));

        when(listsService.findColumnById("col")).thenReturn(column);
        when(rowRepository.findById("row")).thenReturn(Optional.of(row));
        when(cellRepository.saveAll(any())).thenReturn(Collections.singletonList(cell));


        listService.updateCell("row", "col", "2024-08-07");

        verify(cellRepository, times(1)).saveAll(any());
        assertEquals("2024-08-07", row.getCells().get(0).getValue().get());
    }


    @Test
    void updateRow_ShouldThrowInvalidValueException_WhenValueIsInvalid() {
        // Arrange
        String listId = "listId";
        String rowId = "rowId";
        String columnId = "columnId";
        String invalidValue = "invalidValue";

        ListResponse listResponse = mock(ListResponse.class);
        Row row = new Row();
        Column column = new ColumnBuilder(ColumnType.NUMBER, "column").build();

        when(listsService.findById(listId)).thenReturn(listResponse);
        when(listsService.findColumnById(columnId)).thenReturn(column);
        when(rowRepository.findById(rowId)).thenReturn(Optional.of(row));


        // Act & Assert
        assertThrows(InvalidValueException.class, () -> listService.updateRow(listId, rowId, columnId, invalidValue));
    }

    @Test
    void updateCell_ShouldUpdateCellValue_WhenValueIsValid() throws InvalidValueException {
        // Arrange
        String rowId = "rowId";
        String columnId = "columnId";
        String newValue = "newValue";

        Row row = new Row();
        Column column = new ColumnBuilder(ColumnType.TEXT, "column").build();
        column.setId(columnId);
        IValue value = ValueFactory.create(newValue);

        Cell cell = new Cell();  // Use a real Cell instance
        cell.setColumn(column);
        cell.setValue(value);  // Set initial value for verification

        List<Cell> cells = new ArrayList<>(Collections.singletonList(cell));
        row.setCells(cells);  // Add cell to row

        when(listsService.findColumnById(columnId)).thenReturn(column);
        when(rowRepository.findById(rowId)).thenReturn(Optional.of(row));

        // Act
        listService.updateCell(rowId, columnId, newValue);

        // Assert
        assertEquals(value.get(), cell.getValue().get());  // Verify that the cell value was updated correctly
        verify(cellRepository).saveAll(Collections.singletonList(cell));  // Verify that saveAll was called with the updated cell
    }


    @Test
    void updateCell_ShouldThrowInvalidValueException_WhenValueIsInvalid() {
        // Arrange
        String rowId = "rowId";
        String columnId = "columnId";
        String invalidValue = "invalidValue";

        Row row = new Row();
        Column column = new ColumnBuilder(ColumnType.NUMBER, "column").build();

        when(listsService.findColumnById(columnId)).thenReturn(column);
        when(rowRepository.findById(rowId)).thenReturn(Optional.of(row));

        // Act & Assert
        assertThrows(InvalidValueException.class, () -> listService.updateCell(rowId, columnId, invalidValue));
    }

    @Test
    void testDeleteRow() {
        String listId = "list1";
        String rowId = "row1";

        // Given
        when(listsService.findById(listId)).thenReturn(new ListResponse());

        // When
        ListResponse result = listService.deleteRow(listId, rowId);

        // Then
        verify(rowRepository).deleteById(rowId); // Verify interaction with the mock repository
        verify(cellRepository).deleteByRowId(rowId); // Verify interaction with the mock repository
        assertNotNull(result);
    }


    // Test cases for View
    @Test
    void createView_shouldCreateAndReturnUpdatedListResponse() {
        // Given
        String listId = "listId";
        ViewRequest viewRequest = new ViewRequest();
        viewRequest.setName("Test View");
        viewRequest.setType(ViewType.LIST);


        ListResponse mockListResponse = new ListResponse();
        MicrosoftList mockList = new MicrosoftList();
        mockList.setId(listId);
        mockListResponse.setId(listId);
        when(listsService.findById(listId)).thenReturn(mockListResponse);

        View view = new View();
        view.setName(viewRequest.getName());
        view.setType(ViewFactory.create(viewRequest.getType()));
        view.setList(mockList);

        // Mocking the save method
        when(viewRepository.save(any(View.class))).thenReturn(view);

        // When
        ListResponse updatedListResponse = listService.createView(listId, viewRequest);

        // Then
        verify(viewRepository, times(1)).save(any(View.class));
        assertNotNull(updatedListResponse);
        assertEquals(listId, updatedListResponse.getId());
    }

    @Test
    void updateView_shouldUpdateAndReturnUpdatedListResponse() {
        String listId = "listId";
        String viewId = "viewId";
        ViewRequest viewRequest = new ViewRequest();
        viewRequest.setName("Updated View");
        viewRequest.setType(ViewType.CALENDAR);
        Map<ViewConfig, String> configs =
                Map.of(ViewConfig.LAYOUT, "MONTH",
                        ViewConfig.START_DATE, "2021-08-01",
                        ViewConfig.END_DATE, "2021-08-31");
        viewRequest.setConfigs(configs);

        ListResponse mockListResponse = new ListResponse();
        mockListResponse.setId(listId);
        mockListResponse.setViews(List.of(MapperUtil.mapper.map(viewRequest, ViewResponse.class)));
        when(listsService.findById(listId)).thenReturn(mockListResponse);

        View existingView = new View();
        existingView.setName("Old View");
        existingView.setType(ViewFactory.create(ViewType.LIST));

        when(viewRepository.findById(viewId)).thenReturn(Optional.of(existingView));

        View updatedView = new View();
        updatedView.setName(viewRequest.getName());
        updatedView.setType(ViewFactory.create(viewRequest.getType()));

        when(viewRepository.save(any(View.class))).thenReturn(existingView);

        // When
        ListResponse updatedListResponse = listService.updateView(listId, viewId, viewRequest);

        // Then
        verify(viewRepository, times(1)).save(any(View.class));
        assertNotNull(updatedListResponse);
        assertEquals(listId, updatedListResponse.getId());
        assertEquals(viewRequest.getName(), updatedListResponse.getViews().get(0).getName());
    }

    @Test
    void deleteView_shouldRemoveViewAndReturnUpdatedListResponse() {
        // Given
        String listId = "listId";
        String viewId = "viewId";

        ListResponse mockListResponse = new ListResponse();
        mockListResponse.setId(listId);
        when(listsService.findById(listId)).thenReturn(mockListResponse);

        doNothing().when(viewRepository).deleteById(viewId);

        // When
        ListResponse updatedListResponse = listService.deleteView(listId, viewId);

        // Then
        verify(viewRepository, times(1)).deleteById(viewId);
        assertNotNull(updatedListResponse);
        assertEquals(listId, updatedListResponse.getId());
    }




}
