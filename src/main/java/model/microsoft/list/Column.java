package model.microsoft.list;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import model.microsoft.list.deserializer.TypeDeserializer;
import model.microsoft.list.serializer.TypeSerializer;
import model.microsoft.list.type.IType;
import model.microsoft.list.type.TypeFactory;
import model.microsoft.list.value.IValue;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Column {

    @JsonIgnore
    private MicrosoftList list;
    private String name;
    @JsonDeserialize(using = TypeDeserializer.class)
    @JsonSerialize(using = TypeSerializer.class)
    private IType type;

    private List<Parameter> config;

    private boolean isHidden;

    private Object defaultValue;

    public Column() {
        this.config = new ArrayList<>();
        this.isHidden = false;
    }


    public boolean isValidValue(IValue value) {
        return this.type.isValueValid(config, value);
    }

    public int compare(Object o1, Object o2) {
        return this.type.compare(o1, o2);
    }

}
