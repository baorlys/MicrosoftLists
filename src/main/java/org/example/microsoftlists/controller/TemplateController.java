package org.example.microsoftlists.controller;

import org.example.microsoftlists.model.Column;
import org.example.microsoftlists.model.Template;
import org.example.microsoftlists.service.MicrosoftListService;
import org.example.microsoftlists.service.TemplateService;
import org.example.microsoftlists.view.ApiSuccess;
import org.example.microsoftlists.view.dto.request.TemplateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/template")
public class TemplateController {
    @Autowired
    TemplateService templateService;
    @Autowired
    MicrosoftListService listsService;

    @PostMapping("/save")
    public ResponseEntity<ApiSuccess> saveTemplate(@RequestBody TemplateRequest templateReq) {
        List<Column> columnList = listsService.findAllColsOfList(templateReq.getListId());
        Template res = templateService.save(templateReq.getDisplayName(), columnList);
        return ResponseEntity.ok(new ApiSuccess("Template saved successfully", res));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiSuccess> deleteTemplate(@PathVariable String id) {
        return ResponseEntity.ok(new ApiSuccess("Template deleted successfully", templateService.delete(id)));
    }

}
