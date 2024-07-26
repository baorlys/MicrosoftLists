package org.example.microsoftlists.model.microsoft.list.type;

import org.example.microsoftlists.model.microsoft.list.Parameter;
import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.model.constants.ConfigParameter;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.microsoft.list.value.IValue;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DateType implements IType {
    private final SimpleDateFormat dateFormat;
    private final SimpleDateFormat formatDate;
    private final SimpleDateFormat formatTime;

    public DateType() {
        String[] formatParts = Configuration.DATETIME_FORMAT.split(" ");

        String datePattern = formatParts[0];
        this.formatDate = new SimpleDateFormat(datePattern);

        String timePattern = formatParts[1] + " " + formatParts[2];
        this.formatTime = new SimpleDateFormat(timePattern);
        this.dateFormat = new SimpleDateFormat(Configuration.DATETIME_FORMAT);
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

    private boolean isDateValid(String dateStr) {
        dateStr = dateStr.trim();
        String[] dateParts = dateStr.split(" ");

        try {
            formatDate.parse(dateParts[0]);

            return Optional.of(dateParts)
                    .filter(parts -> parts.length > 1)
                    .map(parts -> parts[1] + " " + parts[2])
                    .flatMap(timeStr -> {
                        try {
                            formatTime.parse(timeStr);
                            return Optional.of(true);
                        } catch (ParseException e) {
                            return Optional.of(false);
                        }
                    })
                    .orElse(true);
        } catch (ParseException e) {
            return false;
        }
    }


    @Override
    public ColumnType getColumnType() {
        return ColumnType.DATE;
    }

    @Override
    public List<Parameter> handleConfig(List<Parameter> config) {
        List<Parameter> configList = config.stream()
                .filter(para -> !para.getName().equals(ConfigParameter.DEFAULT_VALUE))
                .collect(Collectors.toList());
        Parameter defaultValue = handleDefault(config);
        Optional.ofNullable(defaultValue)
                .ifPresent(configList::add);
        return configList;
    }

    @Override
    public boolean isValueValid(List<Parameter> config, IValue value) {
        Predicate<Object> isDate = obj -> obj instanceof String && isDateValid((String) obj);
        return isDate.test(value.get());
    }

    @Override
    public int compare(Object o1, Object o2) {
        try {
            return Optional.ofNullable(o1)
                    .map(Object::toString)
                    .filter(dateStr -> dateStr.length() > 1)
                    .map(dateStr1 -> {
                        try {
                            Date date1 = dateFormat.parse(dateStr1);
                            Date date2 = dateFormat.parse((String) o2);
                            return date1.compareTo(date2);
                        } catch (Exception e) {
                            Logger.getAnonymousLogger().warning("Failed to parse date: " + dateStr1);
                            return 0;
                        }
                    })
                    .orElseGet(() -> {
                        try {
                            Date date1 = formatDate.parse((String) o1);
                            Date date2 = formatDate.parse((String) o2);
                            return date1.compareTo(date2);
                        } catch (Exception e) {
                            Logger.getAnonymousLogger().warning("Failed to parse date: " + o1);
                            return 0;
                        }
                    });
        } catch (Exception e) {
            Logger.getAnonymousLogger().warning("Unexpected error: " + e.getMessage());
        }
        return 0;
    }
}
