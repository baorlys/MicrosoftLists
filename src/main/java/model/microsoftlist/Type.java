package model.microsoftlist;


import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import model.constants.DateTime;
import model.constants.TypeColumn;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class Type {
    private TypeColumn typeColumn;
    @Expose

    private List<Parameter> config;
    public Type(TypeColumn type) {
        this.typeColumn = type;

    }

    public void setConfig(List<Parameter> config) {
        this.config = Optional.ofNullable(config)
                .orElse(List.of());
        String defaultVal = String.valueOf(ConfigColumnFactory.configureColumn(typeColumn));
        Optional<Parameter> optionalParameter = this.config.stream()
                .filter(parameter -> parameter.getName().equals("defaultVal")
                        && parameter.getValue().equals(DateTime.CURRENT_DATE))
                .findFirst();
        optionalParameter.ifPresent(parameter -> parameter.setValue(defaultVal));
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
