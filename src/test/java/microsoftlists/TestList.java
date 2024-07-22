package microsoftlists;

import com.fasterxml.jackson.core.JsonProcessingException;
import config.Configuration;
import model.constants.*;
import model.microsoft.list.MicrosoftList;
import model.microsoft.list.Parameter;
import model.microsoft.list.view.BoardView;
import model.microsoft.list.view.CalendarView;
import model.microsoft.list.view.GalleryView;
import model.microsoft.list.view.ListView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ListService;
import service.RowService;
import service.builder.ColumnBuilder;
import service.builder.MicrosoftListBuilder;
import service.file.OpenService;
import util.JsonUtil;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TestList {
    // Init a list for testing
    MicrosoftList list;

    // Set up before each test
    @BeforeEach
    void setUp() {

        // Init 11 columns with different types
        var colText = new ColumnBuilder(ColumnType.TEXT, "Column Text")
                .build();

        var colNumber = new ColumnBuilder(ColumnType.NUMBER, "Column Number")
                .configure(
                        Parameter.of(ConfigParameter.NUMBER_SYMBOL, NumberSymbol.NONE))
                .build();

        var colDate = new ColumnBuilder(ColumnType.DATE, "Column Date")
                .configure(Parameter.of(ConfigParameter.DEFAULT_VALUE, DateTime.CURRENT_DATE))
                .build();

        var colYesNo = new ColumnBuilder(ColumnType.CHECKBOX, "Column YesNo")
                .build();

        var colChoice = new ColumnBuilder(ColumnType.CHOICE, "Column Choice")
                .configure(
                        Parameter.of(ConfigParameter.CHOICES, "choice 1","choice 2", "choice 3"),
                        Parameter.of(ConfigParameter.MULTIPLE_SELECTION, false)
                )
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
                .configure(Parameter.of(ConfigParameter.DEFAULT_VALUE, 0))
                .build();


        // Create a list with the 11 columns
        this.list = new MicrosoftListBuilder("Test Create Blank List")
                .description("blank list")
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
    }

    @Test
    void testDefaultConfigOfDateColumn() {

        assertNotNull(ListService.getColumn(list, "Column Date"));

        // Get current date
        var currDate = LocalDate.now();
        var format = DateTimeFormatter.ofPattern(Configuration.DATETIME_FORMAT);
        var date = currDate.format(format);

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
        assertThrows(IllegalArgumentException.class, () -> ListService.addColumn(list, newCol));

        // Check if the number of columns has not changed
        var currColsCount = ListService.getColumnsCount(list);
        assertEquals(colsCount, currColsCount);
    }

    @Test
    // Add row
    void testAddRow() throws IOException {
        var rowsCount = ListService.getRowsCount(list);

        ListService.addRow(list);
        var currRowsCount = ListService.getRowsCount(list);
        assertEquals(rowsCount + 1, currRowsCount);

        var firstRow = ListService.getRow(list, 0);

        var readJson = OpenService.openFile((Configuration.DIR_PATH
                        + list.getName().replace(" ", "_"))
                ,Configuration.DATA_PATH , List.class);
        assertEquals(JsonUtil.toJson(readJson), JsonUtil.toJson(List.of(firstRow)));

        // test if the column have default value
        ListService.settingColumn(list, "Column Text",
                new ColumnBuilder(ColumnType.TEXT, "Column Text 2")
                        .configure(Parameter.of(ConfigParameter.DEFAULT_VALUE, "default text"))
                        .build());
        ListService.addRow(list);
        var secondRow = ListService.getRow(list, 1);
        assertEquals("default text", RowService.getCell(secondRow, "Column Text 2"));

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
        String inputJson = JsonUtil.toJson(firstInput);
        ListService.addRow(list, inputJson);

        var secondInput = new HashMap<>(Map.of(
                "Column Text", "text 2",
                "Column Number", 1,
                "Column YesNo", true
        ));

        // Convert the second input to JSON
        inputJson = JsonUtil.toJson(secondInput);
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
        var inputJson = JsonUtil.toJson(input);
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
        var inputJson = JsonUtil.toJson(input);
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
        var numPagesExpected = 10;
        assertEquals(numPagesExpected, firstPage.size());

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
        var inputJson = JsonUtil.toJson(input);
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
    // Test load data of list from config columns
    void testFilterDataFromConfig() {
        var curColsCount = ListService.getColumnsCount(list);

        ListService.addRow(list);
        assertEquals(1, ListService.getRowsCount(list));

        ListService.settingColumn(list, "Column Text",
                new ColumnBuilder(ColumnType.TEXT, "Column Text 2")
                        .isHidden(true)
                        .build());

        MicrosoftList listFilter = ListService.loadList(list);
        assertNotNull(listFilter);

        var colsFilter = listFilter.getColumns();
        assertEquals(curColsCount - 1, colsFilter.size());

        var rowsFilter = listFilter.getRows();
        assertEquals(1, rowsFilter.size());

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
        var inputJson = JsonUtil.toJson(input);
        ListService.addRow(list, inputJson);

        input = new HashMap<>(Map.of(
                "Column Text", "Hi",
                "Column Number", 2,
                "Column YesNo", true
        ));
        inputJson = JsonUtil.toJson(input);
        ListService.addRow(list, inputJson);

        input = new HashMap<>(Map.of(
                "Column Text", "Xin chao",
                "Column Number", 1,
                "Column YesNo", false
        ));
        inputJson = JsonUtil.toJson(input);
        ListService.addRow(list, inputJson);

        input = new HashMap<>(Map.of(
                "Column Text", "Hello 3",
                "Column Number", 2,
                "Column YesNo", true
        ));
        inputJson = JsonUtil.toJson(input);
        ListService.addRow(list, inputJson);

        input = new HashMap<>(Map.of(
                "Column Text", "HelloHello",
                "Column Number", 2,
                "Column YesNo", true
        ));
        inputJson = JsonUtil.toJson(input);
        ListService.addRow(list, inputJson);

        assertEquals(5, ListService.getRowsCount(list));

        var searchResult = ListService.search(list, "hello");
        assertEquals(3, searchResult.size());

        var searchResult2 = ListService.search(list, "1");
        assertEquals(2, searchResult2.size());

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
                "Column Date", "05-01-2024",
                "Column YesNo", true
        ));
        var inputJson = JsonUtil.toJson(input);
        ListService.addRow(list, inputJson);

        input = new HashMap<>(Map.of(
                "Column Text", "C",
                "Column Number", 2,
                "Column Date", "02-01-2024",
                "Column YesNo", true
        ));
        inputJson = JsonUtil.toJson(input);
        ListService.addRow(list, inputJson);

        input = new HashMap<>(Map.of(
                "Column Text", "D",
                "Column Number", 1,
                "Column Date", "01-01-2024",
                "Column YesNo", true
        ));
        inputJson = JsonUtil.toJson(input);
        ListService.addRow(list, inputJson);

        input = new HashMap<>(Map.of(
                "Column Text", "A",
                "Column Number", 4,
                "Column Date", "09-01-2024",
                "Column YesNo", true
        ));
        inputJson = JsonUtil.toJson(input);
        ListService.addRow(list, inputJson);

        input = new HashMap<>(Map.of(
                "Column Text", "B",
                "Column Number", 2,
                "Column Date", "01-01-2024",
                "Column YesNo", true
        ));
        inputJson = JsonUtil.toJson(input);
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
        assertEquals("01-01-2024", RowService.getCell(sortedRows.get(0), "Column Date"));

        sortedRows = ListService.sort(list, "Column Date", SortOrder.DESCENDING);
        assertEquals("09-01-2024", RowService.getCell(sortedRows.get(0), "Column Date"));
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
