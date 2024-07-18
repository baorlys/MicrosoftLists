package model.microsoftlist.type;

import model.constants.TypeColumn;
import model.microsoftlist.Parameter;

import java.util.List;

public class HyperlinkType extends AbstractType {
    public HyperlinkType() {
        super(TypeColumn.HYPERLINK);
    }
    @Override
    protected List<Parameter> handleConfig(List<Parameter> config) {
        return config;
    }
}
