package model.microsoft.list.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import model.constants.ViewType;
import model.microsoft.list.MicrosoftList;

@Getter
@Setter
public class AbstractView {
    @JsonIgnore
    private MicrosoftList list;
    private String name;
    private ViewType type;

    protected AbstractView(String name, ViewType type) {
        this.name = name;
        this.type = type;
    }
}
