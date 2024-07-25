package model.microsoft.list.type;

import model.constants.ColumnType;
import model.microsoft.list.Parameter;
import model.microsoft.list.value.IValue;

import java.util.List;

public class TextType implements IType {

    @Override
    public ColumnType getType() {
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
