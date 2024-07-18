package model.microsoftlist.type;

import model.constants.TypeColumn;
import model.microsoftlist.Parameter;

import java.util.List;

public class AverageRatingType extends AbstractType {
    public AverageRatingType() {
        super(TypeColumn.AVERAGE_RATING);
    }
    @Override
    protected List<Parameter> handleConfig(List<Parameter> config) {
        return config;
    }
}
