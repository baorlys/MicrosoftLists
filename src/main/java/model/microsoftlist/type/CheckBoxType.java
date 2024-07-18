package model.microsoftlist.type;

import model.constants.TypeColumn;
import model.microsoftlist.Parameter;

import java.util.List;

public class CheckBoxType extends AbstractType {
    public CheckBoxType() {
        super(TypeColumn.CHECKBOX);
    }
    @Override
    protected List<Parameter> handleConfig(List<Parameter> config) {
        return config;
    }
}
