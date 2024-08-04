package org.example.microsoftlists.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.constants.IdentifyModel;
import org.example.microsoftlists.model.deserializer.TemplateDeserializer;
import org.example.microsoftlists.model.serializer.TemplateSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@JsonSerialize(using = TemplateSerializer.class)
@JsonDeserialize(using = TemplateDeserializer.class)
public class Template implements Identifiable {
    private final String typeIdentify = IdentifyModel.TEMPLATE.name();

    private UUID id;
    private String displayName;

    private List<Column> columns;

    public Template() {
        this.id = UUID.randomUUID();
        this.columns = new ArrayList<>();
    }



}
