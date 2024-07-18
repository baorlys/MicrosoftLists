package service.builder;

import model.constants.TypeColumn;
import model.microsoftlist.Column;
import model.microsoftlist.Parameter;
import model.microsoftlist.type.TypeFactory;

import java.util.List;

public class ColumnBuilder {
    Column column;

    public ColumnBuilder(TypeColumn type, String colName) {
        column = new Column();
        column.setName(colName);
        column.setType(TypeFactory.getType(type));
    }

    public Column build() {
        return column;
    }


    public ColumnBuilder configure(Parameter... configList) {
        column.setConfig(List.of(configList));
        return this;
    }
}
