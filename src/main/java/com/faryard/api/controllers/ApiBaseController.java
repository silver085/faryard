package com.faryard.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api/v1")
public class ApiBaseController {
    @Autowired
    MessageSource msgSource;

    @GetMapping("/test")
    public String testMethod(){

        return msgSource.getMessage("api.testmessage", null, Locale.forLanguageTag(LocaleContextHolder.getLocale().getLanguage()));
    }

}
