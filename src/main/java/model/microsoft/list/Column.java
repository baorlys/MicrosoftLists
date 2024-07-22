package model.microsoft.list;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import model.constants.ConfigParameter;
import model.microsoft.list.type.AbstractType;
import model.microsoft.list.value.IValue;

import java.util.ArrayList;
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
        this.config = new ArrayList<>();
        this.isHidden = false;
    }


    public boolean isValueValid(IValue value) {
        return this.type.isValueValid(config, value);
    }

    public Object getDefaultValue() {
        return this.config.stream()
                .filter(para -> para.getName().equals(ConfigParameter.DEFAULT_VALUE))
                .findFirst().map(Parameter::getValue).orElse(null);
    }

    public int compare(Object o1, Object o2) {
        return this.type.compare(o1, o2);
    }

}
