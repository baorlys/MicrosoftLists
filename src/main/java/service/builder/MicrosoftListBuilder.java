package service.builder;

import config.Configuration;
import model.constants.ColumnType;
import model.microsoft.list.Column;
import model.microsoft.list.MicrosoftList;
import service.ListService;
import service.file.SaveService;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
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
        list.addColumn(new ColumnBuilder(ColumnType.TEXT, Configuration.COLUMN_NAME)
                .build());
        return this;
    }

    public MicrosoftListBuilder addColumns(Column... columns) {
        ListService.addColumns(list, Arrays.asList(columns));
        return this;
    }
}
