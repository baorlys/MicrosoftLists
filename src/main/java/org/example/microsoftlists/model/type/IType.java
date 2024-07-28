package org.example.microsoftlists.model.type;

import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.Parameter;
import org.example.microsoftlists.model.value.IValue;

import java.util.List;

public interface IType {
    ColumnType getColumnType();

    List<Parameter> handleConfig(List<Parameter> config);
    boolean isValueValid(List<Parameter> config, IValue value);
    int compare(Object o1, Object o2);
}
