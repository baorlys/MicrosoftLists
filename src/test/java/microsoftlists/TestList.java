package microsoftlists;

import model.constants.NumberSymbol;
import model.constants.TypeColumn;
import model.listitem.ListItem;
import model.listitem.Parameter;
import model.listitem.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ItemService;
import service.ListItemService;
import service.builder.ColumnBuilder;
import service.builder.ListItemBuilder;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestList {
    ListItem listItem;
    @Test
    void testCreateBlankListItem() {

        ListItem listItem = new ListItemBuilder()
                .name("Test Create Blank List")
                .description("blank list")
                .color(Color.RED)
                .icon("ICON1")
                .initDefaultColumn()
                .build();

        assertEquals("Test Create Blank List", listItem.getName());
        assertEquals("blank list", listItem.getDescription());
        assertEquals(Color.RED, listItem.getColor());
        assertEquals("ICON1", listItem.getIconId());
        assertEquals(1, ListItemService.getColumnsCount(listItem));
    }
    @BeforeEach
    void setUp() {

        listItem = new ListItemBuilder()
                .name("Test Create Blank List")
                .description("blank list")
                .color(Color.RED)
                .icon("ICON1")
                .initDefaultColumn()
                .build();

        ListItemService.addColumn(listItem,
                new ColumnBuilder()
                        .type(new Type(TypeColumn.TEXT))
                        .name("Column Text")
                        .configure(List.of(
                                new Parameter("defaultVal", "default text"))
                        )
                        .build());

        ListItemService.addColumn(listItem,
                new ColumnBuilder()
                        .type(new Type(TypeColumn.NUMBER))
                        .name("Column Number")
                        .configure(List.of(
                                new Parameter("defaultVal", 0),
                                new Parameter("symbol", NumberSymbol.NONE))
                        )
                        .build());
    }

    @Test
    void testColumnConfig() {

        var columnText = ListItemService.getColumn(listItem, 1);
        assertEquals("Column Text", columnText.getName());
        assertEquals("{\"typeColumn\":\"TEXT\"," +
                        "\"config\":[{\"name\":\"defaultVal\",\"value\":\"default text\"}]}",
                columnText.getType().toString());

        var columnNumber = ListItemService.getColumn(listItem, 2);
        assertEquals("Column Number", columnNumber.getName());
        assertEquals("{\"typeColumn\":\"NUMBER\"," +
                        "\"config\":[{\"name\":\"defaultVal\",\"value\":0},{\"name\":\"symbol\",\"value\":\"NONE\"}]}",
                columnNumber.getType().toString());
    }

    @Test
    void testAddColumn() {

        ListItemService.addColumn(listItem,
                new ColumnBuilder()
                        .type(new Type(TypeColumn.DATE))
                        .name("Column Date")
                        .configure(List.of(
                                new Parameter("defaultVal", "default text"))
                        )
                        .build());

        assertEquals(4, ListItemService.getColumnsCount(listItem));


    }

    @Test
    void testAddItem() {

        ListItemService.addItem(listItem);
        assertEquals(1, listItem.getItems().size());
        assertEquals(3, listItem.getItems().get(0).getCells().size());
    }

    @Test
    void testAddDataToItem() {

        ListItemService.addItem(listItem);

        var firstItem = ListItemService.getItem(listItem, 0);
        var firstCell = ItemService.getCell(firstItem, 0);
        var secondCell = ItemService.getCell(firstItem, 1);

        ListItemService.updateCellData(listItem, firstItem, firstCell.getId(),  "text");
        assertEquals("text", listItem.getItems().get(0).getCells().get(0).getValue());

        ListItemService.updateCellData(listItem, firstItem, secondCell.getId(), 1);
        assertEquals(1, listItem.getItems().get(0).getCells().get(1).getValue());
    }

    @Test
    void testUpdateDataOfCell() {

        ListItemService.addItem(listItem);

        var firstItem = ListItemService.getItem(listItem, 0);
        assertNotNull(firstItem);

        var firstCell = ItemService.getCell(firstItem, 0);
        assertNotNull(firstCell);

        var secondCell = ItemService.getCell(firstItem, 1);
        assertNotNull(secondCell);

        ListItemService.updateCellData(listItem, firstItem, firstCell.getId(),  "text");
        assertEquals("text", listItem.getItems().get(0).getCells().get(0).getValue());

        ListItemService.updateCellData(listItem, firstItem, secondCell.getId(), 1);
        assertEquals(1, listItem.getItems().get(0).getCells().get(1).getValue());

        ListItemService.updateCellData(listItem, firstItem, firstCell.getId(),  "new text");
        assertEquals("new text", listItem.getItems().get(0).getCells().get(0).getValue());

        ListItemService.updateCellData(listItem, firstItem, secondCell.getId(), 2);
        assertEquals(2, listItem.getItems().get(0).getCells().get(1).getValue());
    }




}
