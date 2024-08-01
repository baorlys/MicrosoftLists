package org.example.microsoftlists.service;

import org.example.microsoftlists.exception.NameExistsException;
import org.example.microsoftlists.model.*;
import org.example.microsoftlists.repository.ColumnConfigRepository;
import org.example.microsoftlists.repository.ColumnRepository;
import org.example.microsoftlists.repository.RowRepository;
import org.example.microsoftlists.view.dto.MapperUtil;
import org.example.microsoftlists.view.dto.request.ListRequest;

import org.example.microsoftlists.repository.MicrosoftListRepository;
import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.view.dto.response.ListResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MicrosoftListService {


    private final MicrosoftListRepository listRepository;

    private final ColumnRepository colRepository;

    private final ColumnConfigRepository colConfigRepository;

    private final RowRepository rowRepository;

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

        Optional.of(isListExists(listReq.getName()))
                .filter(exists -> !exists)
                .orElseThrow(() -> new NameExistsException("List's name already exists"));

        MicrosoftList list = new MicrosoftList();
        MapperUtil.mapper.map(listReq, list);

        listRepository.save(list);

        return list;
    }

    public boolean delete(String id) throws IOException {
        listRepository.delete(id);
        colRepository.deleteAllOfList(id);
        return true;
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




}
