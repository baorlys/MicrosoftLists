package model.microsoftlist.type;

import model.constants.TypeColumn;
import model.microsoftlist.Parameter;

import java.util.List;

public class NumberType extends AbstractType {
    public NumberType() {
        super(TypeColumn.NUMBER);
    }

    @Override
    protected List<Parameter> handleConfig(List<Parameter> config) {
        return config;
    }
}
