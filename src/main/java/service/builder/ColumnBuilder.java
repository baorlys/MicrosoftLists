package service.builder;

import model.listitem.Column;
import model.listitem.Parameter;
import model.listitem.Type;
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

    public ColumnBuilder type(Type type) {
        column.setType(type);
        return this;
    }

    public ColumnBuilder configure(List<Parameter> config) {
        column.getType().setConfig(config);
        return this;
    }
}
