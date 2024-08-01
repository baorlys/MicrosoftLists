package org.example.microsoftlists.model.type;

import org.example.microsoftlists.model.constants.ColumnType;


public class AverageRatingType implements IType {



    @Override
    public ColumnType getColumnType() {
        return ColumnType.AVERAGE_RATING;
    }






}
