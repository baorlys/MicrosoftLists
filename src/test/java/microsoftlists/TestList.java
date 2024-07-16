package microsoftlists;

import model.constants.DateTime;
import model.constants.NumberSymbol;
import model.constants.TypeColumn;
import model.microsoftlist.MicrosoftList;
import model.microsoftlist.Parameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.RowService;
import service.ListService;
import service.builder.ColumnBuilder;
import service.builder.MicrosoftListBuilder;

import java.awt.*;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestList {
    MicrosoftList list;
    @Test
    void testCreateBlankListItem() {

        list = new MicrosoftListBuilder()
                .name("Test Create Blank List")
                .description("blank list")
                .color(Color.RED)
                .icon("ICON1")
                .initDefaultColumn()
                .build();

        assertEquals("Test Create Blank List", list.getName());
        assertEquals("blank list", list.getDescription());
        assertEquals(Color.RED, list.getColor());
        assertEquals("ICON1", list.getIconId());
        assertEquals(1, ListService.getColumnsCount(list));
    }

    // Set up before each test
    @BeforeEach
    void setUp() {

        list = new MicrosoftListBuilder()
                .name("Test Create Blank List")
                .description("blank list")
                .color(Color.RED)
                .icon("ICON1")
                .initDefaultColumn()
                .addColumns(
                        List.of(
                                new ColumnBuilder()
                                        .type(TypeColumn.TEXT)
                                        .name("Column Text")
                                        .configure(List.of(
                                                new Parameter("defaultVal", "default text"))
                                        )
                                        .build(),
                                new ColumnBuilder()
                                        .type(TypeColumn.NUMBER)
                                        .name("Column Number")
                                        .configure(List.of(
                                                new Parameter("defaultVal", 0),
                                                new Parameter("symbol", NumberSymbol.NONE))
                                        )
                                        .build()
                        )
                )
                .build();


    }

    @Test
    void testAddColumn() {

        long columnsCount = ListService.getColumnsCount(list);

        ListService.addColumn(list,
                new ColumnBuilder()
                        .type(TypeColumn.DATE)
                        .name("Column Date")
                        .configure(List.of(
                                new Parameter("defaultVal", DateTime.CURRENT_DATE))
                        )
                        .build());

        assertEquals(columnsCount + 1, ListService.getColumnsCount(list));

        String date = Date.from(new Date().toInstant()).toString();
        assertEquals("{\"typeColumn\":\"DATE\",\"config\":[{\"name\":\"defaultVal\",\"value\":\""+date+"\"}]}"
                , ListService.getColumnConfig(list, "Column Date"));

    }

    @Test
    void testAddColumnSameName() {

        long columnsCount = ListService.getColumnsCount(list);

        ListService.addColumn(list,
                new ColumnBuilder()
                        .type(TypeColumn.DATE)
                        .name("Column Text")
                        .build());

        assertEquals(columnsCount, ListService.getColumnsCount(list));

        assertEquals("{\"typeColumn\":\"TEXT\",\"config\":[{\"name\":\"defaultVal\",\"value\":\"default text\"}]}"
                , ListService.getColumnConfig(list, "Column Text"));

    }

    @Test
    void testAddRow() {

        long countRows = ListService.getRowsCount(list);

        ListService.addNewRow(list);
        assertEquals(countRows + 1, ListService.getRowsCount(list));

        // Check if the row has the same number of cells as the number of columns
        assertEquals(ListService.getColumnsCount(list), ListService.getRow(list, 0).getCells().size());
    }

    @Test
    void testAddDataToCell() {

        ListService.addNewRow(list);

        var firstRow = ListService.getRow(list, 0);
        var firstCell = RowService.getCell(firstRow, 0);
        var secondCell = RowService.getCell(firstRow, 1);

        ListService.updateCellData(list, firstRow, firstCell.getId(),  "text");
        assertEquals("text", list.getRows().get(0).getCells().get(0).getValue());

        ListService.updateCellData(list, firstRow, secondCell.getId(), 1);
        assertEquals(1, list.getRows().get(0).getCells().get(1).getValue());
    }

    @Test
    void testUpdateDataOfCell() {

        ListService.addNewRow(list);

        var firstItem = ListService.getRow(list, 0);
        assertNotNull(firstItem);

        var firstCell = RowService.getCell(firstItem, 0);
        assertNotNull(firstCell);

        var secondCell = RowService.getCell(firstItem, 1);
        assertNotNull(secondCell);

        ListService.updateCellData(list, firstItem, firstCell.getId(),  "text");
        assertEquals("text", list.getRows().get(0).getCells().get(0).getValue());

        ListService.updateCellData(list, firstItem, secondCell.getId(), 1);
        assertEquals(1, list.getRows().get(0).getCells().get(1).getValue());

        ListService.updateCellData(list, firstItem, firstCell.getId(),  "new text");
        assertEquals("new text", list.getRows().get(0).getCells().get(0).getValue());

        ListService.updateCellData(list, firstItem, secondCell.getId(), 2);
        assertEquals(2, list.getRows().get(0).getCells().get(1).getValue());
    }




}
