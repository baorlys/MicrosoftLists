package model.microsoftlist;

import lombok.Getter;
import lombok.Setter;
import service.file.SaveService;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Getter
@Setter
public class MicrosoftList {
    private String name;
    private String description;
    private Color color;
    private String iconId;
    private List<Column> columns;
    private List<Row> rows;
    public MicrosoftList() {
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
    }

    public void addColumn(Column column) {
        this.columns.add(column);
        try {
            SaveService.saveStructure(this);
        } catch (Exception e) {
            Logger.getAnonymousLogger().warning("Failed to save structure");
        }
    }

    public void addRow() {
        Row row = new Row(this);

        for (Column column : this.columns) {
            row.addCell(Cell.of(row, column, ""));
        }

        this.rows.add(row);
        try {
            SaveService.saveData(this);
        } catch (Exception e) {
            Logger.getAnonymousLogger().warning("Failed to save data");
        }
    }
}
