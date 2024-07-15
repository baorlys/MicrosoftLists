package microsoftlists;

import model.listitem.ListItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ListItemService;
import service.builder.ListItemBuilder;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestList {
    ListItem listItem;
    @Test
    void testCreateBlankListItem() {
        ListItem listItem = new ListItemBuilder()
                .name("Test Create Blank List")
                .description("blank list")
                .color(Color.RED)
                .icon("ICON1")
                .build();
        assertEquals("Test Create Blank List", listItem.getName());
        assertEquals("blank list", listItem.getDescription());
        assertEquals(Color.RED, listItem.getColor());
        assertEquals("ICON1", listItem.getIconId());
        assertEquals(1, ListItemService.getColumnsCount(listItem));
    }
    @BeforeEach
    public void setUp() {
        ListItem listItem = new ListItemBuilder().build();
    }
}
