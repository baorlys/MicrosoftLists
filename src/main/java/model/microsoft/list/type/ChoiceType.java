package model.microsoft.list.type;

import lombok.Getter;
import model.constants.ColumnType;
import model.microsoft.list.Parameter;
import model.microsoft.list.value.IValue;

import java.util.List;

@Getter
public class ChoiceType extends AbstractType {
    private IValue values;
    public ChoiceType() {
        super(ColumnType.CHOICE);
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
