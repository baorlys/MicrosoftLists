package org.example.microsoftlists.model.type;

import org.example.microsoftlists.model.constants.ColumnType;

public class ImageType implements IType {

    @Override
    public ColumnType getColumnType() {
        return ColumnType.IMAGE;
    }


}
