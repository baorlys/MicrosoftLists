package org.example.microsoftlists.service.builder;

import org.example.microsoftlists.service.ListService;
import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.Column;
import org.example.microsoftlists.model.MicrosoftList;
import org.example.microsoftlists.service.file.SaveService;

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
