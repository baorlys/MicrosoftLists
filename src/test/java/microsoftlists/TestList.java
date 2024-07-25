package microsoftlists;

import org.example.microsoftlists.model.constants.CalendarLayout;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.constants.ConfigParameter;
import org.example.microsoftlists.model.constants.MessageType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.model.microsoft.list.MicrosoftList;
import org.example.microsoftlists.model.microsoft.list.Parameter;
import org.example.microsoftlists.model.microsoft.list.view.BoardView;
import org.example.microsoftlists.model.microsoft.list.view.CalendarView;
import org.example.microsoftlists.model.microsoft.list.view.GalleryView;
import org.example.microsoftlists.model.microsoft.list.view.ListView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.microsoftlists.service.ListService;
import org.example.microsoftlists.service.RowService;
import org.example.microsoftlists.service.builder.ColumnBuilder;
import org.example.microsoftlists.service.builder.MicrosoftListBuilder;
import org.example.microsoftlists.service.file.JsonService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TestList {
    // Init a list for testing
    MicrosoftList list;

//     Set up before each test
    @BeforeEach
    void setUp() throws IOException {
        // Create a list
        list = new MicrosoftListBuilder("Test List")
                .color(Color.BLUE)
                .icon("ICON")
                .initDefaultColumn()
                .build();

    }
    @Test
        // Add row with values
    void testAddRow2() throws JsonProcessingException {
        var rowsCount = ListService.getRowsCount(list);

        var firstInput = new HashMap<>(Map.of(
                "Title", "test",
                "Column Date", "2021-01-01"
        ));

        // Convert the first input to JSON
        String inputJson = JsonService.toJson(firstInput);
        ListService.addRow(list, inputJson);
    }

    @Test
        // Add row
    void testRow() throws IOException {
        list.getRows();


    }


    @Test
    // Test create a list
    void testCreateList() {
        // Init 11 columns with different types
        var colText = new ColumnBuilder(ColumnType.TEXT, "Column Text")
                .build();

        var colNumber = new ColumnBuilder(ColumnType.NUMBER, "Column Number")

                .build();

        var colDate = new ColumnBuilder(ColumnType.DATE, "Column Date")
                .build();

        var colYesNo = new ColumnBuilder(ColumnType.CHECKBOX, "Column YesNo")
                .build();

        var colChoice = new ColumnBuilder(ColumnType.CHOICE, "Column Choice")

                .build();

        var colHyperlink = new ColumnBuilder(ColumnType.HYPERLINK, "Column Hyperlink")
                .build();

        var colImage = new ColumnBuilder(ColumnType.IMAGE, "Column Image")
                .build();

        var colPerson = new ColumnBuilder(ColumnType.PERSON, "Column Person")
                .build();

        var colMultipleLinesOfText = new ColumnBuilder(ColumnType.MULTIPLE_LINES_OF_TEXT,
                "Column Multiple Lines of Text")
                .build();

        var colLookup = new ColumnBuilder(ColumnType.LOOKUP, "Column Lookup")
                .build();

        var colAverageRating = new ColumnBuilder(ColumnType.AVERAGE_RATING, "Column Average Rating")
                .build();


        // Create a list with the 11 columns
        MicrosoftList testCreated = new MicrosoftListBuilder("Test Create List")
                .color(Color.RED)
                .icon("ICON1")
                .initDefaultColumn()
                .addColumns(
                        colDate,
                        colText,
                        colNumber,
                        colYesNo,
                        colChoice,
                        colHyperlink,
                        colImage,
                        colPerson,
                        colMultipleLinesOfText,
                        colLookup,
                        colAverageRating)
                .build();

        assertNotNull(testCreated);
        assertEquals("Test Create List", testCreated.getName());
        assertEquals(Color.RED, testCreated.getColor());
        assertEquals(12, ListService.getColumnsCount(testCreated));
    }
    @Test
    void testDefaultConfigOfDateColumn() {

        assertNotNull(ListService.getColumn(list, "Column Date"));

        // Get current date
        var currDateTime = LocalDateTime.now();
        // Define the desired format
        var format = DateTimeFormatter.ofPattern(Configuration.DATETIME_FORMAT);
        // Format the current date and time
        var date = currDateTime.format(format);

        var defaultValues =
                ListService.getColConfigValue(list, "Column Date", ConfigParameter.DEFAULT_VALUE);
        // Check if the default value of the new column is the current date
        assertEquals(date,  defaultValues);

    }

    // Test add a column with the same type as an existing column
    @Test
    void testAddColumnSameType() {

        var colsCount = ListService.getColumnsCount(list);

        var colText = ListService.getColumn(list, "Column Text");
        assertNotNull(colText);

        var newCol = new ColumnBuilder(ColumnType.TEXT, "Column Text 2").build();
        ListService.addColumn(list, newCol);
        var currColsCount = ListService.getColumnsCount(list);
        assertEquals(colsCount + 1, currColsCount);
    }


    // Test adding a column with the same name as an existing column
    @Test()
    void testAddColumnSameName() {

        // Get the number of columns before adding a new column
        var colsCount = ListService.getColumnsCount(list);

        // Check if Column Text already exists
        var colText = ListService.getColumn(list, "Column Text");
        assertNotNull(colText);

        // Create a new column with the same name as the existing column
        var newCol = new ColumnBuilder(ColumnType.TEXT, "Column Text")
                .build();

        // Check if adding a column with the same name as an existing column throws an exception
        var resultMessage = ListService.addColumn(list, newCol);
        assertEquals(MessageType.ERROR, resultMessage.getType());
        assertEquals("Column already exists", resultMessage.getMsg());
        // Check if the number of columns has not changed
        var currColsCount = ListService.getColumnsCount(list);
        assertEquals(colsCount, currColsCount);
    }





    @Test
    // Add row with values
    void testAddRowWithValues() throws JsonProcessingException {
        var rowsCount = ListService.getRowsCount(list);

        var firstInput = new HashMap<>(Map.of(
                "Column Text", "text",
                "Column Number", 1,
                "Column Date", "2021-01-01",
                "Column YesNo", true,
                "Column Choice", "choice 1",
                "Column Hyperlink", "https://www.google.com",
                "Column Image", "https://www.google.com/image.jpg",
                "Column Person", "person 1",
                "Column Multiple Lines of Text", "default text",
                "Column Lookup", "lookup 1"
        ));
        firstInput.put("Column Average Rating", 0);

        // Convert the first input to JSON
        String inputJson = JsonService.toJson(firstInput);
        ListService.addRow(list, inputJson);

        var secondInput = new HashMap<>(Map.of(
                "Column Text", "text 2",
                "Column Number", 1,
                "Column YesNo", true
        ));

        // Convert the second input to JSON
        inputJson = JsonService.toJson(secondInput);
        ListService.addRow(list, inputJson);

        var currRowsCount = ListService.getRowsCount(list);

        assertEquals(rowsCount + 2, currRowsCount);

    }

    @Test
    // Test updating a row
    void testUpdateRow() throws JsonProcessingException {

        var rowsCount = ListService.getRowsCount(list);
        assertEquals(0, rowsCount);

        // Add a row with values
        var input = new HashMap<>(Map.of(
                "Column Text", "text 2",
                "Column Number", 1,
                "Column YesNo", true
        ));
        var inputJson = JsonService.toJson(input);
        ListService.addRow(list, inputJson);

        assertEquals(1, ListService.getRowsCount(list));
        assertEquals("text 2", ListService.getValue(list, 0, "Column Text"));

        // Update the row
        ListService.updateCellAtRow(list, 0, "Column Text", "text 3");
        assertEquals("text 3", ListService.getValue(list, 0, "Column Text"));

    }

    @Test
    // Test updating a row with choices column
    void testUpdateRowWithChoicesColumn() throws JsonProcessingException {
        var rowsCount = ListService.getRowsCount(list);
        assertEquals(0, rowsCount);

        // Add a row with values
        var input = new HashMap<>(Map.of(
                "Column Text", "text 2",
                "Column Number", 1,
                "Column YesNo", true,
                "Column Choice", "choice 1"
        ));
        var inputJson = JsonService.toJson(input);
        ListService.addRow(list, inputJson);

        int rowIndex = (int) (ListService.getRowsCount(list) - 1);

        assertEquals(1, ListService.getRowsCount(list));
        assertEquals("choice 1", ListService.getValue(list, rowIndex, "Column Choice"));

        // Update the row
        var msgResult = ListService.updateCellAtRow(list, 0, "Column Choice", "choice 2");
        assertEquals(MessageType.SUCCESS, msgResult.getType());
        assertEquals("choice 2", ListService.getValue(list, rowIndex, "Column Choice"));

        // Update the row with invalid value
        msgResult = ListService.updateCellAtRow(list, rowIndex, "Column Choice",  "choice 4", "choice 2");
        assertEquals(MessageType.ERROR, msgResult.getType());
        assertEquals("Invalid value", msgResult.getMsg());

        // Test multiple choices
        ListService.settingColumn(list, "Column Choice",
                new ColumnBuilder(ColumnType.CHOICE, "Column Choice")
                        .configure(
                                Parameter.of(ConfigParameter.CHOICES, "choice 1","choice 2", "choice 3"),
                                Parameter.of(ConfigParameter.MULTIPLE_SELECTION, true))
                        .build());

        ListService.updateCellAtRow(list, rowIndex, "Column Choice",  "choice 1", "choice 3");
        assertEquals(List.of("choice 1", "choice 3"), ListService.getValue(list, rowIndex, "Column Choice"));
    }

    @Test
    // Test paging of rows
    void testPaging() {
        var rowsCount = ListService.getRowsCount(list);

        // Add 50 rows
        for (int i = 0; i < 50; i++) {
            ListService.addRow(list);
        }
        var currRowsCount = ListService.getRowsCount(list);
        assertEquals(rowsCount + 50, currRowsCount);

        // Get the first 10 rows
        var firstPage = ListService.getPagedRows(list, 1,10);
        var pageCountExpected = 10;
        assertEquals(pageCountExpected, firstPage.size());

        var firstRowOfFirstPage = firstPage.get(0);
        var indexExpected = 10;
        assertEquals(indexExpected, ListService.getRowIndex(list, firstRowOfFirstPage));



    }


    @Test
    // Test deleting a column
    void testDeleteColumn() throws JsonProcessingException {
        var colsCount = ListService.getColumnsCount(list);

        // Check if Column Text already exists
        var colText = ListService.getColumn(list, "Column Text");
        assertNotNull(colText);

        // Add a row with values for Column Text
        var input = new HashMap<>(Map.of(
                "Column Text", "text 2",
                "Column Number", 1,
                "Column YesNo", true
        ));
        var inputJson = JsonService.toJson(input);
        ListService.addRow(list, inputJson);

        assertEquals(1, ListService.getRowsCount(list));
        assertEquals("text 2", ListService.getValue(list, 0, "Column Text"));

        ListService.deleteColumn(list, "Column Text");
        var currColsCount = ListService.getColumnsCount(list);
        assertEquals(colsCount - 1, currColsCount);

        var colTextAfterDelete = ListService.getColumn(list, "Column Text");
        assertNull(colTextAfterDelete);
        assertNull(ListService.getValue(list, 0, "Column Text"));
    }

    @Test
    // Test deleting a row
    void testDeleteRow() {
        var rowsCount = ListService.getRowsCount(list);

        ListService.addRow(list);
        var currRowsCount = ListService.getRowsCount(list);
        assertEquals(rowsCount + 1, currRowsCount);

        ListService.deleteRow(list, 0);
        var currRowsCountAfterDelete = ListService.getRowsCount(list);
        assertEquals(rowsCount, currRowsCountAfterDelete);
    }

    @Test
    // Test deleting multiple rows
    void testDeleteMultipleRows() {
        var rowsCount = ListService.getRowsCount(list);

        ListService.addRow(list);
        ListService.addRow(list);
        ListService.addRow(list);
        var currRowsCount = ListService.getRowsCount(list);
        assertEquals(rowsCount + 3, currRowsCount);

        ListService.deleteRows(list, List.of(0, 1));
        var currRowsCountAfterDelete = ListService.getRowsCount(list);
        assertEquals(rowsCount + 1, currRowsCountAfterDelete);
    }
    @Test
    // Setting the column
    void testSetColumn() {
        var colText = ListService.getColumn(list, "Column Text");
        assertNotNull(colText);

        // Set the column to hidden and change the name
        ListService.settingColumn(list, "Column Text",
                new ColumnBuilder(ColumnType.TEXT, "Column Text 2")
                        .isHidden(true)
                        .build());

        var colTextAfterSet = ListService.getColumn(list, "Column Text");
        assertNull(colTextAfterSet);

        var colText2 = ListService.getColumn(list, "Column Text 2");
        assertNotNull(colText2);
    }


    @Test
    // Search row data
    void testSearchRowData() throws JsonProcessingException {
        // Insert 5 rows to the list
        var input = new HashMap<>(Map.of(
                "Column Text", "Hello",
                "Column Number", 1,
                "Column YesNo", true
        ));
        var inputJson = JsonService.toJson(input);
        ListService.addRow(list, inputJson);

        input = new HashMap<>(Map.of(
                "Column Text", "Hi",
                "Column Number", 2,
                "Column YesNo", true
        ));
        inputJson = JsonService.toJson(input);
        ListService.addRow(list, inputJson);

        input = new HashMap<>(Map.of(
                "Column Text", "Xin chao",
                "Column Number", 1,
                "Column YesNo", false
        ));
        inputJson = JsonService.toJson(input);
        ListService.addRow(list, inputJson);

        input = new HashMap<>(Map.of(
                "Column Text", "Hello 1",
                "Column Number", 2,
                "Column YesNo", true
        ));
        inputJson = JsonService.toJson(input);
        ListService.addRow(list, inputJson);

        input = new HashMap<>(Map.of(
                "Column Text", "HelloHello",
                "Column Number", 2,
                "Column YesNo", true
        ));
        inputJson = JsonService.toJson(input);
        ListService.addRow(list, inputJson);

        assertEquals(5, ListService.getRowsCount(list));

        var searchResult = ListService.search(list, "hello");
        assertEquals(3, searchResult.size());

        var searchResult2 = ListService.search(list, "1");
        assertEquals(3, searchResult2.size());

        var searchResult3 = ListService.search(list, "true");
        assertEquals(4, searchResult3.size());
    }


    @Test
    // Test sorting rows
    void testSortRows() throws JsonProcessingException {
        // Insert 5 rows to the list
        var input = new HashMap<>(Map.of(
                "Column Text", "F",
                "Column Number", 1,
                "Column Date", "05-01-2024 00:00 AM",
                "Column YesNo", true
        ));
        var inputJson = JsonService.toJson(input);
        ListService.addRow(list, inputJson);

        input = new HashMap<>(Map.of(
                "Column Text", "C",
                "Column Number", 2,
                "Column Date", "02-01-2024 00:00 AM",
                "Column YesNo", true
        ));
        inputJson = JsonService.toJson(input);
        ListService.addRow(list, inputJson);

        input = new HashMap<>(Map.of(
                "Column Text", "D",
                "Column Number", 1,
                "Column Date", "01-01-2024 00:00 AM",
                "Column YesNo", true
        ));
        inputJson = JsonService.toJson(input);
        ListService.addRow(list, inputJson);

        input = new HashMap<>(Map.of(
                "Column Text", "A",
                "Column Number", 4,
                "Column Date", "09-01-2024 00:00 AM",
                "Column YesNo", true
        ));
        inputJson = JsonService.toJson(input);
        ListService.addRow(list, inputJson);

        input = new HashMap<>(Map.of(
                "Column Text", "B",
                "Column Number", 2,
                "Column Date", "01-01-2024 00:00 AM",
                "Column YesNo", true
        ));
        inputJson = JsonService.toJson(input);
        ListService.addRow(list, inputJson);

        assertEquals(5, ListService.getRowsCount(list));
        assertEquals("F", ListService.getValue(list, 0, "Column Text"));

        var sortedRows = ListService.sort(list, "Column Text", SortOrder.ASCENDING);
        assertEquals("A", RowService.getCell(sortedRows.get(0), "Column Text"));

        sortedRows = ListService.sort(list, "Column Text", SortOrder.DESCENDING);
        assertEquals("F", RowService.getCell(sortedRows.get(0), "Column Text"));

        sortedRows = ListService.sort(list, "Column Number", SortOrder.ASCENDING);
        assertEquals(1, RowService.getCell(sortedRows.get(0), "Column Number"));

        sortedRows = ListService.sort(list, "Column Number", SortOrder.DESCENDING);
        assertEquals(4, RowService.getCell(sortedRows.get(0), "Column Number"));

        sortedRows = ListService.sort(list, "Column Date", SortOrder.ASCENDING);
        assertEquals("01-01-2024 00:00 AM", RowService.getCell(sortedRows.get(0), "Column Date"));

        sortedRows = ListService.sort(list, "Column Date", SortOrder.DESCENDING);
        assertEquals("09-01-2024 00:00 AM", RowService.getCell(sortedRows.get(0), "Column Date"));
    }

    @Test
    // Test create view from list
    void testCreateView() {
        var listView = new ListView("View 1");
        var view = ListService.createView(list, listView);
        assertNotNull(view);
        assertEquals("View 1", ListService.getView(list,"View 1").getName());

        var colDate = ListService.getColumn(list, "Column Date");
        var calendarView = new CalendarView("Calendar View", CalendarLayout.MONTH, colDate, colDate);
        view = ListService.createView(list, calendarView);
        assertNotNull(view);
        assertEquals("Calendar View", ListService.getView(list,"Calendar View").getName());

        var galleryView = new GalleryView("Gallery View");
        view = ListService.createView(list, galleryView);
        assertNotNull(view);
        assertEquals("Gallery View", ListService.getView(list,"Gallery View").getName());

        var choiceCol = ListService.getColumn(list, "Column Choice");
        var boardView = new BoardView("Board View", choiceCol);
        view = ListService.createView(list, boardView);
        assertNotNull(view);
        assertEquals("Board View", ListService.getView(list,"Board View").getName());

        // Test create view with invalid organize column
        var colText = ListService.getColumn(list, "Column Text");
        assertThrows(IllegalArgumentException.class, () -> new BoardView("Board View 2", colText));


    }







}
