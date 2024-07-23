package model.microsoft.list.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import model.constants.ColumnType;
import model.microsoft.list.Parameter;
import model.microsoft.list.value.IValue;

import java.util.Comparator;
import java.util.List;



public abstract class AbstractType implements Comparator<Object> {
    @JsonIgnore
    private final ColumnType columnType;

    protected AbstractType(ColumnType type) {
        this.columnType = type;

    }
    @Override
    public int compare(Object o1, Object o2) {
        return this.handleCompare(o1, o2);
    }
    protected abstract int handleCompare(Object o1, Object o2);



    public List<Parameter> handle(List<Parameter> config) {
        return this.handleConfig(config);
    }
    protected abstract List<Parameter> handleConfig(List<Parameter> config);

    public ColumnType getType() {
        return columnType;
    }


    public boolean isValueValid(List<Parameter> config, IValue value) {
        return this.handleIsValueValid(config, value);
    }

    protected abstract boolean handleIsValueValid(List<Parameter> config, IValue value);
}
