package org.example.microsoftlists.model.type;

import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.Parameter;
import org.example.microsoftlists.model.constants.ConfigParameter;
import org.example.microsoftlists.model.value.IValue;

import java.util.List;

public interface IType {
    ColumnType getColumnType();


    default List<Parameter> handleConfig(List<Parameter> configs) {
        return configs;
    }

    default IValue handleDefault(List<Parameter> configs) {
        return configs.stream()
                .filter(para -> para.getName().equals(ConfigParameter.DEFAULT_VALUE))
                .map(Parameter::getValue)
                .findFirst().orElse(null);
    }
    default boolean isValueValid(List<Parameter> config, IValue value) {
        return true;
    }
    default int compare(Object o1, Object o2) {
        return 0;
    }
}
