package org.example.microsoftlists.model.value;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ValueFactory {
    private ValueFactory() {
        // Empty constructor
    }
    private static final Map<Boolean, Supplier<IValue>> valueMap = new HashMap<>();
    static {
        valueMap.put(true, SingleObject::new);
        valueMap.put(false, ListObject::new);
    }

    public static IValue create(Object... objects) {
        boolean isSingle = objects.length == 1;
        IValue value = valueMap.get(isSingle).get();
        value.set(objects);
        return value;
    }


}
