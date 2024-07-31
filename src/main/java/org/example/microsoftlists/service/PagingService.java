package org.example.microsoftlists.service;

import org.example.microsoftlists.config.Configuration;
import org.example.microsoftlists.view.dto.response.ListResponse;
import org.example.microsoftlists.view.dto.response.RowResponse;

import java.io.IOException;
import java.util.List;

public class PagingService {
    private final MicrosoftListService listsService;

    public PagingService() {
        this.listsService = new MicrosoftListService();
    }

    public ListResponse getList(String id, int page) throws IOException {
        ListResponse list = listsService.findById(id);
        List<RowResponse> rows = list.getRows();

        int pageSize = Configuration.PAGE_SIZE;
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, rows.size());

        list.setRows(rows.subList(start, end));
        return list;
    }
}
