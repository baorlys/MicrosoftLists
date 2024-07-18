package model.microsoftlist.type;

import model.constants.TypeColumn;
import model.microsoftlist.Parameter;

import java.util.List;



public abstract class AbstractType {
    private final TypeColumn typeColumn;

    protected AbstractType(TypeColumn type) {
        this.typeColumn = type;

    }

    public List<Parameter> handle(List<Parameter> config) {
        return this.handleConfig(config);
    }
    protected abstract List<Parameter> handleConfig(List<Parameter> config);

    public TypeColumn getType() {
        return typeColumn;
    }



}
