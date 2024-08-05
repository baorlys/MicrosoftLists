package org.example.microsoftlists.model.view;

import org.example.microsoftlists.model.Column;
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
        return column.getConfigs().stream()
                .filter(parameter -> parameter.getName().equals(ConfigParameter.MULTIPLE_SELECTION))
                .anyMatch(parameter -> parameter.getValue().get().toLowerCase().equals(Boolean.FALSE.toString()));
    }

}
