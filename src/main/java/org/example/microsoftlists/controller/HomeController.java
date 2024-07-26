package org.example.microsoftlists.controller;

import org.example.microsoftlists.dto.request.ColumnDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "Api is working!";
    }


}
