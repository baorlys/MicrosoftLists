package org.example.microsoftlists.service;

import org.example.microsoftlists.view.dto.MapperUtil;
import org.example.microsoftlists.view.dto.request.ListRequest;

import org.example.microsoftlists.model.MicrosoftList;
import org.example.microsoftlists.repository.MicrosoftListRepository;
import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.view.dto.response.ListResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MicrosoftListService {

    private static final String DIR_PATH = Configuration.DATA_PATH;
    private static final String LISTS_PATH = Configuration.LISTS_PATH;

    private final MicrosoftListRepository listRepository;
    private final ListService listService;

    public MicrosoftListService() {
        this.listRepository = new MicrosoftListRepository(DIR_PATH, LISTS_PATH);
        this.listService = new ListService();
    }

    public ListResponse findById(String id) throws IOException {
        MicrosoftList list = listRepository.findById(id);
        return MapperUtil.mapper.map(list, ListResponse.class);
    }

    public List<MicrosoftList> loadLists() throws IOException {
        return listRepository.findAll();
    }

    public MicrosoftList create(ListRequest request) throws IOException {
        MicrosoftList list = new MicrosoftList();
        MapperUtil.mapper.map(request, list);

        listRepository.save(list);

        return list;
    }

    public boolean delete(String id) throws IOException {
        listRepository.delete(id);
        listService.deleteAllColumns(id);
        return true;
    }

    public void update(String id, MicrosoftList list) throws IOException {
        listRepository.update(id, list);
        // maybe update columns
    }


    public MicrosoftList findByName(String listName) throws IOException {
        List<MicrosoftList> lists = loadLists();
        return lists.stream()
                .filter(list -> list.getName().equals(listName))
                .findFirst()
                .orElse(null);
    }




}
