package org.example.microsoftlists.model.type;

import org.example.microsoftlists.model.Parameter;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.value.IValue;

import java.util.List;

public class AverageRatingType implements IType {



    @Override
    public ColumnType getColumnType() {
        return ColumnType.AVERAGE_RATING;
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
