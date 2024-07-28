package org.example.microsoftlists.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.model.constants.IdentifyModel;
import org.example.microsoftlists.model.serializer.MicrosoftListSerializer;
import org.example.microsoftlists.model.serializer.RowSerializer;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Getter
@Setter
@JsonSerialize(using = RowSerializer.class)
public class Row implements Identifiable {
    private final String typeIdentify = IdentifyModel.ROW.name();
    private UUID id;
    private MicrosoftList list;

    private List<Cell> cells;
    private LocalDateTime createdAt;

    public Row() {
        this.id = UUID.randomUUID();
        this.cells = new ArrayList<>();
        this.createdAt = getCurrentDate();
    }


    public Row(MicrosoftList list) {
        this.id = UUID.randomUUID();
        this.list = list;
        this.cells = new ArrayList<>();
        this.createdAt = getCurrentDate();
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

    private LocalDateTime getCurrentDate() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Configuration.DATETIME_FORMAT);
        String formattedString = LocalDateTime.now().format(formatter);
        return LocalDateTime.parse(formattedString, formatter);
    }


}
