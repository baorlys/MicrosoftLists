package model.microsoftlist.type;

import model.constants.TypeColumn;
import model.microsoftlist.Parameter;

import java.util.List;

public class LookupType extends AbstractType {
    public LookupType() {
        super(TypeColumn.LOOKUP);
    }
    @Override
    protected List<Parameter> handleConfig(List<Parameter> config) {
        return config;
    }
}
