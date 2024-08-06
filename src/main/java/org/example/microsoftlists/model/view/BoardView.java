package org.example.microsoftlists.model.view;

import org.example.microsoftlists.model.Column;
import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.constants.ConfigParameter;

import java.util.Map;

@Setter
@Getter
public class BoardView extends View {
    private Column organize;


    public BoardView(Map<String,String> data) {
        this.setData(data);
    }

    private boolean isOrganizeValid(Column column) {
        return column.getConfigs().stream()
                .filter(parameter -> parameter.getName().equals(ConfigParameter.MULTIPLE_SELECTION))
                .anyMatch(parameter -> parameter.getValue().get().toLowerCase().equals(Boolean.FALSE.toString()));
    }

}
