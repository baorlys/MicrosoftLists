package model.microsoft.list.type;

import model.constants.ColumnType;
import model.microsoft.list.Parameter;
import model.microsoft.list.value.IValue;

import java.util.List;

public class TextType extends AbstractType {
    public TextType() {
        super(ColumnType.TEXT);
    }


    @Override
    protected List<Parameter> handleConfig(List<Parameter> config) {
        return config;
    }

    @Override
    protected boolean handleIsValueValid(List<Parameter> config, IValue value) {
        return true;
    }

    @Override
    public int handleCompare(Object o1, Object o2) {
        String strO1 = (String) o1;
        String strO2 = (String) o2;
        return strO1.compareTo(strO2);
    }
}
