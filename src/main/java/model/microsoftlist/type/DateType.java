package model.microsoftlist.type;

import model.constants.ConfigParameter;
import model.constants.TypeColumn;
import model.microsoftlist.Parameter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DateType extends AbstractType {
    public DateType() {
        super(TypeColumn.DATE);
    }

    @Override
    protected List<Parameter> handleConfig(List<Parameter> config) {
        return config.stream()
                .filter(para -> para.getName().equals(ConfigParameter.DEFAULT_VALUE))
                .map(para -> {
                    Optional<Object> result = ValueTypeFactory.getValue(para.getValue().get(0));
                    para.setValue(result.map(Collections::singletonList).orElse(para.getValue()));
                    return para;
                })
                .collect(Collectors.toList());
    }
}
