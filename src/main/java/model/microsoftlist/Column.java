package model.microsoftlist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import model.microsoftlist.type.AbstractType;

import java.util.List;

@Getter
@Setter
public class Column {

    @JsonIgnore
    private MicrosoftList list;
    private String name;
    private AbstractType type;

    private List<Parameter> config;
    private boolean isHidden;


    public Column() {
        this.isHidden = false;
    }



}
