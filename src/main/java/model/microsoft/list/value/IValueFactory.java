package model.microsoft.list.value;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class IValueFactory {
    private IValueFactory() {
    }
    private static final Map<Boolean, Supplier<IValue>> typeValue = new HashMap<>();
    static {
        typeValue.put(true, SingleObject::new);
        typeValue.put(false, ListObject::new);
    }

    public static IValue create(Boolean isSingle) {
        return typeValue.get(isSingle).get();
    }
}
