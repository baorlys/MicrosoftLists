package model.microsoftlist.type;

import model.constants.TypeColumn;
import model.microsoftlist.Parameter;

import java.util.List;

public class TextType extends AbstractType {
    public TextType() {
        super(TypeColumn.TEXT);
    }


    @Override
    protected List<Parameter> handleConfig(List<Parameter> config) {
        return config;
    }
}
