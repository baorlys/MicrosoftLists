package model.microsoft.list.type;

import config.Configuration;
import model.constants.ConfigParameter;
import model.constants.ColumnType;
import model.microsoft.list.Parameter;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DateType extends AbstractType {
    private final SimpleDateFormat format;
    public DateType() {
        super(ColumnType.DATE);
        this.format = new SimpleDateFormat(Configuration.DATETIME_FORMAT);
    }

    @Override
    protected int handleCompare(Object o1, Object o2) {
        try {
            Date date1 = format.parse((String) o1);
            Date date2 = format.parse((String) o2);
            return date1.compareTo(date2);
        } catch (Exception e) {
            Logger.getAnonymousLogger().warning("Failed to parse date");
        }
        return 0;
    }

    @Override
    protected List<Parameter> handleConfig(List<Parameter> config) {
        List<Parameter> configList = config.stream()
                .filter(para -> !para.getName().equals(ConfigParameter.DEFAULT_VALUE))
                .collect(Collectors.toList());
        Parameter defaultValue = handleDefault(config);
        Optional.ofNullable(defaultValue)
                .ifPresent(configList::add);
        return configList;
    }

    @Override
    protected boolean handleIsValueValid(List<Parameter> config, Object value) {
        return false;
    }

    private Parameter handleDefault(List<Parameter> config) {
        Parameter defaultValue = config.stream()
                .filter(para -> para.getName().equals(ConfigParameter.DEFAULT_VALUE))
                .findFirst().orElse(null);
        Optional.ofNullable(defaultValue)
                .ifPresent(para -> {
                    Optional<Object> result = ValueTypeFactory.getValue(para.getValue());
                    para.setValue(result.orElse(para.getValue()));
                });
        return defaultValue;
    }
}
