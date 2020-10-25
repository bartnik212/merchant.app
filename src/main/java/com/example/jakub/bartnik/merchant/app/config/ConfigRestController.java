package com.example.jakub.bartnik.merchant.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConfigRestController {

    @Autowired
    private ApplicationProperties applicationProperties;

    @GetMapping("/")
    public List<MessageDto> getConfig(){
        return List.of(
                new MessageDto(applicationProperties.getMessage1())
        );
    }
}
