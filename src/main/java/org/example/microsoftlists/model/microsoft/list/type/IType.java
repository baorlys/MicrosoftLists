package org.example.microsoftlists.model.microsoft.list.type;

import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.microsoft.list.Parameter;
import org.example.microsoftlists.model.microsoft.list.value.IValue;

import java.util.List;

public interface IType {
    ColumnType getType();

    List<Parameter> handleConfig(List<Parameter> config);
    boolean isValueValid(List<Parameter> config, IValue value);
    int compare(Object o1, Object o2);
}
