package microsoftlists;

import config.Configuration;
import model.constants.ConfigParameter;
import model.constants.DateTime;
import model.constants.NumberSymbol;
import model.constants.TypeColumn;
import model.microsoftlist.MicrosoftList;
import model.microsoftlist.Parameter;
import model.microsoftlist.Row;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ListService;
import service.builder.ColumnBuilder;
import service.builder.MicrosoftListBuilder;
import util.JsonUtil;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestList {
    // Init a list for testing
    MicrosoftList list;

    // Set up before each test
    @BeforeEach
    void setUp() {

        // Init 11 columns with different types
        var colText = new ColumnBuilder(TypeColumn.TEXT, "Column Text")
                .configure(Parameter.of(ConfigParameter.DEFAULT_VALUE, "default text"))
                .build();

        var colNumber = new ColumnBuilder(TypeColumn.NUMBER, "Column Number")
                .configure(
                        Parameter.of(ConfigParameter.NUMBER_SYMBOL, NumberSymbol.NONE),
                        Parameter.of(ConfigParameter.DEFAULT_VALUE, 0))
                .build();

        var colDate = new ColumnBuilder(TypeColumn.DATE, "Column Date")
                .configure(Parameter.of(ConfigParameter.DEFAULT_VALUE, DateTime.CURRENT_DATE))
                .build();

        var colYesNo = new ColumnBuilder(TypeColumn.CHECKBOX, "Column YesNo")
                .configure(Parameter.of(ConfigParameter.DEFAULT_VALUE, false))
                .build();

        var colChoice = new ColumnBuilder(TypeColumn.CHOICE, "Column Choice")
                .configure(
                        Parameter.of(ConfigParameter.DEFAULT_VALUE, "choice 1", "choice 2", "choice 3"),
                        Parameter.of(ConfigParameter.MULTIPLE_SELECTION, true)
                )
                .build();

        var colHyperlink = new ColumnBuilder(TypeColumn.HYPERLINK, "Column Hyperlink")
                .configure(Parameter.of(ConfigParameter.DEFAULT_VALUE, "https://www.google.com"))
                .build();

        var colImage = new ColumnBuilder(TypeColumn.IMAGE, "Column Image")
                .configure(Parameter.of(ConfigParameter.DEFAULT_VALUE, "https://www.google.com/image.jpg"))
                .build();

        var colPerson = new ColumnBuilder(TypeColumn.PERSON, "Column Person")
                .configure(Parameter.of(ConfigParameter.DEFAULT_VALUE, "person 1"))
                .build();

        var colMultipleLinesOfText = new ColumnBuilder(TypeColumn.MULTIPLE_LINES_OF_TEXT,
                "Column Multiple Lines of Text")
                .configure(Parameter.of(ConfigParameter.DEFAULT_VALUE, "default text"))
                .build();

        var colLookup = new ColumnBuilder(TypeColumn.LOOKUP, "Column Lookup")
                .configure(Parameter.of(ConfigParameter.DEFAULT_VALUE, "lookup 1"))
                .build();

        var colAverageRating = new ColumnBuilder(TypeColumn.AVERAGE_RATING, "Column Average Rating")
                .configure(Parameter.of(ConfigParameter.DEFAULT_VALUE, 0))
                .build();


        // Create a list with the 11 columns
        this.list = new MicrosoftListBuilder("Test Create Blank List")
                .description("blank list")
                .color(Color.RED)
                .icon("ICON1")
                .initDefaultColumn()
                .addColumns(
                        colText,
                        colNumber,
                        colDate,
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

        var defaultVal =
                ListService.getColConfigValue(list, "Column Date", ConfigParameter.DEFAULT_VALUE);
        // Check if the default value of the new column is the current date
        assertEquals(date,  defaultVal.get(0));

    }

    // Test add a column with the same type as an existing column
    @Test
    void testAddColumnSameType() {

        var colsCount = ListService.getColumnsCount(list);

        var colText = ListService.getColumn(list, "Column Text");
        assertNotNull(colText);

        var newCol = new ColumnBuilder(TypeColumn.TEXT, "Column Text 2").build();
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
        var newCol = new ColumnBuilder(TypeColumn.TEXT, "Column Text")
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

        var readJson = JsonUtil.fromJsonFile(Configuration.DIR_PATH
                        + list.getName().replace(" ", "_")
                ,Configuration.DATA_PATH , List.class);
        assertEquals(JsonUtil.toJson(readJson), JsonUtil.toJson(List.of(firstRow)));

    }

    @Test
    // Add row with values
void testAddRowWithValues() {
        var rowsCount = ListService.getRowsCount(list);

        ListService.addRow(list, "text", 1, "2021-01-01", true, "choice 1",
                "https://www.google.com", "https://www.google.com/image.jpg",
                "person 1", "default text", "lookup 1", 0);
        var currRowsCount = ListService.getRowsCount(list);
        assertEquals(rowsCount + 1, currRowsCount);

        var firstRow = ListService.getRow(list, 0);
        assertEquals(row.getCells(), firstRow.getCells());
    }






}
