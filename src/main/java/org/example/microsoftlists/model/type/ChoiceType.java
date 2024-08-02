package org.example.microsoftlists.model.type;

import org.example.microsoftlists.model.Parameter;
import lombok.Getter;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.constants.ConfigParameter;
import org.example.microsoftlists.model.value.IValue;
import org.example.microsoftlists.model.value.ListObject;

import java.util.List;
import java.util.function.Predicate;

@Getter
public class ChoiceType implements IType {



    @Override
    public ColumnType getColumnType() {
        return ColumnType.CHOICE;
    }



    @Override
    public boolean isValueValid(List<Parameter> config, IValue value) {
        Predicate<Parameter> isMultiSelect = para -> para.getName().equals(ConfigParameter.MULTIPLE_SELECTION)
                && para.getValue().get().equalsIgnoreCase(Boolean.TRUE.toString());
        return !(config.stream().noneMatch(isMultiSelect) && value instanceof ListObject);
    }





}
