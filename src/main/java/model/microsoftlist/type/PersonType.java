package model.microsoftlist.type;

import model.constants.TypeColumn;
import model.microsoftlist.Parameter;

import java.util.List;

public class PersonType extends AbstractType {
    public PersonType() {
        super(TypeColumn.PERSON);
    }
    @Override
    protected List<Parameter> handleConfig(List<Parameter> config) {
        return config;
    }
}
