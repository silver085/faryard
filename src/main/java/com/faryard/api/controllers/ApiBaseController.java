package com.faryard.api.controllers;

import com.faryard.api.DTO.UserProfileDTO;
import com.faryard.api.facades.UserFacade;
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

    @Autowired
    UserFacade userFacade;

    @GetMapping("/test")
    public String testMethod(){

        return msgSource.getMessage("api.testmessage", null, Locale.forLanguageTag(LocaleContextHolder.getLocale().getLanguage()));
    }

    @GetMapping("/isvalidauth")
    public String isValidAuth(){
        return "OK";
    }

    @GetMapping("/userprofile")
    public UserProfileDTO getUserProfile(){

        return userFacade.getLoggedUserProfile();
    }

}
