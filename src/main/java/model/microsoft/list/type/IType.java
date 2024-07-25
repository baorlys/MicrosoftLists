package model.microsoft.list.type;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import model.constants.ColumnType;
import model.microsoft.list.Parameter;
import model.microsoft.list.value.IValue;

import java.util.List;

public interface IType {
    ColumnType getType();

    List<Parameter> handleConfig(List<Parameter> config);
    boolean isValueValid(List<Parameter> config, IValue value);
    int compare(Object o1, Object o2);
}
