package org.example.microsoftlists.model.microsoft.list.view;

import org.example.microsoftlists.model.microsoft.list.Column;
import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.constants.ConfigParameter;
import org.example.microsoftlists.model.constants.ViewType;

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
