package model.microsoft.list.type;

import model.constants.ColumnType;
import model.microsoft.list.Parameter;
import model.microsoft.list.value.IValue;

import java.util.List;

public class MultipleLinesOfTextType implements IType {
    @Override
    public ColumnType getType() {
        return ColumnType.MULTIPLE_LINES_OF_TEXT;
    }

    @Override
    public List<Parameter> handleConfig(List<Parameter> config) {
        return config;
    }

    @Override
    public boolean isValueValid(List<Parameter> config, IValue value) {
        return true;
    }

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }
}
