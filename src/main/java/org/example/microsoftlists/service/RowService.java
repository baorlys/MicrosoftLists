package org.example.microsoftlists.service;

import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.dto.response.ColumnResponse;
import org.example.microsoftlists.dto.response.ListResponse;
import org.example.microsoftlists.model.Cell;
import org.example.microsoftlists.model.Column;
import org.example.microsoftlists.model.MicrosoftList;
import org.example.microsoftlists.model.value.IValue;
import org.example.microsoftlists.model.Row;
import org.example.microsoftlists.model.value.SingleObject;
import org.example.microsoftlists.model.value.ValueFactory;
import org.example.microsoftlists.repository.RowRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RowService {
    private static final String DIR_PATH = Configuration.DATA_PATH;
    private static final String ROWS_PATH = Configuration.ROWS_PATH;
    private final RowRepository rowRepository;


    public RowService() {
        this.rowRepository = new RowRepository(DIR_PATH, ROWS_PATH);
    }

    public Row generateRow(ListResponse list) {
        List<ColumnResponse> columns = list.getColumns();

        Row row = new Row();

        row.setList(MicrosoftList.of(list));

        for (ColumnResponse column : columns) {
            Optional<Object> defaultVal = Optional.ofNullable(column.getDefaultValue());
            row.addCell(Cell.of(row, Column.of(column) , new SingleObject(defaultVal.orElse(""))));
        }

        return row;
    }

    public void save(Row row) throws IOException {
        rowRepository.save(row);
    }

    public Row findRowById(String rowId) throws IOException {
        return rowRepository.findById(rowId);
    }


    public void updateCell(String rowId, String columnId, Object value) throws IOException {
        Row row = findRowById(rowId);

        List<Cell> cells = row.getCells();

        cells.stream()
                .filter(cell -> cell.getColumn().getId().equals(UUID.fromString(columnId)))
                .findFirst()
                .ifPresent(cell -> {
                    IValue val = ValueFactory.create(value);
                    cell.setValue(val);
                });

        row.setCells(cells);

        rowRepository.update(rowId, row);

    }
}
