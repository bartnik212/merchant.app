package com.example.jakub.bartnik.merchant.app.core.config;

import com.example.jakub.bartnik.merchant.app.module.enums.*;
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

        playerService.setGameInitializationState(GameInitializationState.CHOOSE_FIRST_GOOD);

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

        playerService.setGameInitializationState(GameInitializationState.CHOOSE_FIRST_WEAPON);

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

        playerService.setGameInitializationState(GameInitializationState.SELECT_WEAPON);
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

        boolean isInitializingSelectWeapon = playerService.checkIfItsInitializingSelectWeapon();
        String redirect = "";

        if (isInitializingSelectWeapon) {
            playerService.setGameInitializationState(GameInitializationState.CHOOSE_CITY);
            redirect = "redirect:/choose_city";
        } else {
            playerService.setCityActionSelected(CityAction.SHOW_CITY_ACTIONS);
            redirect = "redirect:/city_actions";
        }

        log.info("weapon selected: " + playerService.getWeaponSelected());
        playerService.setWeaponSelected(weaponOwnedForm.getOwnedWeapons());

        return redirect;
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

        playerService.setGameInitializationState(GameInitializationState.CITY_SELECTED);
        playerService.setCitySelected(cityChosenForm.getChosenCity());
        playerService.setCoins(playerService.getCoins() - 5);
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

        model.addAttribute("answerForm", new MerchantAnswerForm());
        model.addAttribute("allAnswers", MerchantAnswer.values());


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
    public String postMeetMerchant(MerchantAnswerForm merchantAnswerForm) {

        return merchantAnswerForm.getMerchantAnswerSelected() == MerchantAnswer.YES ? "redirect:/positive_answer" :
                "redirect:/negative_answer";

    }

    @GetMapping("/no_good_merchant")
    public String noGoodMerchant(Model model) {

        model.addAttribute("noGoodDialog", applicationProperties.getNoGoodDialog());
        playerService.setCityActionSelected(CityAction.SHOW_CITY_ACTIONS);
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
                break;

            case GOOD_MERCHANT_WARSAW:
                playerService.getListOfGoods().remove(Good.IRON);
                playerService.setCoins(playerService.getCoins() + 20);

                model.addAttribute("positiveAnswer", applicationProperties.getIronMerchantPositiveAnswer());
                break;

            case GOOD_MERCHANT_ZAKOPANE:
                playerService.getListOfGoods().remove(Good.COPPER);
                playerService.setCoins(playerService.getCoins() + 20);

                model.addAttribute("positiveAnswer", applicationProperties.getCopperMerchantPositiveAnswer());
                break;

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

        playerService.setCityActionSelected(CityAction.SHOW_CITY_ACTIONS);
        return "negative_answer";

    }


    @GetMapping("/go_on_vacation")
    public String goOnVacation(Model model) {

        VacationPlace vacationPlace = playerService.getCurrentlyVisitingVacationPlace();

        switch (vacationPlace) {

            case MOTLAWA:
                model.addAttribute("dialog", applicationProperties.getMotlawaDialog());
                break;

            case VISTULA:
                model.addAttribute("dialog", applicationProperties.getVistulaDialog());
                break;

            case GUBALOWKA:
                model.addAttribute("dialog", applicationProperties.getGubalowkaDialog());
                break;
        }

        playerService.setCoins(playerService.getCoins() - 5);
        playerService.setHealthPoints(100);
        playerService.setCityActionSelected(CityAction.SHOW_CITY_ACTIONS);

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
                break;

            case WARSAW:
                model.addAttribute("localCompanyDialog", applicationProperties.getIronWorksDialog());
                break;

            case ZAKOPANE:
                model.addAttribute("localCompanyDialog", applicationProperties.getSawmillDialog());
                break;
        }

        playerService.setCityActionSelected(CityAction.GO_TO_LOCAL_COMPANY);

        return "go_to_local_company";
    }

    @GetMapping("/go_to_local_company2")
    public String goToLocalCompany2(Model model) {

        model.addAttribute("battleAnswerForm", new BattleAnswerForm());
        model.addAttribute("allAnswers", BattleAnswer.values());
        Enemy enemy = playerService.fightWithWorkerOfLocalCompany();

        switch (enemy) {
            case FIREARMER:
                model.addAttribute("workerLocalCompanyDialog", applicationProperties.getWorkerCopperSmelterDialog());
                break;

        }


        return "go_to_local_company2";
    }

    @PostMapping("/go_to_local_company2")
    public String postGoToLocalCompany2(BattleAnswerForm battleAnswerForm) {

        return battleAnswerForm.getBattleAnswerSelected() == BattleAnswer.FIGHT ? "redirect:/go_to_local_company3" :
                "redirect:/leave_local_company";

    }

    @GetMapping("/go_to_local_company3")
    public String goToLocalCompany3(Model model) {
        Enemy enemy = playerService.fightWithWorkerOfLocalCompany();

        switch (enemy) {

            case FIREARMER:
                model.addAttribute("workerLocalCompanyDialog2", applicationProperties.getWorkerCopperSmelterDialog2());
                break;

        }

        return "go_to_local_company3";
    }

    @GetMapping("/go_to_local_company4")
    public String goToLocalCompany4(Model model) {
        Enemy enemy = playerService.fightWithWorkerOfLocalCompany();
        FightResult fightResult = playerService.paperScissorsRock(enemy);

        switch (fightResult) {

            case WIN:
                model.addAttribute("winDialog", applicationProperties.getWinDialog());
                playerService.saveGood(Good.COPPER);
        }

        return "/go_to_local_company4";
    }

    @GetMapping("leave_local_company")
    public String leaveLocalCompany(Model model) {

        model.addAttribute("leaveLocalCompany", applicationProperties.getLeaveLocalCompany());
        playerService.setCityActionSelected(CityAction.SHOW_CITY_ACTIONS);

        return "leave_local_company";
    }

    @GetMapping("/go_to_weapon_store")
    public String goToWeaponStore(Model model) {

        model.addAttribute("weaponStoreDialog", applicationProperties.getWeaponStoreDialog());
        model.addAttribute("allWeapons", Weapon.values());
        model.addAttribute("weaponOwnedForm", new WeaponOwnedForm());

        playerService.setCityActionSelected(CityAction.GO_TO_WEAPON_STORE);

        return "go_to_weapon_store";
    }

    @PostMapping("/go_to_weapon_store")
    public String postWeaponStore(WeaponOwnedForm weaponOwnedForm) {

        if (playerService.getListOfWeapons().contains(weaponOwnedForm.getOwnedWeapons())) {
            return "redirect:/duplicated_weapon";
        } else {
            playerService.saveWeapon(weaponOwnedForm.getOwnedWeapons());
        }

        return "redirect:/weapon_bought";
    }

    @GetMapping("/duplicated_weapon")
    public String duplicatedWeapon(Model model) {
        model.addAttribute("duplicatedWeapon", applicationProperties.getDuplicatedWeapon());
        playerService.setCityActionSelected(CityAction.SHOW_CITY_ACTIONS);

        return "duplicated_weapon";
    }

    @GetMapping("weapon_bought")
    public String weaponBought(Model model) {
        model.addAttribute("weaponBought", applicationProperties.getWeaponBought());
        playerService.setCoins(playerService.getCoins() - 10);
        playerService.setCityActionSelected(CityAction.SHOW_CITY_ACTIONS);

        return "weapon_bought";
    }


    @GetMapping("/game")
    public String game() {

        GameInitializationState gameInitializationState = playerService.getGameInitializationState();

        if (gameInitializationState == GameInitializationState.ENTER_NAME) {
            return "redirect:/user_form";

        } else if (gameInitializationState == GameInitializationState.CHOOSE_FIRST_GOOD) {
            return "redirect:/choose_first_good";

        } else if (gameInitializationState == GameInitializationState.CHOOSE_FIRST_WEAPON) {
            return "redirect:/choose_first_weapon";

        } else if (gameInitializationState == GameInitializationState.SELECT_WEAPON) {
            return "redirect:/select_weapon";

        } else if (gameInitializationState == GameInitializationState.CHOOSE_CITY) {
            return "redirect:/choose_city";

        } else if (gameInitializationState == GameInitializationState.CITY_SELECTED) {
            playerService.setGameInitializationState(null);
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
            return "redirect:/select_weapon";

        } else if (cityActionSelected == CityAction.SHOW_CITY_ACTIONS) {
            return "redirect:/city_actions";
        }

        return "/game2";
    }

}

