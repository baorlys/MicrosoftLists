package model.microsoftlist;

import model.constants.TypeColumn;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class ConfigColumnFactory {
    private ConfigColumnFactory() {
        throw new IllegalStateException("Utility class");
    }

    private static final Map<TypeColumn, Supplier<Optional<String>>> configMap = new EnumMap<>(TypeColumn.class);

    static {
        configMap.put(TypeColumn.TEXT, Optional::empty);
        configMap.put(TypeColumn.NUMBER, Optional::empty);
        configMap.put(TypeColumn.DATE, () -> {
            LocalDate currentDate = LocalDate.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            return Optional.of(currentDate.format(formatter));
        });
    }

    public static Optional<String> configureColumn(TypeColumn type) {
        Supplier<Optional<String>> supplier = configMap.get(type);
        return supplier.get();
    }
}
