package com.example.jakub.bartnik.merchant.app.core.config;

import com.example.jakub.bartnik.merchant.app.module.goods.enums.Good;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Slf4j
@Controller
public class ConfigController {

    @Autowired
    PlayerService playerService;

    @Autowired
    private ApplicationProperties applicationProperties;



    @GetMapping("/initialmessage")
    public String getInitial(Model model){
        Good[] allGoods = Good.values();

        model.addAttribute("allGoods", allGoods);
        model.addAttribute("initialGoodForm", new GoodInitialForm());
        model.addAttribute("message1", new MessageDto(applicationProperties.getMessage1()));

        return "initial_message";
    }
    

    @PostMapping("/initialmessage")
    public String getConfig(GoodInitialForm goodInitialForm) {

        log.info("chosen good: " + goodInitialForm.getGoodDto());
        playerService.saveGood(goodInitialForm.getGoodDto());

        return "redirect:/nav";
    }

}
