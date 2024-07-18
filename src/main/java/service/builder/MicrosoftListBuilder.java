package service.builder;

import config.Configuration;
import model.constants.TypeColumn;
import model.microsoftlist.Column;
import model.microsoftlist.MicrosoftList;
import service.file.SaveService;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class MicrosoftListBuilder {
    MicrosoftList list;
    public MicrosoftListBuilder(String name) {
        list = new MicrosoftList();
        list.setName(name);
    }



    public MicrosoftList build() {
        try {
            SaveService.saveStructure(list);
        } catch (IOException e) {
            Logger.getAnonymousLogger().warning("Error saving list structure");
        }
        return list;
    }


    public MicrosoftListBuilder description(String descr) {
        list.setDescription(descr);
        return this;
    }

    public MicrosoftListBuilder color(Color color) {
        list.setColor(color);
        return this;
    }

    public MicrosoftListBuilder icon(String iconId) {
        list.setIconId(iconId);
        return this;
    }

    public MicrosoftListBuilder initDefaultColumn() {
        list.addColumn(new ColumnBuilder(TypeColumn.TEXT, Configuration.COLUMN_NAME).build());
        return this;
    }

    public MicrosoftListBuilder addColumns(Column... columns) {
        List<Column> newColumns = new ArrayList<>(list.getColumns());
        newColumns.addAll(Arrays.asList(columns));
        list.setColumns(newColumns);
        return this;
    }
}
