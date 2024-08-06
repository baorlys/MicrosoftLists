package org.example.microsoftlists.service;

import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.view.dto.response.ListResponse;
import org.example.microsoftlists.view.dto.response.RowResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PagingService {
    @Autowired
    MicrosoftListService listsService;


    public ListResponse getList(String id, int page)  {
        ListResponse list = listsService.findById(id);
        List<RowResponse> rows = list.getRows();

        int pageSize = Configuration.PAGE_SIZE;
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, rows.size());

        list.setRows(rows.subList(start, end));
        return list;
    }
}
