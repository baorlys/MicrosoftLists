package org.example.microsoftlists.model.microsoft.list.type;

import org.example.microsoftlists.model.constants.DateTime;
import org.example.microsoftlists.config.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class ValueTypeFactory {
    private ValueTypeFactory() {
        throw new IllegalStateException("Utility class");
    }

    private static final Map<Object, Supplier<Object>> valueMap = new HashMap<>();

    static {

        valueMap.put(DateTime.CURRENT_DATE, () -> {
            LocalDateTime currentDate = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Configuration.DATETIME_FORMAT);

            return currentDate.format(formatter);
        });


    }

    public static Optional<Object> getValue(Object type) {
        return Optional.ofNullable(valueMap.get(type)).map(Supplier::get);
    }
}
