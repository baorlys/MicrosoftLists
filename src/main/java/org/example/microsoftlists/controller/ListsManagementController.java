package org.example.microsoftlists.controller;

import jakarta.validation.Valid;
import org.example.microsoftlists.view.dto.request.ListRequest;
import org.example.microsoftlists.view.dto.response.ListResponse;
import org.example.microsoftlists.model.MicrosoftList;
import org.example.microsoftlists.service.MicrosoftListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/lists-management")
public class ListsManagementController {
    MicrosoftListService listsService = new MicrosoftListService();

    @GetMapping("/lists")
    public ResponseEntity<List<MicrosoftList>> getLists() throws IOException {
        List<MicrosoftList> lists = listsService.loadLists();
        return ResponseEntity.ok(lists);
    }



    @PostMapping("/lists")
    public ResponseEntity<MicrosoftList> createList(@Valid @RequestBody ListRequest request) throws IOException {
        MicrosoftList list = listsService.create(request);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/lists/{id}")
    public ResponseEntity<ListResponse> getListById(@PathVariable String id) throws IOException {
        ListResponse list = listsService.findById(id);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/lists/{id}")
    public ResponseEntity<MicrosoftList> updateList(@PathVariable String id,
                                                    @RequestBody MicrosoftList list) throws IOException {
        listsService.update(id, list);
        return ResponseEntity.ok(list);
    }


    @DeleteMapping("/lists/{id}")
    public ResponseEntity<Boolean> deleteList(@PathVariable String id) throws IOException {
        return ResponseEntity.ok(listsService.delete(id));
    }



}
