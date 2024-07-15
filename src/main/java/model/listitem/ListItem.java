package model.listitem;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.List;

@Getter
@Setter
public class ListItem {
    private String name;
    private String description;
    private Color color;
    private String iconId;
    private List<Column> columns;
}
