package model.microsoft.list.type;

import lombok.Getter;
import model.constants.ColumnType;
import model.constants.ConfigParameter;
import model.microsoft.list.Parameter;
import model.microsoft.list.value.IValue;

import java.util.List;
import java.util.function.Predicate;

@Getter
public class ChoiceType extends AbstractType {
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
    protected boolean handleIsValueValid(List<Parameter> config, IValue value) {
        Predicate<Parameter> isMultiSelect = para -> para.getName().equals(ConfigParameter.MULTIPLE_SELECTION)
                && para.getValue().equals(true);
        return !(config.stream().noneMatch(isMultiSelect) && value.isMultiple());

    }


}
