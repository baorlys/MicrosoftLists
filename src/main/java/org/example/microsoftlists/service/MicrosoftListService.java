package org.example.microsoftlists.service;

import org.example.microsoftlists.exception.NameExistsException;
import org.example.microsoftlists.model.*;
import org.example.microsoftlists.model.value.SingleObject;
import org.example.microsoftlists.repository.ColumnConfigRepository;
import org.example.microsoftlists.repository.ColumnRepository;
import org.example.microsoftlists.repository.RowRepository;
import org.example.microsoftlists.service.builder.ColumnBuilder;
import org.example.microsoftlists.view.dto.MapperUtil;
import org.example.microsoftlists.view.dto.request.ColumnRequest;
import org.example.microsoftlists.view.dto.request.ListRequest;

import org.example.microsoftlists.repository.MicrosoftListRepository;
import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.view.dto.request.ParaRequest;
import org.example.microsoftlists.view.dto.response.ListResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MicrosoftListService {


    private final MicrosoftListRepository listRepository;

    private final ColumnRepository colRepository;

    private final ColumnConfigRepository colConfigRepository;

    private final RowRepository rowRepository;

    TemplateService templateService = new TemplateService();


    public MicrosoftListService() {
        this.listRepository = new MicrosoftListRepository(Configuration.DATA_PATH, Configuration.LISTS_PATH);
        this.colRepository = new ColumnRepository(Configuration.DATA_PATH, Configuration.COLS_PATH);
        this.colConfigRepository = new ColumnConfigRepository(Configuration.DATA_PATH, Configuration.COL_CONFIG_PATH);
        this.rowRepository = new RowRepository(Configuration.DATA_PATH, Configuration.ROWS_PATH);
    }

    public ListResponse findById(String id) throws IOException {
        MicrosoftList list = listRepository.findById(id);

        list.setColumns(findAllColsOfList(id));
        list.setRows(findAllRowsOfList(id));

        return MapperUtil.mapper.map(list, ListResponse.class);
    }

    public List<Column> findAllColsOfList(String listId) throws IOException {
        List<Column> columns = colRepository.findAllByListId(listId);

        for (Column column : columns) {
            List<ColumnConfig> columnConfigs = colConfigRepository.findAllByColumnId(column.getId().toString());
            List<Parameter> parameters = columnConfigs.stream()
                    .map(ColumnConfig::getParameter)
                    .collect(Collectors.toList());
            column.setConfigs(parameters);
        }

        return columns;
    }

    public List<Row> findAllRowsOfList(String listId) throws IOException {
        return rowRepository.findAllByListId(listId);
    }

    public List<MicrosoftList> loadLists() throws IOException {
        return listRepository.findAll();
    }

    public boolean isListExists(String listName) throws IOException {
        return loadLists().stream()
                .anyMatch(list -> list.getName().equals(listName));

    }

    public MicrosoftList create(ListRequest listReq) throws IOException, NameExistsException {
        CommonService.throwIsExists(isListExists(listReq.getName()), "List name already exists");

        MicrosoftList list = new MicrosoftList();
        MapperUtil.mapper.map(listReq, list);

        listRepository.save(list);

        return list;
    }

    public MicrosoftList create(String templateId, ListRequest listReq) throws IOException, NameExistsException {
        CommonService.throwIsExists(isListExists(listReq.getName()), "List name already exists");

        MicrosoftList list = new MicrosoftList();

        Template template = templateService.find(templateId);
        MapperUtil.mapper.map(listReq, list);
        list.setColumns(template.getColumns());

        listRepository.save(list);
        saveAll(template.getColumns());

        return list;
    }



    public void delete(String id) throws IOException {
        listRepository.delete(id);
        colRepository.deleteAllOfList(id);
    }

    public void update(String id, MicrosoftList list) throws IOException {
        listRepository.update(id, list);
        // maybe update columns
    }


    public ListResponse findByName(String listName) throws IOException {
        List<MicrosoftList> lists = loadLists();
        MicrosoftList list = lists.stream()
                .filter(i -> i.getName().equals(listName))
                .findFirst()
                .orElse(null);
        return MapperUtil.mapper.map(list, ListResponse.class);
    }


    //region Column
    public Column generateColumn(ColumnRequest column) {
        List<ParaRequest> configs = column.getConfigs();

        List<Parameter> paras = new ArrayList<>();
        for (ParaRequest para : configs) {
            paras.add(new Parameter(para.getName(), para.getValue()));
        }

        return new ColumnBuilder(column.getType(), column.getName())
                .configure(paras)
                .build();
    }

    public void save(Column column) throws IOException {


        colRepository.save(column);

        List<ColumnConfig> columnConfigs = new ArrayList<>();

        for (Parameter para : column.getConfigs()) {
            ColumnConfig columnConfig = new ColumnConfig(column, para);
            columnConfigs.add(columnConfig);
        }

        List<Row> rows = rowRepository.findAllByListId(column.getList().getId().toString());
        for (Row row : rows) {
            Optional<String> defaultVal = Optional.ofNullable(column.getDefaultValue());
            row.addCell(Cell.of(row, column, new SingleObject(defaultVal.orElse(""))));
            rowRepository.update(row.getId().toString(), row);
        }

        colConfigRepository.saveAll(columnConfigs);
    }

    public void saveAll(List<Column> columns) throws IOException {
        for (Column column : columns) {
            save(column);
        }
    }

    public void deleteColumn(String id) throws IOException {
        colRepository.delete(id);

        colConfigRepository.deleteAllByColumnId(id);

        rowRepository.deleteCellsByColumnId(id);
    }

    public void updateColumn(String id, Column column) throws IOException {
        colRepository.update(id, column);


        colConfigRepository.deleteAllByColumnId(id);

        List<ColumnConfig> columnConfigs = new ArrayList<>();

        for (Parameter para : column.getConfigs()) {
            ColumnConfig columnConfig = new ColumnConfig(column, para);
            columnConfigs.add(columnConfig);
        }

        colConfigRepository.saveAll(columnConfigs);
    }

    public Column findColumnById(String id) throws IOException {
        Column column = colRepository.findById(id);

        List<ColumnConfig> columnConfigs = colConfigRepository.findAllByColumnId(id);

        List<Parameter> parameters = columnConfigs.stream()
                .map(ColumnConfig::getParameter)
                .collect(Collectors.toList());

        column.setConfigs(parameters);

        return column;
    }
    //endregion



}
