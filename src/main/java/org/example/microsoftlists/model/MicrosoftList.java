package org.example.microsoftlists.model;

import org.example.microsoftlists.view.dto.response.ListResponse;
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

    public static MicrosoftList of(ListResponse list) {
        MicrosoftList microsoftList = new MicrosoftList();
        microsoftList.setId(UUID.fromString(list.getId()));
        microsoftList.setName(list.getName());
        microsoftList.setDescription(list.getDescription());
        return microsoftList;
    }

    public UUID getId() {
        return id;
    }

    public MicrosoftList(String name) {
        this();
        this.name = name;
    }


}
