package org.example.microsoftlists.model.type;

import org.example.microsoftlists.model.Config;
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
    public boolean isValueValid(List<Config> config, IValue value) {
        Predicate<Config> isMultiSelect = para -> para.getName().equals(ConfigParameter.MULTIPLE_SELECTION)
                && para.getValue().get().equalsIgnoreCase(Boolean.TRUE.toString());
        return !(config.stream().noneMatch(isMultiSelect) && value instanceof ListObject);
    }





}
