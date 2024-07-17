package model.microsoftlist;

import model.constants.DateTime;
import model.constants.TypeColumn;
import util.Pair;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class ConfigColumnFactory {
    private ConfigColumnFactory() {
        throw new IllegalStateException("Utility class");
    }

    private static final Map<Pair<TypeColumn,Optional<Object>>, Supplier<Optional<Object>>> configMap = new HashMap<>();

    static {
        configMap.put(Pair.of(TypeColumn.TEXT, Optional.empty()), Optional::empty);

        configMap.put(Pair.of(TypeColumn.NUMBER, Optional.empty()), Optional::empty);

        configMap.put(Pair.of(TypeColumn.DATE, Optional.of(DateTime.CURRENT_DATE)), () -> {

            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            return Optional.of(currentDate.format(formatter));
        });
    }

    public static Optional<Object> configureColumn(Pair<TypeColumn,Optional<Object>> type) {
        Supplier<Optional<Object>> supplier = configMap.get(type);
        return supplier.get();
    }
}
