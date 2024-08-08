package org.example.microsoftlists.model.type;

import org.example.microsoftlists.model.Config;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.value.IValue;

import java.math.BigDecimal;
import java.util.List;

public class NumberType implements IType {

    @Override
    public ColumnType getColumnType() {
        return ColumnType.NUMBER;
    }


    @Override
    public boolean isValueValid(List<Config> config, IValue value) {
        try {
            new BigDecimal(value.get());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int compare(IValue o1, IValue o2) {
        try {
            new BigDecimal(o1.get());
        } catch (Exception e) {
            return 1;
        }
        try {
            new BigDecimal(o2.get());
        } catch (Exception e) {
            return -1;
        }
        return new BigDecimal(o1.get()).compareTo(new BigDecimal(o2.get()));
    }



}
