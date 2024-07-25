package org.example.microsoftlists.model.microsoft.list;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class Row {
    @JsonIgnore
    private MicrosoftList list;

    private List<Cell> cells;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;


    public Row(MicrosoftList list) {
        this.list = list;
        this.cells = new ArrayList<>();
        this.createdAt = new Date();
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

}
