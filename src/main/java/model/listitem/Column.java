package model.listitem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Column {
    private ListItem listItem;
    private String name;
    private Type type;
    private boolean isHidden = false;




    public Column(ListItem listItem, String name, Type type, boolean isHidden) {
        this.listItem = listItem;
        this.name = name;
        this.type = type;
        this.isHidden = isHidden;
    }



}
