package org.example.microsoftlists.model.microsoft.list.type;

import org.example.microsoftlists.model.microsoft.list.Parameter;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.microsoft.list.value.IValue;

import java.util.List;

public class ImageType implements IType {

    @Override
    public ColumnType getColumnType() {
        return ColumnType.IMAGE;
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
        return 0;
    }
}
