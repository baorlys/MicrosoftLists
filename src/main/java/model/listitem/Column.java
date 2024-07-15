package model.listitem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Column {
    private String name;
    private Type type;
    private boolean isHidden;

}
