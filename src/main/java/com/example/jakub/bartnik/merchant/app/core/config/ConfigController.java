package com.example.jakub.bartnik.merchant.app.core.config;

import com.example.jakub.bartnik.merchant.app.module.goods.enums.Good;
import com.example.jakub.bartnik.merchant.app.module.goods.enums.Weapon;
import com.example.jakub.bartnik.merchant.app.module.services.PlayerService;
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

    @GetMapping("/nav")
    public String getNavBar(){
        return "part_navigator";
    }

    @GetMapping("/rules")
    public String getRules(){
        return "rules";
    }

    @GetMapping("/username")
    public String getUserForm(){
        return "user_form";
    }


    @GetMapping("/choose_first_good")
    public String chooseFirstGood(Model model){

        model.addAttribute("message1", new MessageDto(applicationProperties.getMessage1()));
        model.addAttribute("allGoods", Good.values());
        model.addAttribute("goodOwnedForm", new GoodOwnedForm());

        return "choose_first_good";
    }
    

    @PostMapping("/choose_first_good")
    public String postFirstGood(GoodOwnedForm goodOwnedForm) {

        playerService.saveGood(goodOwnedForm.getOwnedGoods());

        return "redirect:/choose_first_weapon";
    }

    @GetMapping("/choose_first_weapon")
    public String chooseFirstWeapon (Model model){

        model.addAttribute("message2", new MessageDto(applicationProperties.getMessage2()));
        model.addAttribute("allWeapons", Weapon.values());
        model.addAttribute("weaponOwnedForm", new WeaponOwnedForm());

        return "choose_first_weapon";
    }

    @PostMapping("/choose_first_weapon")
    public String postFirstWeapon (WeaponOwnedForm weaponOwnedForm){

        playerService.saveWeapon(weaponOwnedForm.getOwnedWeapons());
        return "redirect:/nav";
    }


}
