package org.example.microsoftlists.model.type;

import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.Config;
import org.example.microsoftlists.model.constants.ConfigParameter;
import org.example.microsoftlists.model.value.IValue;

import java.util.List;

public interface IType {
    ColumnType getColumnType();


    default List<Config> handleConfig(List<Config> configs) {
        return configs;
    }

    default IValue handleDefault(List<Config> configs) {
        return configs.stream()
                .filter(para -> para.getName().equals(ConfigParameter.DEFAULT_VALUE))
                .map(Config::getValue)
                .findFirst().orElse(null);
    }
    default boolean isValueValid(List<Config> config, IValue value) {
        return true;
    }
    default int compare(IValue o1, IValue o2) {
        return 0;
    }
}
