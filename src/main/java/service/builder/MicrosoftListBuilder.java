package service.builder;

import model.constants.TypeColumn;
import model.microsoftlist.Column;
import model.microsoftlist.MicrosoftList;
import model.microsoftlist.Type;

import java.awt.*;
import java.util.List;

public class MicrosoftListBuilder {
    MicrosoftList listItem;
    public MicrosoftListBuilder() {
        listItem = new MicrosoftList();
    }



    public MicrosoftList build() {
        return listItem;
    }

    public MicrosoftListBuilder name(String name) {
        listItem.setName(name);
        return this;
    }

    public MicrosoftListBuilder description(String descr) {
        listItem.setDescription(descr);
        return this;
    }

    public MicrosoftListBuilder color(Color color) {
        listItem.setColor(color);
        return this;
    }

    public MicrosoftListBuilder icon(String iconId) {
        listItem.setIconId(iconId);
        return this;
    }

    public MicrosoftListBuilder initDefaultColumn() {
        listItem.addColumn(new Column(listItem, "Title", new Type(TypeColumn.TEXT)));
        return this;
    }

    public MicrosoftListBuilder addColumns(List<Column> columns) {
        for (Column column : columns) {
            listItem.addColumn(column);
        }
        return this;
    }
}
