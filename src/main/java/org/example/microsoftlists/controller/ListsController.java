package org.example.microsoftlists.controller;

import org.example.microsoftlists.dto.request.ListRequest;
import org.example.microsoftlists.dto.response.ListResponse;
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
public class ListsController {
    MicrosoftListService listsService = new MicrosoftListService();

    @GetMapping("/lists")
    public ResponseEntity<List<MicrosoftList>> getLists() {
        try {
            List<MicrosoftList> lists = listsService.loadLists();
            return ResponseEntity.ok(lists);
        } catch (IOException e) {
            log.error("Error loading lists", e);
            return ResponseEntity.badRequest().build();
        }
    }



    @PostMapping("/lists")
    public ResponseEntity<MicrosoftList> createList(ListRequest request) {
        try {
            MicrosoftList list = listsService.create(request);
            return ResponseEntity.ok(list);
        } catch (IOException e) {
            log.error("Error creating list", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/lists/{id}")
    public ResponseEntity<ListResponse> getListById(@PathVariable String id) {
        try {
            ListResponse list = listsService.findById(id);
            return ResponseEntity.ok(list);
        } catch (IOException e) {
            log.error("Error loading list", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/lists/{id}")
    public ResponseEntity<MicrosoftList> updateList(@PathVariable String id, MicrosoftList list) {
        try {
            listsService.update(id, list);
            return ResponseEntity.ok(list);
        } catch (IOException e) {
            log.error("Error updating list", e);
            return ResponseEntity.badRequest().build();
        }
    }




    @DeleteMapping("/lists/{id}")
    public ResponseEntity<Boolean> deleteList(@PathVariable String id) {
        try {
            return ResponseEntity.ok(listsService.delete(id));
        } catch (IOException e) {
            log.error("Error deleting list", e);
            return ResponseEntity.badRequest().build();
        }
    }



}
