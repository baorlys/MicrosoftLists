package model.microsoftlist.type;

import model.constants.TypeColumn;
import model.microsoftlist.Parameter;

import java.util.List;

public class MultipleLinesOfTextType extends AbstractType {
    public MultipleLinesOfTextType() {
        super(TypeColumn.MULTIPLE_LINES_OF_TEXT);
    }
    @Override
    protected List<Parameter> handleConfig(List<Parameter> config) {
        return config;
    }
}
