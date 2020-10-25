package com.example.jakub.bartnik.merchant.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("/nav")
    public String getNavBar(){
        return "part_navigator";
    }

    @GetMapping("/rules")
    public String getRules(){
        return "rules";
    }
}
