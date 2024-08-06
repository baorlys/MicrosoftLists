package org.example.microsoftlists.service;

import jakarta.transaction.Transactional;
import org.example.microsoftlists.exception.NameExistsException;
import org.example.microsoftlists.model.*;
import org.example.microsoftlists.model.value.SingleObject;
import org.example.microsoftlists.repository.*;
import org.example.microsoftlists.service.builder.ColumnBuilder;
import org.example.microsoftlists.view.dto.MapperUtil;
import org.example.microsoftlists.view.dto.request.ColumnRequest;
import org.example.microsoftlists.view.dto.request.ListRequest;

import org.example.microsoftlists.view.dto.request.ParaRequest;
import org.example.microsoftlists.view.dto.response.ListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class MicrosoftListService {
    @Autowired
    private MicrosoftListRepository listRepository;
    @Autowired
    private ColumnRepository colRepository;
    @Autowired
    private ConfigRepository configRepository;
    @Autowired
    private RowRepository rowRepository;
    @Autowired
    private TemplateService templateService;

    @Autowired
    private CellRepository cellRepository;

    public ListResponse findById(String id) {
        MicrosoftList list = Optional.of(listRepository.findById(id)).get().orElse(null);
        CommonService.throwIsNull(list, "List not found");

        Objects.requireNonNull(list);
        list.setColumns(findAllColsOfList(id));
        list.setRows(findAllRowsOfList(id));

        return MapperUtil.mapper.map(list, ListResponse.class);
    }

    public List<Column> findAllColsOfList(String listId) {
        List<Column> columns = colRepository.findAllByListId(listId);

        for (Column column : columns) {
            List<Config> configs = configRepository.findAllByColumnId(column.getId());
            column.setConfigs(configs);
        }

        return columns;
    }

    public List<Row> findAllRowsOfList(String listId) {
        return rowRepository.findAllByListId(listId);
    }

    public List<MicrosoftList> loadLists() {
        return listRepository.findAll();
    }

    public List<ListResponse> loadListsResponse() {
        List<MicrosoftList> lists = loadLists();
        List<ListResponse> listResponses = new ArrayList<>();
        for (MicrosoftList list : lists) {
            listResponses.add(MapperUtil.mapper.map(list, ListResponse.class));
        }
        return listResponses;
    }

    public boolean isListExists(String listName)  {
        return loadLists().stream()
                .anyMatch(list -> list.getName().equals(listName));

    }

    public MicrosoftList create(ListRequest listReq) throws NameExistsException {
        CommonService.throwIsExists(isListExists(listReq.getName()), "List name already exists");

        MicrosoftList list = new MicrosoftList();
        MapperUtil.mapper.map(listReq, list);

        listRepository.save(list);

        return list;
    }

    public ListResponse create(String templateId, ListRequest listReq) throws NameExistsException {
        CommonService.throwIsExists(isListExists(listReq.getName()), "List name already exists");

        MicrosoftList list = new MicrosoftList();

        Template template = templateService.find(templateId);
        MapperUtil.mapper.map(listReq, list);

        list.setColumns(template.getColumns());

        listRepository.save(list);
        saveAll(template.getColumns());

        return MapperUtil.mapper.map(list, ListResponse.class);
    }



    public void delete(String id) {
        listRepository.deleteById(id);
    }

    public void update(String id, MicrosoftList list) {
        MicrosoftList oldList = Optional.of(listRepository.findById(id)).get().orElse(null);
        CommonService.throwIsNull(oldList, "List not found");

        Objects.requireNonNull(oldList);
        list.setId(oldList.getId());
        listRepository.save(list);
    }



    public ListResponse findByName(String listName) {
        List<MicrosoftList> lists = loadLists();
        MicrosoftList list = lists.stream()
                .filter(i -> i.getName().equals(listName))
                .findFirst()
                .orElse(null);
        return MapperUtil.mapper.map(list, ListResponse.class);
    }


    //region Column
    public Column generateColumn(ColumnRequest column) {
        List<ParaRequest> pars = column.getConfigs();

        List<Config> configs = new ArrayList<>();

        Column res = new ColumnBuilder(column.getType(), column.getName())
                .build();

        for (ParaRequest para : pars) {
            configs.add(new Config(res, para.getName(), para.getValue()));
        }
        res.setConfigs(configs);

        return res;
    }

    public void save(Column column) {
        colRepository.save(column);

        List<Config> columnConfigs = column.getConfigs();
        configRepository.saveAll(columnConfigs);


        List<Row> rows = rowRepository.findAllByListId(column.getList().getId());
        for (Row row : rows) {
            Optional<String> defaultVal = Optional.ofNullable(column.getDefaultValue());
            row.addCell(Cell.of(row, column, new SingleObject(defaultVal.orElse(""))));
            cellRepository.save(row.getCells().get(row.getCells().size() - 1));
        }

    }

    public void saveAll(List<Column> columns) {
        for (Column column : columns) {
            save(column);
        }
    }

    @Transactional
    public void deleteColumn(String id) {
        cellRepository.deleteAllByColumnId(id);
        configRepository.deleteAllByColumnId(id);
        colRepository.deleteById(id);
    }

    public void updateColumn(String id, Column column)  {
        Column oldColumn = Optional.of(colRepository.findById(id)).get().orElse(null);
        CommonService.throwIsNull(oldColumn, "Column not found");

        Objects.requireNonNull(oldColumn);
        column.setId(oldColumn.getId());
        colRepository.save(column);

    }

    public Column findColumnById(String id) {
        Column column = Optional.of(colRepository.findById(id)).get().orElse(null);
        CommonService.throwIsNull(column, "Column not found");

        Objects.requireNonNull(column);
        List<Config> configs = configRepository.findAllByColumnId(id);
        column.setConfigs(configs);

        return column;
    }
    //endregion



}
