package org.example.microsoftlists.model.type;

import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.value.IValue;

public class TextType implements IType {

    @Override
    public ColumnType getColumnType() {
        return ColumnType.TEXT;
    }


    @Override
    public int compare(IValue o1, IValue o2) {
        String strO1 = o1.get();
        String strO2 = o2.get();
        return strO1.compareTo(strO2);
    }


}
