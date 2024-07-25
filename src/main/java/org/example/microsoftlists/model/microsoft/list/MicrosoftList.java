package org.example.microsoftlists.model.microsoft.list;

import org.example.microsoftlists.model.constants.IdentifyModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.microsoft.list.view.AbstractView;
import org.example.microsoftlists.model.microsoft.list.value.SingleObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public MicrosoftList(String name) {
        this();
        this.name = name;
    }


    public void addColumn(Column column) {
        this.columns.add(column);

    }

    public void addRow() {
        Row row = new Row(this);

        for (Column column : this.columns) {
            Optional<Object> defaultVal = Optional.ofNullable(column.getDefaultValue());
            row.addCell(Cell.of(row, column, new SingleObject(defaultVal.orElse(""))));
        }

        this.rows.add(row);

    }

    public void addRow(Row row) {
        this.rows.add(row);

    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;


    }

    public void setRows(List<Row> rows) {
        this.rows = rows;

    }




    public void createView(AbstractView view) {
        this.views.add(view);

    }


}
