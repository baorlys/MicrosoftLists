package org.example.microsoftlists.model;

import org.example.microsoftlists.model.constants.IdentifyModel;
import org.example.microsoftlists.model.serializer.MicrosoftListSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.deserializer.TypeDeserializer;
import org.example.microsoftlists.model.serializer.TypeSerializer;
import org.example.microsoftlists.model.type.IType;
import org.example.microsoftlists.model.value.IValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class Column implements Identifiable {
    private final String typeIdentify = IdentifyModel.COLUMN.name();
    private UUID id;

    @JsonSerialize(using = MicrosoftListSerializer.class)
    private MicrosoftList list;
    private String name;
    @JsonDeserialize(using = TypeDeserializer.class)
    @JsonSerialize(using = TypeSerializer.class)
    private IType type;

    @JsonIgnore
    private List<Parameter> configs;

    private boolean isHidden;


    public Column() {
        this.id = UUID.randomUUID();
        this.configs = new ArrayList<>();
        this.isHidden = false;
    }

    public void setConfigs(Parameter... configs) {
        this.configs = List.of(configs);
    }



    public void setConfigs(List<Parameter> configs) {
        this.configs = configs;
    }

    public boolean isValidValue(IValue value) {
        return this.type.isValueValid(configs, value);
    }

    public int compare(String o1, String o2) {
        return this.type.compare(o1, o2);
    }

    @JsonIgnore
    public String getDefaultValue() {
        return Optional.ofNullable(this.type.handleDefault(configs))
                .map(IValue::get)
                .orElse("");
    }

    public Column copy()  {
        Column cloned = new Column();

        cloned.name = this.name;
        cloned.type = this.type;
        cloned.configs = new ArrayList<>(this.configs);


        return cloned;
    }
}
