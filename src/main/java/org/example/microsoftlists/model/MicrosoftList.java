package org.example.microsoftlists.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class MicrosoftList {
    @Id
    private String id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "list", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Column> columns;
    @OneToMany(mappedBy = "list",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Row> rows;
    public MicrosoftList() {
        this.id = UUID.randomUUID().toString();
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
    }



    public void setColumns(List<Column> columns) {
        List<Column> cols = new ArrayList<>();
        for (Column column : columns) {
            Column col = column.copy();
            col.setList(this);
            cols.add(col);
        }
        this.columns = cols;
    }
}
