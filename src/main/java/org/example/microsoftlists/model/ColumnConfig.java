package org.example.microsoftlists.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.microsoftlists.model.constants.IdentifyModel;
import org.example.microsoftlists.model.deserializer.ParameterDeserializer;
import org.example.microsoftlists.model.serializer.ColumnSerializer;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ColumnConfig implements Identifiable {
    private final String typeIdentify = IdentifyModel.COLUMN_CONFIG.name();
    private UUID id;
    @JsonSerialize(using = ColumnSerializer.class)

    private Column column;

    @JsonDeserialize(using = ParameterDeserializer.class)
    private Parameter parameter;


    public ColumnConfig(Column column, Parameter parameter) {
        this.id = UUID.randomUUID();
        this.column = column;
        this.parameter = parameter;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
