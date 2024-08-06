package org.example.microsoftlists.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.converter.ITypeConverter;
import org.example.microsoftlists.model.type.IType;
import org.example.microsoftlists.model.value.IValue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "columns")
public class Column  {
    @Id
    private String id;
    @ManyToOne
    private MicrosoftList list;
    private String name;
    @Convert(converter = ITypeConverter.class)
    private IType type;
    @OneToMany(mappedBy = "column", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Config> configs;

    @ManyToOne
    @JsonIgnore
    private Template template;
    private boolean isHidden;


    public Column() {
        this.id = UUID.randomUUID().toString();
        this.isHidden = false;
    }


    public void setConfigs(List<Config> configs) {
        this.configs = configs;
    }

    public boolean isValidValue(IValue value) {
        return this.type.isValueValid(configs, value)
                || value.get().isEmpty()
                || value.get().equals("");
    }

    public int compare(IValue o1, IValue o2) {
        return this.type.compare(o1, o2);
    }

    @JsonIgnore
    public String getDefaultValue() {
        return Optional.ofNullable(this.type.handleDefault(configs))
                .map(IValue::get)
                .orElse("");
    }

    public Column copy()  {
        Column cloned = new Column();

        cloned.name = this.name;
        cloned.type = this.type;
        cloned.configs = this.configs;


        return cloned;
    }
}
