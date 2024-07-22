package model.microsoft.list.type;

import model.constants.ColumnType;
import model.microsoft.list.Parameter;
import model.microsoft.list.value.IValue;

import java.math.BigDecimal;
import java.util.List;

public class NumberType extends AbstractType {
    public NumberType() {
        super(ColumnType.NUMBER);
    }

    @Override
    protected int handleCompare(Object o1, Object o2) {
        BigDecimal numberO1 = new BigDecimal((Integer) o1);
        BigDecimal numberO2 = new BigDecimal((Integer) o2);
        return numberO1.compareTo(numberO2);
    }

    @Override
    protected List<Parameter> handleConfig(List<Parameter> config) {
        return config;
    }

    @Override
    protected boolean handleIsValueValid(List<Parameter> config, IValue value) {
        return false;
    }


}
