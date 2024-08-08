package org.example.microsoftlists.service.builder;

import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.Column;
import org.example.microsoftlists.model.Config;
import org.example.microsoftlists.model.type.TypeFactory;

import java.util.List;

public class ColumnBuilder {
    Column column;

    public ColumnBuilder(ColumnType type, String colName) {
        column = new Column();
        column.setName(colName);
        column.setType(TypeFactory.getType(type));
    }

    public Column build() {
        return column;
    }


    public ColumnBuilder configure(List<Config> configs) {
        column.setConfigs(configs);
        return this;
    }

}
