package model.microsoft.list;

import config.Configuration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import model.microsoft.list.view.AbstractView;
import service.file.SaveService;
import model.microsoft.list.value.SingleObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Getter
@Setter
@AllArgsConstructor
public class MicrosoftList {
    private String name;
    private String description;
    private Color color;
    private String iconId;
    private List<Column> columns;
    private List<Row> rows;
    private List<AbstractView> views;
    private int pageSize = Configuration.PAGE_SIZE;
    private int pageNumber = Configuration.PAGE_NUMBER;

    public MicrosoftList() {
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.views = new ArrayList<>();
    }

    public MicrosoftList(List<Column> columnsFilter, List<Row> rowsFilter) {
        this.columns = columnsFilter;
        this.rows = rowsFilter;
    }


    public void addColumn(Column column) {
        this.columns.add(column);
        try {
            SaveService.saveStructure(this);
        } catch (Exception e) {
            Logger.getAnonymousLogger().warning(e.getMessage());
        }
    }

    public void addRow() {
        Row row = new Row(this);

        for (Column column : this.columns) {
            Optional<Object> defaultVal = Optional.ofNullable(column.getDefaultValue());
            row.addCell(Cell.of(row, column, new SingleObject(defaultVal.orElse(""))));
        }

        this.rows.add(row);
        try {
            SaveService.saveData(this);
        } catch (Exception e) {
            Logger.getAnonymousLogger().warning(e.getMessage());
        }
    }

    public void addRow(Row row) {
        this.rows.add(row);
        try {
            SaveService.saveData(this);
        } catch (Exception e) {
            Logger.getAnonymousLogger().warning(e.getMessage());
        }
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
        try {
            SaveService.saveStructure(this);
        } catch (Exception e) {
            Logger.getAnonymousLogger().warning(e.getMessage());
        }

    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
        try {
            SaveService.saveData(this);
        } catch (Exception e) {
            Logger.getAnonymousLogger().warning(e.getMessage());
        }
    }




    public void createView(AbstractView view) {
        this.views.add(view);
        try {
            SaveService.saveView(this);
        } catch (Exception e) {
            Logger.getAnonymousLogger().warning("Failed to save view");
        }
    }
}
