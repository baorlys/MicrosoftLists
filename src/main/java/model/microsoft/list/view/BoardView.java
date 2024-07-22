package model.microsoft.list.view;

import lombok.Getter;
import lombok.Setter;
import model.constants.ConfigParameter;
import model.constants.ViewType;
import model.microsoft.list.Column;

import java.util.Optional;

@Setter
@Getter
public class BoardView extends AbstractView {
    private Column organize;
    public BoardView(String name, Column organize) {
        super(name,ViewType.BOARD);
        this.setName(name);
        this.organize = Optional.ofNullable(organize)
                .filter(this::isOrganizeValid)
                .orElseThrow(
                        () -> new IllegalArgumentException("Organize column must be single selection column.")
                );
    }

    private boolean isOrganizeValid(Column column) {
        return column.getConfig().stream()
                .filter(parameter -> parameter.getName().equals(ConfigParameter.MULTIPLE_SELECTION))
                .anyMatch(parameter -> parameter.getValue().equals(false));
    }

}
