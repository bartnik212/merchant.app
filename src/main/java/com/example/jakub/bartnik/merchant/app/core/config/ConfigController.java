package com.example.jakub.bartnik.merchant.app.core.config;

import com.example.jakub.bartnik.merchant.app.module.enums.goods.*;
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
        model.addAttribute("cityActionSelected", playerService.getCityActionSelected());

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

        playerService.setHealthPoints(100);
        playerService.setCoins(25);
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

        return "redirect:/city_actions";
    }

    @GetMapping("/city_actions")
    public String showCityActions(Model model) {

        CityAction[] cityActionsToShow = {CityAction.MEET_WITH_GOOD_MERCHANT,
                CityAction.CHANGE_THE_CITY,
                CityAction.GO_TO_LOCAL_COMPANY,
                CityAction.GO_ON_VACATION,
                CityAction.GO_TO_WEAPON_STORE,
                CityAction.CHOOSE_WEAPON_TO_FIGHT,
                CityAction.RANDOM_ACTION};


        model.addAttribute("message5", applicationProperties.getMessage5());
        model.addAttribute("cityActionsToShow", cityActionsToShow);
        model.addAttribute("cityActionForm", new CityActionForm());

        return "city_actions";
    }

    @PostMapping("/city_actions")
    public String postCityAction(CityActionForm cityActionForm) {

        playerService.setCityActionSelected(cityActionForm.getActionSelected());
        log.info("city action selected: " + playerService.getCityActionSelected());

        return "redirect:/game2";
    }

    @GetMapping("/meet_merchant")
    public String meetMerchant(Model model) {

        Good goodType = playerService.getCurrentlyVisitingMerchantGood();

        model.addAttribute("answerForm", new AnswerForm());
        model.addAttribute("allAnswers", Answer.values());


        if (goodType == null) {
            return "redirect:/no_good_merchant";

        } else {

            switch (goodType) {
                case WOOD:
                    model.addAttribute("merchantDialog", applicationProperties.getWoodMerchantDialog());
                    break;

                case IRON:
                    model.addAttribute("merchantDialog", applicationProperties.getIronMerchantDialog());
                    break;

                case COPPER:
                    model.addAttribute("merchantDialog", applicationProperties.getCopperMerchantDialog());
                    break;

            }
        }

        return "meet_merchant_test";

    }

    @PostMapping("/meet_merchant")
    public String postMeetMerchant(AnswerForm answerForm) {

        return answerForm.getAnswerSelected() == Answer.YES ? "redirect:/positive_answer" :
                "redirect:/negative_answer";

    }

    @GetMapping("/no_good_merchant")
    public String noGoodMerchant(Model model) {

        model.addAttribute("noGoodDialog", applicationProperties.getNoGoodDialog());

        return "no_good_merchant";

    }

    @GetMapping("/positive_answer")
    public String positiveAnswer(Model model) {

        PositiveAnswerAction positiveAnswerAction = playerService.getPositiveAnswer();

        switch (positiveAnswerAction) {

            case GOOD_MERCHANT_GDANSK:
                playerService.getListOfGoods().remove(Good.WOOD);
                playerService.setCoins(playerService.getCoins() + 20);

                model.addAttribute("positiveAnswer", applicationProperties.getWoodMerchantPositiveAnswer());

            case GOOD_MERCHANT_WARSAW:
                playerService.getListOfGoods().remove(Good.IRON);
                playerService.setCoins(playerService.getCoins() + 20);

                model.addAttribute("positiveAnswer", applicationProperties.getIronMerchantPositiveAnswer());

            case GOOD_MERCHANT_ZAKOPANE:
                playerService.getListOfGoods().remove(Good.COPPER);
                playerService.setCoins(playerService.getCoins() + 20);

                model.addAttribute("positiveAnswer", applicationProperties.getCopperMerchantPositiveAnswer());

        }

        playerService.setCityActionSelected(CityAction.SHOW_CITY_ACTIONS);
        return "positive_answer";

    }

    @GetMapping("/negative_answer")
    public String negativeAnswer(Model model) {

        if (playerService.getCitySelected() == City.GDANSK &&
                playerService.getCityActionSelected() == CityAction.MEET_WITH_GOOD_MERCHANT) {

            model.addAttribute("negativeAnswer", applicationProperties.getMerchantNegativeAnswer());

        }

        return "negative_answer";

    }


    @GetMapping("/go_on_vacation")
    public String goOnVacation(Model model) {

        VacationPlace vacationPlace = playerService.getCurrentlyVisitingVacationPlace();

        switch (vacationPlace) {

            case MOTLAWA:
                model.addAttribute("dialog", applicationProperties.getMotlawaDialog());

            case VISTULA:
                model.addAttribute("dialog", applicationProperties.getVistulaDialog());

            case GUBALOWKA:
                model.addAttribute("dialog", applicationProperties.getGubalowkaDialog());
        }

        playerService.setHealthPoints(100);

        return "go_on_vacation";
    }

    @GetMapping("/random_action")
    public String getRandomAction(Model model) {

        return "random_action";
    }

    @GetMapping("/go_to_local_company")
    public String goToLocalCompany(Model model) {

        City localCompany = playerService.getCurrentlyVisitingLocalCompany();

        switch (localCompany) {

            case GDANSK:
                model.addAttribute("localCompanyDialog", applicationProperties.getCopperSmelterDialog());
            case WARSAW:
                model.addAttribute("localCompanyDialog", applicationProperties.getIronWorksDialog());
            case ZAKOPANE:
                model.addAttribute("localCompanyDialog", applicationProperties.getCopperSmelterDialog());
        }

        return "go_to_local_company";
    }

    @GetMapping("/go_to_weapon_store")
    public String goToWeaponStore(Model model) {

        //trzeba zrobić to samo co w meet_merchant_test, bo również są 3 możliwości

        model.addAttribute("weaponStoreDialog", applicationProperties.getWeaponStoreDialog());
        model.addAttribute("allWeapons", Weapon.values());
        model.addAttribute("weaponOwnedForm", new WeaponOwnedForm());

        return "go_to_weapon_store";
    }


    @GetMapping("/choose_weapon_to_fight")
    public String chooseWeaponToFight(Model model) {

        // trzeba zrobić podobnie, jak we wcześniejszych przypadkach -
        // i zaimplementować kolejnego switcha w "/select_weapon"
        // żeby nie tworzyć choose_weapon_to_fight

        model.addAttribute("message3", applicationProperties.getMessage3());
        model.addAttribute("ownedWeapons", playerService.getListOfWeapons());
        model.addAttribute("weaponOwnedForm", new WeaponOwnedForm());

        return "choose_weapon_to_fight";
    }

    @PostMapping("/choose_weapon_to_fight")
    public String postChooseWeaponTofight(WeaponOwnedForm weaponOwnedForm) {

        playerService.setWeaponSelected(weaponOwnedForm.getOwnedWeapons());
        log.info("weapon selected: " + playerService.getWeaponSelected());

        return "redirect:/city_actions";
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

        } else if (gameState == GameState.CITY_SELECTED) {
            playerService.setGameState(null);
            return "redirect:/city_actions";

        }

        return "redirect:/game2";
        //wywolaj bezposrednio metode
        //zdjac endpointa z game2
        //gamestate zmienic game initialization state

    }

    @GetMapping("/game2")
    public String game2() {

        CityAction cityActionSelected = playerService.getCityActionSelected();

        if (cityActionSelected == CityAction.CHANGE_THE_CITY) {
            return "redirect:/choose_city";

        } else if (cityActionSelected == CityAction.MEET_WITH_GOOD_MERCHANT) {
            return "redirect:/meet_merchant";

        } else if (cityActionSelected == CityAction.GO_ON_VACATION) {
            return "redirect:/go_on_vacation";

        } else if (cityActionSelected == CityAction.RANDOM_ACTION) {
            return "redirect:/random_action";

        } else if (cityActionSelected == CityAction.GO_TO_LOCAL_COMPANY) {
            return "redirect:/go_to_local_company";

        } else if (cityActionSelected == CityAction.GO_TO_WEAPON_STORE) {
            return "redirect:/go_to_weapon_store";

        } else if (cityActionSelected == CityAction.CHOOSE_WEAPON_TO_FIGHT) {
            return "redirect:/choose_weapon_to_fight";

        } else if (cityActionSelected == CityAction.SHOW_CITY_ACTIONS) {
            return "redirect:/city_actions";
        }

        return "/game2";
    }

}

