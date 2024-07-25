package model.microsoft.list.value;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SingleObject.class, name = "SingleObject"),
        @JsonSubTypes.Type(value = ListObject.class, name = "ListObject")
})
public interface IValue {
    Object get();

    void set(Object... object);

}
