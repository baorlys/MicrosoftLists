package model.microsoft.list;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.microsoft.list.deserializer.TypeDeserializer;
import model.microsoft.list.deserializer.ValueDeserializer;
import model.microsoft.list.type.TypeFactory;
import model.microsoft.list.value.IValue;
import model.microsoft.list.value.ValueFactory;

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
