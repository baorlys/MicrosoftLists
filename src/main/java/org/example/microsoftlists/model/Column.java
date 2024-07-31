package org.example.microsoftlists.model;

import org.example.microsoftlists.view.dto.response.ColumnResponse;
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
import org.example.microsoftlists.model.type.TypeFactory;
import org.example.microsoftlists.model.value.IValue;

import java.util.ArrayList;
import java.util.List;
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
    private List<Parameter> config;

    private boolean isHidden;

    private Object defaultValue;

    public Column() {
        this.id = UUID.randomUUID();
        this.config = new ArrayList<>();
        this.isHidden = false;
    }

    public static Column of(ColumnResponse column) {
        Column col = new Column();
        col.setId(UUID.fromString(column.getId()));
        col.setName(column.getName());
        col.setType(TypeFactory.getType(column.getType()));
        col.setDefaultValue(column.getDefaultValue());
        col.setHidden(column.isHidden());
        col.setConfig(column.getConfigs());
        return col;
    }

    public void setConfig(Parameter... configs) {
        this.config = List.of(configs);
    }



    public void setConfig(List<Parameter> configs) {
        this.config = configs;
    }

    public boolean isValidValue(IValue value) {
        return this.type.isValueValid(config, value);
    }

    public int compare(Object o1, Object o2) {
        return this.type.compare(o1, o2);
    }

}
