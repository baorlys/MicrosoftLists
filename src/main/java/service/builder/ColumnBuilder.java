package service.builder;

import model.constants.ColumnType;
import model.microsoft.list.Column;
import model.microsoft.list.Parameter;
import model.microsoft.list.type.TypeFactory;

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
