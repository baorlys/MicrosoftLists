package org.example.microsoftlists.service.builder;

import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.microsoft.list.Column;
import org.example.microsoftlists.model.microsoft.list.Parameter;
import org.example.microsoftlists.model.microsoft.list.type.TypeFactory;

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


    public ColumnBuilder configure(Parameter... configs) {
        column.setConfig(List.of(configs));
        return this;
    }

    public ColumnBuilder isHidden(boolean isHide) {
        column.setHidden(isHide);
        return this;
    }


}
