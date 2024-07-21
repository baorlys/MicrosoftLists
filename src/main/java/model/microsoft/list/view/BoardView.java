package model.microsoft.list.view;

import lombok.Getter;
import lombok.Setter;
import model.constants.ViewType;
import model.microsoft.list.Column;

@Setter
@Getter
public class BoardView extends AbstractView {
    private Column organize;
    public BoardView(String name, Column organize) {
        super(name,ViewType.BOARD);
        this.setName(name);
        this.organize = organize;
    }

}
