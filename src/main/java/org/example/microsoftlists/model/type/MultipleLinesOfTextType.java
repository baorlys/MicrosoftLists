package org.example.microsoftlists.model.type;

import org.example.microsoftlists.model.constants.ColumnType;

public class MultipleLinesOfTextType implements IType {
    @Override
    public ColumnType getColumnType() {
        return ColumnType.MULTIPLE_LINES_OF_TEXT;
    }


}
