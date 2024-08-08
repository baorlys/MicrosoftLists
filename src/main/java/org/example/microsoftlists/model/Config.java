package org.example.microsoftlists.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.microsoftlists.model.constants.ConfigParameter;
import org.example.microsoftlists.model.converter.IValueConverter;
import org.example.microsoftlists.model.value.IValue;
import org.example.microsoftlists.model.value.ValueFactory;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Config {
    @Id
    private String id;
    @ManyToOne
    @JsonIgnore
    private Column column;
    @Enumerated(EnumType.STRING)
    private ConfigParameter name;
    @Convert(converter = IValueConverter.class)
    private IValue value;




    public Config(Column column, ConfigParameter name, String value) {
        this.id = UUID.randomUUID().toString();
        this.column = column;
        this.name = name;
        this.value = ValueFactory.create(value);
    }

    public Config copy(Column column) {
        Config config = new Config();
        config.setId(UUID.randomUUID().toString());
        config.setColumn(column);
        config.setName(this.getName());
        config.setValue(this.getValue().get());
        return config;
    }



    public void setValue(String value) {
        this.value = ValueFactory.create(value);
    }


}
