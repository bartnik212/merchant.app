package com.example.jakub.bartnik.merchant.app.core.config;

import com.example.jakub.bartnik.merchant.app.module.goods.enums.City;
import com.example.jakub.bartnik.merchant.app.module.goods.enums.GameState;
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
    public String getNavBar() {
        return "part_navigator";
    }

    @GetMapping("/rules")
    public String getRules() {
        return "rules";
    }

    @GetMapping("/user_form")
    public String getUserForm(Model model) {
        playerService.setGameState(GameState.ENTER_NAME);

//        model.addAttribute("player", playerService);
        model.addAttribute("nameForm", new NameForm());
        model.addAttribute("username", playerService.getName());

        return "user_form";
    }

    @PostMapping("/user_form")
    public String postUserForm(String name) {
        playerService.setName(name);
        log.info("name: " + playerService.getName());

        return "redirect:/choose_first_good";
    }

    @GetMapping("/player_status")
    public String getPlayerStatus(Model model) {

        model.addAttribute("listOfGoods", playerService.getListOfGoods());
        model.addAttribute("listOfWeapons", playerService.getListOfWeapons());
        model.addAttribute("weaponSelected", playerService.getWeaponSelected());
        model.addAttribute("coins", playerService.getCoins());
        model.addAttribute("healthPoints", playerService.getHealthPoints());
        model.addAttribute("citySelected", playerService.getCitySelected());

        return "player_status";
    }


    @GetMapping("/choose_first_good")
    public String chooseFirstGood(Model model) {

        playerService.setGameState(GameState.CHOOSE_FIRST_GOOD);

        model.addAttribute("message1", new MessageDto(applicationProperties.getMessage1()));
        model.addAttribute("allGoods", Good.values());
        model.addAttribute("goodOwnedForm", new GoodOwnedForm());

        return "choose_first_good";
    }


    @PostMapping("/choose_first_good")
    public String postFirstGood(GoodOwnedForm goodOwnedForm) {
        playerService.setHealthPoints(100);
        playerService.setCoins(25);

        playerService.saveGood(goodOwnedForm.getOwnedGoods());

        return "redirect:/choose_first_weapon";
    }

    @GetMapping("/choose_first_weapon")
    public String chooseFirstWeapon(Model model) {

        model.addAttribute("message2", new MessageDto(applicationProperties.getMessage2()));
        model.addAttribute("allWeapons", Weapon.values());
        model.addAttribute("weaponOwnedForm", new WeaponOwnedForm());

        return "choose_first_weapon";
    }

    @PostMapping("/choose_first_weapon")
    public String postFirstWeapon(WeaponOwnedForm weaponOwnedForm) {

        playerService.saveWeapon(weaponOwnedForm.getOwnedWeapons());
        return "redirect:/select_weapon";
    }

    @GetMapping("/select_weapon")
    public String showWeaponsToSelect(Model model) {

        model.addAttribute("message3", new MessageDto(applicationProperties.getMessage3()));
        model.addAttribute("ownedWeapons", playerService.getListOfWeapons());
        model.addAttribute("weaponOwnedForm", new WeaponOwnedForm());

        return "select_weapon";

    }

    @PostMapping("/select_weapon")
    public String postSelectWeapon(WeaponOwnedForm weaponOwnedForm) {

        playerService.setWeaponSelected(weaponOwnedForm.getOwnedWeapons());
        log.info("weapon selected: " + playerService.getWeaponSelected());
        return "redirect:/choose_city";
    }

    @GetMapping("/choose_city")
    public String showCitiesToChoose(Model model) {

        model.addAttribute("message4", new MessageDto(applicationProperties.getMessage4()));
        model.addAttribute("allCities", City.values());
        model.addAttribute("cityChosenForm", new CityChosenForm());

        return "choose_city";
    }

    @PostMapping("/choose_city")
    public String postChosenCity(CityChosenForm cityChosenForm) {

        playerService.setCitySelected(cityChosenForm.getChosenCity());
        log.info("chosen city: " + playerService.getCitySelected());
        return "redirect:/nav";
    }


    @GetMapping("/game")
    public String game() {

        GameState gameState = playerService.getGameState();

        //wszystkie przesunac o jeden

        if (gameState == GameState.ENTER_NAME) {
            return "redirect:/choose_first_good";

        } else if (gameState == GameState.CHOOSE_FIRST_GOOD) {
            return "redirect:/choose_first_weapon";

        } else if (gameState == GameState.CHOOSE_FIRST_WEAPON) {
            return "redirect:/select_weapon";

        } else if (gameState == GameState.SELECT_WEAPON) {
            return "redirect:/choose_city";
        }

        return "/user_form";

    }
}
