package model.microsoft.list.type;

import model.constants.ColumnType;
import model.microsoft.list.Parameter;

import java.util.List;

public class CheckBoxType extends AbstractType {
    public CheckBoxType() {
        super(ColumnType.CHECKBOX);
    }

    @Override
    protected int handleCompare(Object o1, Object o2) {
        return 0;
    }

    @Override
    protected List<Parameter> handleConfig(List<Parameter> config) {
        return config;
    }

    @Override
    protected boolean handleIsValueValid(List<Parameter> config, Object value) {
        return false;
    }
}