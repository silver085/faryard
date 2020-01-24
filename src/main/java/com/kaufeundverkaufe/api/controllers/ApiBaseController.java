package com.kaufeundverkaufe.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ApiBaseController {

    @GetMapping("/test")
    public String testMethod(){
        return "OK!";
    }
}
