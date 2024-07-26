package org.example.microsoftlists.service;

import org.example.microsoftlists.dto.request.ListDTO;
import org.example.microsoftlists.dto.response.ListResponse;
import org.example.microsoftlists.model.microsoft.list.Column;
import org.example.microsoftlists.model.microsoft.list.MicrosoftList;
import org.example.microsoftlists.repository.MicrosoftListRepository;
import org.example.microsoftlists.config.Configuration;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MicrosoftListService {

    private static final String DIR_PATH = Configuration.DATA_PATH;
    private static final String LISTS_PATH = Configuration.LISTS_PATH;

    private final MicrosoftListRepository listRepository;

    private final ColumnService colService;

    public MicrosoftListService() {
        this.colService = new ColumnService();
        this.listRepository = new MicrosoftListRepository(DIR_PATH, LISTS_PATH);
    }


    public List<MicrosoftList> loadLists() throws IOException {
        List<MicrosoftList> lists = listRepository.findAll();

        for (MicrosoftList list : lists) {
            List<Column> columns = colService.findAllOfList(list.getId().toString());
            list.setColumns(columns);
        }

        return lists;
    }

    public MicrosoftList create(ListDTO request) throws IOException {
        MicrosoftList list = new MicrosoftList();
        list.setName(request.getName());
        list.setDescription(request.getDescription());

        listRepository.save(list);

        return list;
    }

    public boolean delete(String id) throws IOException {
        colService.deleteAllOfList(id);

        listRepository.delete(id);
        return true;
    }

    public void update(String id, MicrosoftList list) throws IOException {
        listRepository.update(id, list);
        // maybe update columns
    }

    public ListResponse findById(String id) throws IOException {
        MicrosoftList list = listRepository.findById(id);
        ListResponse listResponse = new ListResponse(list);

        List<Column> columns = colService.findAllOfList(id);
        listResponse.setColumns(columns);

        return listResponse;


    }

    public MicrosoftList findByName(String listName) throws IOException {
        List<MicrosoftList> lists = loadLists();
        return lists.stream()
                .filter(list -> list.getName().equals(listName))
                .findFirst()
                .orElse(null);
    }




}
