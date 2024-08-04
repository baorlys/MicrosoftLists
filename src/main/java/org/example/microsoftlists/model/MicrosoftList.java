package org.example.microsoftlists.model;

import org.example.microsoftlists.model.constants.IdentifyModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.view.AbstractView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
public class MicrosoftList implements Identifiable {
    private final String typeIdentify = IdentifyModel.LIST.name();
    private UUID id;
    private String name;
    private String description;

    @JsonIgnore
    private List<Column> columns;
    @JsonIgnore
    private List<Row> rows;
    @JsonIgnore
    private List<AbstractView> views;
    public MicrosoftList() {
        this.id = UUID.randomUUID();
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.views = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }


    public void setColumns(List<Column> columns) {
        for (Column column : columns) {
            column.setList(this);
        }
    }
}
