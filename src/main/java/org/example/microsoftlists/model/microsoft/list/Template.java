package org.example.microsoftlists.model.microsoft.list;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Template {
    private List<Column> columns;
    private String displayName;

    public Template(List<Column> columns, String displayName) {
        this.columns = columns;
        this.displayName = displayName;
    }


    public Template(String displayName) {
        this.displayName = displayName;
        this.columns = new ArrayList<>();
    }
}
