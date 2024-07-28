package org.example.microsoftlists.model.type;

import org.example.microsoftlists.model.Parameter;
import lombok.Getter;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.constants.ConfigParameter;
import org.example.microsoftlists.model.value.IValue;

import java.util.List;
import java.util.function.Predicate;

@Getter
public class ChoiceType implements IType {



    @Override
    public ColumnType getColumnType() {
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
