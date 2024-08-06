package org.example.microsoftlists.view.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.constants.ViewConfig;
import org.example.microsoftlists.model.constants.ViewType;
import org.example.microsoftlists.model.view.IView;

import java.util.Map;

@Getter
@Setter
public class ViewResponse {
    private String id;
    private String name;
    private ViewType type;
    private Map<ViewConfig,String> configs;


    public void setType(IView type) {
        this.type = type.getViewType();
    }

    public void setType(ViewType type) {
        this.type = type;
    }

}
