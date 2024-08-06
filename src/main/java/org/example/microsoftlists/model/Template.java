package org.example.microsoftlists.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Template  {

    @Id
    private String id;
    private String displayName;

    @OneToMany(mappedBy = "template",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Column> columns;



    public Template() {
        this.id = UUID.randomUUID().toString();
        this.columns = new ArrayList<>();
    }



}
