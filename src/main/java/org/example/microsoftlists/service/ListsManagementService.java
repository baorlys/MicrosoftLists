package org.example.microsoftlists.service;

import org.example.microsoftlists.config.Configuration;
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
import org.example.microsoftlists.view.dto.response.RowResponse;
import org.example.microsoftlists.view.dto.response.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ListsManagementService {
    private final MicrosoftListRepository listRepository;
    private final ColumnRepository colRepository;
    private final ConfigRepository configRepository;
    private final RowRepository rowRepository;
    private final CellRepository cellRepository;
    private final TemplateRepository templateRepository;


    @Autowired
    public ListsManagementService(
            MicrosoftListRepository listRepository,
            ColumnRepository colRepository,
            ConfigRepository configRepository,
            RowRepository rowRepository,
            TemplateRepository templateRepository,
            CellRepository cellRepository) {
        this.listRepository = listRepository;
        this.colRepository = colRepository;
        this.configRepository = configRepository;
        this.rowRepository = rowRepository;
        this.templateRepository = templateRepository;
        this.cellRepository = cellRepository;
    }

    public ListResponse findById(String id) {
        MicrosoftList list = Optional.of(listRepository.findById(id)).get().orElse(null);
        CommonService.throwIsNull(list, "List not found");

        Objects.requireNonNull(list);
        list.setColumns(findAllColsOfList(id));
        list.setRows(findAllRowsOfList(id, PageRequest.of(0,Configuration.PAGE_SIZE)));

        return MapperUtil.mapper.map(list, ListResponse.class);
    }

    public ListResponse findById(String id, Pageable pageable) {
        ListResponse list = findById(id);
        list.setRows(MapperUtil.mapList(findAllRowsOfList(id, pageable), RowResponse.class));
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

    public List<Row> findAllRowsOfList(String listId, Pageable pageable) {
        Page<Row> rows = rowRepository.findAllByListId(listId, pageable);
        return rows != null ? rows.getContent() : Collections.emptyList();
    }

    public List<MicrosoftList> findAll() {
        return listRepository.findAll();
    }

    public Page<MicrosoftList> findAll(Pageable pageable) {
        return listRepository.findAll(pageable);
    }


    public boolean isListExists(String listName)  {
        return findAll().stream()
                .anyMatch(list -> list.getName().equals(listName));

    }

    public ListResponse create(ListRequest listReq) throws NameExistsException {
        CommonService.throwIsExists(isListExists(listReq.getName()), "List name already exists");

        MicrosoftList list = new MicrosoftList();
        MapperUtil.mapper.map(listReq, list);

        listRepository.save(list);

        return MapperUtil.mapper.map(list, ListResponse.class);
    }

    public ListResponse create(String templateId, ListRequest listReq) throws NameExistsException {
        CommonService.throwIsExists(isListExists(listReq.getName()), "List name already exists");

        MicrosoftList list = new MicrosoftList();

        Template template = findTemplateById(templateId);
        MapperUtil.mapper.map(listReq, list);

        list.setColumns(template.getColumns());

        listRepository.save(list);
        saveAllColumn(template.getColumns());

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
        List<MicrosoftList> lists = findAll();
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

    public void saveColumn(Column column) {
        colRepository.save(column);

        List<Config> columnConfigs = column.getConfigs();
        configRepository.saveAll(columnConfigs);


        List<Row> rows = rowRepository.findAllByListId(column.getList().getId());
        for (Row row : rows) {
            Optional<String> defaultVal = Optional.ofNullable(column.getDefaultValue());
            Cell cell = Cell.of(row, column, new SingleObject(defaultVal.orElse("")));
            cellRepository.save(cell);
        }

    }

    public void saveAllColumn(List<Column> columns) {
        for (Column column : columns) {
            saveColumn(column);
        }
    }

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


    //region Template
    public Template saveTemplate(String displayName, List<Column> cols)  {
        Template template = new Template();

        template.setDisplayName(displayName);
        template.setColumns(cloneColumns(cols, template));

        templateRepository.save(template);
        return template;
    }

    private List<Column> cloneColumns(List<Column> cols, Template template) {
        List<Column> colsClone = new ArrayList<>();
        for (Column col : cols) {
            Column clone = col.copy();
            List<Config> configs = new ArrayList<>();
            for (Config config : col.getConfigs()) {
                Config configClone = config.copy(clone);
                configClone.setColumn(clone);
                configs.add(configClone);
            }
            clone.setConfigs(configs);
            clone.setTemplate(template);
            colsClone.add(clone);
        }
        return colsClone;
    }

    public boolean deleteTemplateById(String id) {
        colRepository.deleteAllByTemplateId(id);
        templateRepository.deleteById(id);
        return true;
    }

    public Template findTemplateById(String id)  {
        Template template = Optional.of(templateRepository.findById(id)).get().orElse(null);
        CommonService.throwIsNull(template, "Template not found");
        return template;
    }

    public TemplateResponse getTemplateResponseById(String id) {
        Template template = findTemplateById(id);
        return MapperUtil.mapper.map(template, TemplateResponse.class);
    }

    public Page<Template> findAllTemplate(Pageable pageable) {
        return templateRepository.findAll(pageable);
    }
    //endregion


}
