package org.example.microsoftlists.model.type;

import org.example.microsoftlists.model.constants.ColumnType;

public class TextType implements IType {

    @Override
    public ColumnType getColumnType() {
        return ColumnType.TEXT;
    }


    @Override
    public int compare(Object o1, Object o2) {
        String strO1 = (String) o1;
        String strO2 = (String) o2;
        return strO1.compareTo(strO2);
    }


}
