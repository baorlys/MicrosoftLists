package model.listitem;


import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
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
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
