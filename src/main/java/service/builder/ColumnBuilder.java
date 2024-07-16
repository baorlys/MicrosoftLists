package service.builder;

import model.constants.TypeColumn;
import model.microsoftlist.Column;
import model.microsoftlist.Parameter;
import model.microsoftlist.Type;
import java.util.List;

public class ColumnBuilder {
    Column column;
    public ColumnBuilder() {
        column = new Column();
    }

    public Column build() {
        return column;
    }

    public ColumnBuilder name(String name) {
        column.setName(name);
        return this;
    }

    public ColumnBuilder type(TypeColumn type) {
        column.setType(new Type(type));
        return this;
    }

    public ColumnBuilder configure(List<Parameter> config) {
        column.getType().setConfig(config);
        return this;
    }
}
