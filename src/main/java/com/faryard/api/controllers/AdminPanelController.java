package com.faryard.api.controllers;

import com.faryard.api.facades.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPanelController {


    @GetMapping("/admin/login")
    public String adminLoginView(){
        return "login.html";
    }

    @GetMapping("/admin/panel")
    public String panel(){
        return "panel.html";
    }
}
