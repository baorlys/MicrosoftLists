package org.example.microsoftlists.model.type;

import org.example.microsoftlists.model.Parameter;
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
    public boolean isValueValid(List<Parameter> config, IValue value) {
        try {
            new BigDecimal(value.get().toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int compare(Object o1, Object o2) {
        BigDecimal numberO1 = new BigDecimal((Integer) o1);
        BigDecimal numberO2 = new BigDecimal((Integer) o2);
        return numberO1.compareTo(numberO2);
    }



}
