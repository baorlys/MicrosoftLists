package org.example.microsoftlists.model.type;

import org.example.microsoftlists.model.Config;
import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.model.constants.ConfigParameter;
import org.example.microsoftlists.model.constants.ColumnType;
import org.example.microsoftlists.model.constants.DateDefault;
import org.example.microsoftlists.model.value.IValue;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Logger;

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

    @Override
    public List<Config> handleConfig(List<Config> configs) {
        return configs;
    }


    @Override
    public IValue handleDefault(List<Config> configs) {
        IValue defaultVal = configs.stream()
                .filter(para -> para.getName().equals(ConfigParameter.DEFAULT_VALUE))
                .map(Config::getValue)
                .findFirst().orElse(null);

        Optional.ofNullable(defaultVal)
                .filter(val -> val.get() .equals(DateDefault.CURRENT_DATE.name()))
                .ifPresent(val -> defaultVal.set(getCurrentDate()));

        return defaultVal;
    }

    private String getCurrentDate() {
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Configuration.DATETIME_FORMAT);

        return currentDate.format(formatter);
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
    public boolean isValueValid(List<Config> config, IValue value) {
        Predicate<Object> isDate = obj -> obj != null && isDateValid((String) obj);
        return isDate.test(value.get());
    }

    @Override
    public int compare(IValue o1, IValue o2) {
        try {
            return Optional.ofNullable(o1)
                    .map(IValue::get)
                    .filter(dateStr -> dateStr.length() > 1)
                    .map(dateStr1 -> {
                        try {
                            Date date1 = dateFormat.parse(dateStr1);
                            Date date2 = dateFormat.parse(o2.get());
                            return date1.compareTo(date2);
                        } catch (Exception e) {
                            Logger.getAnonymousLogger().warning("Failed to parse date: " + dateStr1);
                            return 0;
                        }
                    })
                    .orElseGet(() -> {
                        try {
                            Date date1 = formatDate.parse(Objects.requireNonNull(o1).get());
                            Date date2 = formatDate.parse(o2.get());
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
