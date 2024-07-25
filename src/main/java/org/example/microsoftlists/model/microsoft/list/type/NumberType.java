package org.example.microsoftlists.model.microsoft.list.type;

import org.example.microsoftlists.model.microsoft.list.Parameter;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.microsoft.list.value.IValue;

import java.math.BigDecimal;
import java.util.List;

public class NumberType implements IType {

    @Override
    public ColumnType getType() {
        return ColumnType.NUMBER;
    }

    @Override
    public List<Parameter> handleConfig(List<Parameter> config) {
        return config;
    }


    @Override
    public boolean isValueValid(List<Parameter> config, IValue value) {
        return value.get() instanceof Integer || value.get() instanceof BigDecimal;

    }

    @Override
    public int compare(Object o1, Object o2) {
        BigDecimal numberO1 = new BigDecimal((Integer) o1);
        BigDecimal numberO2 = new BigDecimal((Integer) o2);
        return numberO1.compareTo(numberO2);
    }



}
