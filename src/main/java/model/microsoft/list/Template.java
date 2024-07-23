package model.microsoft.list;

import lombok.Getter;
import lombok.Setter;

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
}
