package com.example.jakub.bartnik.merchant.app.core.config;

import com.example.jakub.bartnik.merchant.app.module.enums.goods.City;
import com.example.jakub.bartnik.merchant.app.module.enums.goods.GameState;
import com.example.jakub.bartnik.merchant.app.module.enums.goods.Good;
import com.example.jakub.bartnik.merchant.app.module.enums.goods.Weapon;
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



    @GetMapping("/player_status")
    public String getPlayerStatus(Model model) {

        model.addAttribute("listOfGoods", playerService.getListOfGoods());
        model.addAttribute("listOfWeapons", playerService.getListOfWeapons());
        model.addAttribute("weaponSelected", playerService.getWeaponSelected());
        model.addAttribute("coins", playerService.getCoins());
        model.addAttribute("healthPoints", playerService.getHealthPoints());
        model.addAttribute("citySelected", playerService.getCitySelected());
        model.addAttribute("palyerName", playerService.getName());

        return "player_status";
    }

    @GetMapping("/user_form")
    public String getUserForm(Model model) {

        playerService.setGameState(GameState.CHOOSE_FIRST_GOOD);

        model.addAttribute("nameForm", new NameForm());
        model.addAttribute("username", playerService.getName());

        return "user_form";
    }

    @PostMapping("/user_form")
    public String postUserForm(NameForm nameForm) {

        playerService.setName(nameForm.getName());
        log.info("name: " + playerService.getName());

        return "redirect:/choose_first_good";
    }


    @GetMapping("/choose_first_good")
    public String chooseFirstGood(Model model) {


        model.addAttribute("message1", applicationProperties.getMessage1());
        model.addAttribute("allGoods", Good.values());
        model.addAttribute("goodOwnedForm", new GoodOwnedForm());

        return "choose_first_good";
    }


    @PostMapping("/choose_first_good")
    public String postFirstGood(GoodOwnedForm goodOwnedForm) {

        playerService.setGameState(GameState.CHOOSE_FIRST_WEAPON);

        playerService.setHealthPoints(100);
        playerService.setCoins(25);

        playerService.saveGood(goodOwnedForm.getOwnedGoods());


        return "redirect:/choose_first_weapon";
    }

    @GetMapping("/choose_first_weapon")
    public String chooseFirstWeapon(Model model) {

        model.addAttribute("message2", applicationProperties.getMessage2());
        model.addAttribute("allWeapons", Weapon.values());
        model.addAttribute("weaponOwnedForm", new WeaponOwnedForm());

        return "choose_first_weapon";
    }

    @PostMapping("/choose_first_weapon")
    public String postFirstWeapon(WeaponOwnedForm weaponOwnedForm) {

        playerService.setGameState(GameState.SELECT_WEAPON);
        playerService.saveWeapon(weaponOwnedForm.getOwnedWeapons());
        return "redirect:/select_weapon";
    }

    @GetMapping("/select_weapon")
    public String showWeaponsToSelect(Model model) {

        model.addAttribute("message3", applicationProperties.getMessage3());
        model.addAttribute("ownedWeapons", playerService.getListOfWeapons());
        model.addAttribute("weaponOwnedForm", new WeaponOwnedForm());

        return "select_weapon";

    }

    @PostMapping("/select_weapon")
    public String postSelectWeapon(WeaponOwnedForm weaponOwnedForm) {

        playerService.setGameState(GameState.CHOOSE_CITY);
        playerService.setWeaponSelected(weaponOwnedForm.getOwnedWeapons());
        log.info("weapon selected: " + playerService.getWeaponSelected());

        return "redirect:/choose_city";
    }

    @GetMapping("/choose_city")
    public String showCitiesToChoose(Model model) {

        model.addAttribute("message4", applicationProperties.getMessage4());
        model.addAttribute("allCities", City.values());
        model.addAttribute("cityChosenForm", new CityChosenForm());

        return "choose_city";
    }

    @PostMapping("/choose_city")
    public String postChosenCity(CityChosenForm cityChosenForm) {

        playerService.setGameState(GameState.CITY_SELECTED);
        playerService.setCitySelected(cityChosenForm.getChosenCity());
        log.info("chosen city: " + playerService.getCitySelected());
        return "redirect:/city";
    }

    @GetMapping("/city")
    public String showCityActions(Model model){

        model.addAttribute("goodMerchant", applicationProperties.getGoodMerchantDialog());
        model.addAttribute("goOnVacation", applicationProperties.getGoOnVacationDialog());
        model.addAttribute("changeTheCity", applicationProperties.getChangeTheCityDialog());
        model.addAttribute("randomAction", applicationProperties.getRandomActionDialog());
        model.addAttribute("goToLocalCompany", applicationProperties.getGoToLocalCompanyDialog());
        model.addAttribute("goToWeaponStore", applicationProperties.getGoToWeaponStoreDialog());
        model.addAttribute("chooseWeaponToFight", applicationProperties.getChooseWeaponToFightDialog());

        model.addAttribute("cityActionForm", new CityActionForm());

//        model.addAttribute("player", playerService.meetWoodMerchant());

        return "/city";
    }

    @PostMapping("city")
    public String postCityAction(CityActionForm cityActionForm){

        playerService.setCityActionSelected(cityActionForm.getActionSelected());
        log.info("city action selected: " + playerService.getCityActionSelected());
        return "redirect:/nav";
    }

    @GetMapping("/meetWoodMerchant")
    public String meetWoodMerchant(Model model) {

        model.addAttribute("woodMerchantDialog", applicationProperties.getWoodMerchantDialog());

        if(playerService.getListOfGoods().contains(Good.WOOD)) {
            return "wood_owned";
        }

        return "";
    }

    @GetMapping("/game")
    public String game() {

        GameState gameState = playerService.getGameState();

        if (gameState == GameState.ENTER_NAME) {
            return "redirect:/user_form";

        } else if (gameState == GameState.CHOOSE_FIRST_GOOD) {
            return "redirect:/choose_first_good";

        } else if (gameState == GameState.CHOOSE_FIRST_WEAPON) {
            return "redirect:/choose_first_weapon";

        } else if (gameState == GameState.SELECT_WEAPON) {
            return "redirect:/select_weapon";

        } else if (gameState == GameState.CHOOSE_CITY) {
            return "redirect:/choose_city";

        } else if(gameState == GameState.CITY_SELECTED){
            return "redirect:/city";
        }

        return "/game";

    }

}
