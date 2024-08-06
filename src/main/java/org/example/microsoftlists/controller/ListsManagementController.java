package org.example.microsoftlists.controller;

import jakarta.validation.Valid;
import org.example.microsoftlists.exception.NameExistsException;
import org.example.microsoftlists.view.ApiSuccess;
import org.example.microsoftlists.view.dto.request.ListRequest;
import org.example.microsoftlists.view.dto.response.ListResponse;
import org.example.microsoftlists.model.MicrosoftList;
import org.example.microsoftlists.service.MicrosoftListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/lists-management")
public class ListsManagementController {
    @Autowired
    private MicrosoftListService listsService;


    @GetMapping("/lists")
    public ResponseEntity<ApiSuccess> getLists()  {
        List<ListResponse> lists = listsService.loadListsResponse();
        return ResponseEntity.ok(new ApiSuccess("Lists loaded successfully", lists));
    }



    @PostMapping("/lists")
    public ResponseEntity<ApiSuccess> createList(@Valid @RequestBody ListRequest request)
            throws NameExistsException {
        MicrosoftList list = listsService.create(request);
        return ResponseEntity.ok(new ApiSuccess("List created successfully", list));
    }

    @PostMapping("/lists/{templateId}")
    public ResponseEntity<ApiSuccess> createList(@PathVariable String templateId,
                                                 @Valid @RequestBody ListRequest listReq)
            throws NameExistsException {
        ListResponse list = listsService.create(templateId, listReq);
        return ResponseEntity.ok(new ApiSuccess("List created successfully", list));
    }

    @GetMapping("/lists/{id}")
    public ResponseEntity<ApiSuccess> getListById(@PathVariable String id) {
        ListResponse list = listsService.findById(id);
        return ResponseEntity.ok(new ApiSuccess("List loaded successfully", list));
    }

    @GetMapping("/lists/{name}/")
    public ResponseEntity<ApiSuccess> getListByName(@PathVariable String name)  {
        ListResponse list = listsService.findByName(name);
        return ResponseEntity.ok(new ApiSuccess("List loaded successfully", list));
    }

    @PutMapping("/lists/{id}")
    public ResponseEntity<ApiSuccess> updateList(@PathVariable String id,
                                                    @RequestBody MicrosoftList list) {
        listsService.update(id, list);
        return ResponseEntity.ok(new ApiSuccess("List updated successfully", list));
    }


    @DeleteMapping("/lists/{id}")
    public ResponseEntity<ApiSuccess> deleteList(@PathVariable String id) {
        listsService.delete(id);
        return ResponseEntity.ok(new ApiSuccess("List deleted successfully", listsService.loadLists()));
    }



}
