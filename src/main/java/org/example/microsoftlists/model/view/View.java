package org.example.microsoftlists.model.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.example.microsoftlists.model.MicrosoftList;
import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.constants.ViewConfig;
import org.example.microsoftlists.model.converter.DataViewConverter;
import org.example.microsoftlists.model.converter.IViewConverter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "views")
public class View {
    @Id
    private String id;
    @ManyToOne
    @JsonIgnore
    private MicrosoftList list;
    private String name;
    @Convert(converter = IViewConverter.class)
    private IView type;

    @Convert(converter = DataViewConverter.class)
    Map<ViewConfig,String> configs;

    public View(String name, IView type, Map<ViewConfig, String> configs) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
        this.configs = configs;

    }
}
