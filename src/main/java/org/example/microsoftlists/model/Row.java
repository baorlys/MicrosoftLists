package org.example.microsoftlists.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.microsoftlists.config.Configuration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Getter
@Setter
@Entity
public class Row {
    @Id
    private String id;
    @ManyToOne
    private MicrosoftList list;

    @OneToMany(mappedBy = "row",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cell> cells;
    private LocalDateTime createdAt;

    public Row() {
        this.id = UUID.randomUUID().toString();
        this.cells = new ArrayList<>();
        this.createdAt = getCurrentDate();
    }


    public Row(MicrosoftList list) {
        this.id = UUID.randomUUID().toString();
        this.list = list;
        this.cells = new ArrayList<>();
        this.createdAt = getCurrentDate();
    }

    public void addCell(Cell cell) {
        cell.setRow(this);
        cells.add(cell);
    }

    private LocalDateTime getCurrentDate() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Configuration.DATETIME_FORMAT);
        String formattedString = LocalDateTime.now().format(formatter);
        return LocalDateTime.parse(formattedString, formatter);
    }


}
