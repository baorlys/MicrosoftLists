package org.example.microsoftlists.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.constants.IdentifyModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
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
