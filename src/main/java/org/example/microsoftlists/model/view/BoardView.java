package org.example.microsoftlists.model.view;

import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.constants.ViewConfig;
import org.example.microsoftlists.model.constants.ViewType;

import java.util.Map;

@Setter
@Getter
public class BoardView implements IView {
    private String organize;

    @Override
    public ViewType getViewType() {
        return ViewType.BOARD;
    }

    @Override
    public void handleConfig(Map<ViewConfig, String> configs) {
        this.organize = configs.get(ViewConfig.ORGANIZE_BY);
    }
}
