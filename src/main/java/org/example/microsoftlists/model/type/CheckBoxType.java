package org.example.microsoftlists.model.type;

import org.example.microsoftlists.model.constants.ColumnType;

public class CheckBoxType implements IType {


    @Override
    public ColumnType getColumnType() {
        return ColumnType.CHECKBOX;
    }


}
