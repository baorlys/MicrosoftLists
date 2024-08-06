package org.example.microsoftlists.controller;

import jakarta.validation.Valid;
import org.example.microsoftlists.exception.NameExistsException;
import org.example.microsoftlists.exception.InvalidValueException;
import org.example.microsoftlists.service.*;
import org.example.microsoftlists.view.ApiSuccess;
import org.example.microsoftlists.view.dto.request.ColumnRequest;
import org.example.microsoftlists.view.dto.request.RowRequest;
import org.example.microsoftlists.view.dto.response.ListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;


@RestController
@RequestMapping("/api/list")
public class ListController {

    @Autowired
    ListService listService;
    @Autowired

    SortingService sortingService;
    @Autowired

    PagingService pagingService;
    @Autowired

    SearchingService searchingService;
    @Autowired

    GroupService groupService;

    @PostMapping("/{id}")
    public ResponseEntity<ApiSuccess> createColumn(@PathVariable String id,
                                                   @Valid @RequestBody ColumnRequest column)
            throws NameExistsException {

        ListResponse list = listService.createColumn(id, column);
        return ResponseEntity.ok(new ApiSuccess("Column created successfully", list));

    }

    @PutMapping("/{id}/column/{columnId}")
    public ResponseEntity<ApiSuccess> updateColumn(@PathVariable String id,
                                                     @PathVariable String columnId,
                                                     @Valid @RequestBody ColumnRequest column) {
        ListResponse list = listService.updateColumn(id, columnId, column);
        return ResponseEntity.ok(new ApiSuccess("Column updated successfully", list));

    }

    @DeleteMapping("/{id}/column/{columnId}")
    public ResponseEntity<ApiSuccess> deleteColumn(@PathVariable String id,
                                                     @PathVariable String columnId) {
        ListResponse list = listService.deleteColumn(id, columnId);
        return ResponseEntity.ok(new ApiSuccess("Column deleted successfully", list));
    }

    @PostMapping("/{id}/row")
    public ResponseEntity<ApiSuccess> createRow(@PathVariable String id)
            throws  InvalidValueException {

        ListResponse list = listService.createRow(id);
        return ResponseEntity.ok(new ApiSuccess("Row created successfully", list));
    }

    @PostMapping("/{id}/row/values")
    public ResponseEntity<ApiSuccess> createRow(@PathVariable String id,
                                                  @RequestBody RowRequest rowRequest)
            throws  InvalidValueException {

        ListResponse list = listService.createRow(id, rowRequest);
        return ResponseEntity.ok(new ApiSuccess("Row created successfully", list));
    }

    @PutMapping("/{id}/row/{rowId}/{columnId}")
    public ResponseEntity<ApiSuccess> updateRow(@PathVariable String id,
                                                  @PathVariable String rowId,
                                                  @PathVariable String columnId,
                                                  @RequestBody String value)
            throws InvalidValueException {

            ListResponse list = listService.updateRow(id, rowId, columnId, value);
            return ResponseEntity.ok(new ApiSuccess("Row updated successfully", list));
    }

    @DeleteMapping("/{id}/row/{rowId}")
    public ResponseEntity<ApiSuccess> deleteRow(@PathVariable String id,
                                                  @PathVariable String rowId) {
        ListResponse list = listService.deleteRow(id, rowId);
        return ResponseEntity.ok(new ApiSuccess("Row deleted successfully", list));
    }

    @GetMapping("/{id}/sort/{columnId}/{order}")
    public ResponseEntity<ApiSuccess> sortList(@PathVariable String id,
                                               @PathVariable String columnId,
                                               @PathVariable SortOrder order) {
        ListResponse list = sortingService.sort(id, columnId, order);
        return ResponseEntity.ok(new ApiSuccess("List sorted successfully", list));
    }

    @GetMapping("/{id}/group/{columnId}")
    public ResponseEntity<ApiSuccess> groupList(@PathVariable String id,
                                                 @PathVariable String columnId) {
        return ResponseEntity.ok(new ApiSuccess("List grouped successfully", groupService.groupBy(id, columnId)));
    }

    @GetMapping("/{id}/search/{key}")
    public ResponseEntity<ApiSuccess> searchList(@PathVariable String id,
                                                   @PathVariable String key)  {
        ListResponse list = searchingService.search(id, key);
        return ResponseEntity.ok(new ApiSuccess("List searched successfully", list));
    }

    @GetMapping("/{id}/{page}")
    public ResponseEntity<ApiSuccess> getList(@PathVariable String id,
                                                @PathVariable int page){
        ListResponse list = pagingService.getList(id, page);
        return ResponseEntity.ok(new ApiSuccess("List fetched successfully", list));
    }


}
