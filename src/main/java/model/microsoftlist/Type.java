package model.microsoftlist;


import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import model.constants.DateTime;
import model.constants.TypeColumn;
import util.Pair;

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


        this.config.stream()
                .filter(para -> para.getName().equals("defaultVal"))
                .findFirst()
                .ifPresent(para -> para.setValue(
                        ConfigColumnFactory.configureColumn(Pair.of(this.typeColumn, Optional.of(para.getValue())))
                                .orElse(para.getValue()))
                );

    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
