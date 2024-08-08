package org.example.microsoftlists.controller;

import jakarta.validation.Valid;
import org.example.microsoftlists.exception.NameExistsException;
import org.example.microsoftlists.model.Column;
import org.example.microsoftlists.model.Template;
import org.example.microsoftlists.view.ApiSuccess;
import org.example.microsoftlists.view.dto.request.ListRequest;
import org.example.microsoftlists.view.dto.request.TemplateRequest;
import org.example.microsoftlists.model.MicrosoftList;
import org.example.microsoftlists.service.ListsManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/lists-management")
public class ListsManagementController {
    private final ListsManagementService listsService;


    @Autowired
    public ListsManagementController(ListsManagementService listsService) {
        this.listsService = listsService;
    }


    @GetMapping("/lists")
    public ResponseEntity<ApiSuccess> getLists(
            @PageableDefault() Pageable pageable)  {

        return ResponseEntity.ok(new ApiSuccess("Lists loaded successfully", listsService.findAll(pageable)));
    }



    @PostMapping("/lists")
    public ResponseEntity<ApiSuccess> createList(@Valid @RequestBody ListRequest request)
            throws NameExistsException {

        return ResponseEntity.ok(new ApiSuccess("List created successfully", listsService.create(request)));
    }

    @PostMapping("/lists/{templateId}")
    public ResponseEntity<ApiSuccess> createList(@PathVariable String templateId,
                                                 @Valid @RequestBody ListRequest listReq)
            throws NameExistsException {

        return ResponseEntity.ok(new ApiSuccess("List created successfully", listsService.create(templateId, listReq)));
    }

    @GetMapping("/lists/{id}")
    public ResponseEntity<ApiSuccess> getListById(@PathVariable String id,
                                                  @PageableDefault() Pageable pageable)  {

        return ResponseEntity.ok(new ApiSuccess("List loaded successfully", listsService.findById(id,pageable)));
    }

    @GetMapping("/lists/{name}/")
    public ResponseEntity<ApiSuccess> getListByName(@PathVariable String name)  {
        return ResponseEntity.ok(new ApiSuccess("List loaded successfully", listsService.findByName(name)));
    }

    @PutMapping("/lists/{id}")
    public ResponseEntity<ApiSuccess> updateList(@PathVariable String id,
                                                    @RequestBody MicrosoftList list) {
        listsService.update(id, list);
        return ResponseEntity.ok(new ApiSuccess("List updated successfully", true));
    }


    @DeleteMapping("/lists")
    public ResponseEntity<ApiSuccess> deleteList(@RequestBody String id) {
        listsService.delete(id);
        return ResponseEntity.ok(new ApiSuccess("List deleted successfully", true));
    }


    @GetMapping("/templates")
    public ResponseEntity<ApiSuccess> getAllTemplates(@PageableDefault() Pageable pageable) {

        return ResponseEntity.ok(new ApiSuccess("Templates load successfully",listsService.findAllTemplate(pageable)));
    }

    @GetMapping("/templates/{id}")
    public ResponseEntity<ApiSuccess> getTemplateById(@PathVariable String id) {

        return ResponseEntity.ok(new ApiSuccess("Template loaded successfully", listsService.getTemplateResponseById(id)));
    }

    @PostMapping("/templates")
    public ResponseEntity<ApiSuccess> saveTemplate(@RequestBody TemplateRequest templateReq) {

        List<Column> columnList = listsService.findAllColsOfList(templateReq.getListId());
        Template res = listsService.saveTemplate(templateReq.getDisplayName(), columnList);
        return ResponseEntity.ok(new ApiSuccess("Template saved successfully", res));
    }



    @DeleteMapping("/templates")
    public ResponseEntity<ApiSuccess> deleteTemplate(@RequestBody String id) {

        return ResponseEntity.ok(new ApiSuccess("Template deleted successfully", listsService.deleteTemplateById(id)));
    }


}
