package model.microsoft.list.type;

import lombok.Getter;
import model.constants.ColumnType;
import model.constants.ConfigParameter;
import model.microsoft.list.Parameter;
import model.microsoft.list.value.IValue;

import java.util.List;
import java.util.function.Predicate;

@Getter
public class ChoiceType implements IType {



    @Override
    public ColumnType getType() {
        return ColumnType.CHOICE;
    }

    @Override
    public List<Parameter> handleConfig(List<Parameter> config) {
        return config;
    }


    @Override
    public boolean isValueValid(List<Parameter> config, IValue value) {
        Predicate<Parameter> isMultiSelect = para -> para.getName().equals(ConfigParameter.MULTIPLE_SELECTION)
                && para.getValue().equals(true);
        return !(config.stream().noneMatch(isMultiSelect) && value.get() instanceof List);
    }

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }




}
