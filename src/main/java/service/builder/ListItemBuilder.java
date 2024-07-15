package service.builder;

import model.constants.TypeColumn;
import model.listitem.Column;
import model.listitem.ListItem;
import model.listitem.Type;

import java.awt.*;

public class ListItemBuilder {
    ListItem listItem;
    public ListItemBuilder() {
        listItem = new ListItem();
    }



    public ListItem build() {
        return listItem;
    }

    public ListItemBuilder name(String name) {
        listItem.setName(name);
        return this;
    }

    public ListItemBuilder description(String descr) {
        listItem.setDescription(descr);
        return this;
    }

    public ListItemBuilder color(Color color) {
        listItem.setColor(color);
        return this;
    }

    public ListItemBuilder icon(String iconId) {
        listItem.setIconId(iconId);
        return this;
    }

    public ListItemBuilder initDefaultColumn() {
        listItem.addColumn(new Column(listItem, "Title", new Type(TypeColumn.TEXT), false));
        return this;
    }
}
