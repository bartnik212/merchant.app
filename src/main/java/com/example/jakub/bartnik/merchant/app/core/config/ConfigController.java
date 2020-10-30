package com.example.jakub.bartnik.merchant.app.core.config;

import com.example.jakub.bartnik.merchant.app.module.goods.dto.Goods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Controller
public class ConfigController {

    private static final Player player = new Player();

    @Autowired
    private ApplicationProperties applicationProperties;


//    , @RequestParam("chosenGood") Goods good
    @GetMapping("/initialmessage")
    public String getConfig(Model model){

        Goods[] allGoods = Goods.values();
        model.addAttribute("message1", new MessageDto(applicationProperties.getMessage1()));
        model.addAttribute("allGoods", allGoods);

        model.addAttribute("player", new Player());

        log.info("chosen good: " + player.getListOfGoods());


        return "initial_message";
    }



}
