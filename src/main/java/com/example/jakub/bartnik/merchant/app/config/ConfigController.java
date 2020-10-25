package com.example.jakub.bartnik.merchant.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class ConfigController {

    @Autowired
    private ApplicationProperties applicationProperties;

    @GetMapping("/initialmessage")
    public String getConfig(Model model){

        model.addAttribute("initialmessage", new MessageDto(applicationProperties.getMessage1()));
        return "initial_message";

//        return List.of(
//                new MessageDto(applicationProperties.getMessage1())
//        );
    }
}
