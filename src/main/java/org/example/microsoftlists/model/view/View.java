package org.example.microsoftlists.model.view;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.example.microsoftlists.model.MicrosoftList;
import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.model.constants.ViewType;
import org.example.microsoftlists.model.converter.DataViewConverter;

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
    private MicrosoftList list;
    private String name;
    @Enumerated(EnumType.STRING)
    private ViewType type;

    @Convert(converter = DataViewConverter.class)
    Map<String,String> data;

    public View(String name, ViewType type, Map<String, String> data) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
        this.data = data;

    }
}
