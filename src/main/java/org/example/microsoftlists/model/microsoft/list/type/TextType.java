package org.example.microsoftlists.model.microsoft.list.type;

import org.example.microsoftlists.model.microsoft.list.Parameter;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.microsoft.list.value.IValue;

import java.util.List;

public class TextType implements IType {

    @Override
    public ColumnType getColumnType() {
        return ColumnType.TEXT;
    }

    @Override
    public List<Parameter> handleConfig(List<Parameter> config) {
        return config;
    }

    @Override
    public boolean isValueValid(List<Parameter> config, IValue value) {
        return true;
    }

    @Override
    public int compare(Object o1, Object o2) {
        String strO1 = (String) o1;
        String strO2 = (String) o2;
        return strO1.compareTo(strO2);
    }


}
