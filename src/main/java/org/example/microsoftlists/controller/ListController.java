package org.example.microsoftlists.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.microsoftlists.dto.request.ColumnRequest;
import org.example.microsoftlists.dto.response.ListResponse;
import org.example.microsoftlists.service.ListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/list")
public class ListController {

    ListService listService = new ListService();

    @PostMapping("/{id}")
    public ResponseEntity<ListResponse> createColumn(@PathVariable String id,@RequestBody ColumnRequest column) {
        try {
            ListResponse list = listService.createColumn(id, column);
            return ResponseEntity.ok(list);
        } catch (IOException e) {
            log.error("Error creating list", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/column/{columnId}")
    public ResponseEntity<ListResponse> updateColumn(@PathVariable String id, @PathVariable String columnId, @RequestBody ColumnRequest column) {
        try {
            ListResponse list = listService.updateColumn(id, columnId, column);
            return ResponseEntity.ok(list);
        } catch (IOException e) {
            log.error("Error updating list", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}/column/{columnId}")
    public ResponseEntity<ListResponse> deleteColumn(@PathVariable String id, @PathVariable String columnId) {
        try {
            ListResponse list = listService.deleteColumn(id, columnId);
            return ResponseEntity.ok(list);
        } catch (IOException e) {
            log.error("Error deleting list", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/row")
    public ResponseEntity<ListResponse> createRow(@PathVariable String id) {
        try {
            ListResponse list = listService.createRow(id);
            return ResponseEntity.ok(list);
        } catch (IOException e) {
            log.error("Error creating list", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/row/{rowId}/{columnId}")
    public ResponseEntity<ListResponse> updateRow(@PathVariable String id, @PathVariable String rowId, @PathVariable String columnId, @RequestBody Object value) {
        try {
            ListResponse list = listService.updateRow(id, rowId, columnId, value);
            return ResponseEntity.ok(list);
        } catch (IOException e) {
            log.error("Error updating list", e);
            return ResponseEntity.badRequest().build();
        }
    }

}
