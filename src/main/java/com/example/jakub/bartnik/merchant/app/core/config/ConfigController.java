package com.example.jakub.bartnik.merchant.app.core.config;

import com.example.jakub.bartnik.merchant.app.core.reward.ChuckNorrisResponseMain;
import com.example.jakub.bartnik.merchant.app.module.enums.*;
import com.example.jakub.bartnik.merchant.app.module.services.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;


@Slf4j
@Controller
public class ConfigController {

    @Autowired
    PlayerService playerService;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private ChuckNorrisResponseMain chuckNorrisResponseMain;

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
        playerService.setGameInitializationState(GameInitializationState.ENTER_NAME);


        playerService.setHealthPoints(100);
        playerService.setCoins(25);
        playerService.setName(nameForm.getName());
        log.info("name: " + playerService.getName());

        return "redirect:/choose_first_good";
    }

    @GetMapping("/choose_first_good")
    public String chooseFirstGood(Model model) {

        playerService.setGameInitializationState(GameInitializationState.CHOOSE_FIRST_GOOD);

        model.addAttribute("message1", applicationProperties.getMessage1());
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
    public String chooseFirstWeapon(Model model) {

        playerService.setGameInitializationState(GameInitializationState.CHOOSE_FIRST_WEAPON);


        model.addAttribute("message2", applicationProperties.getMessage2());
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
        if (playerService.getGameInitializationState() != null)
            playerService.setGameInitializationState(GameInitializationState.SELECT_WEAPON);

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

        if (playerService.getCoins() < 5) {
            return "redirect:/not_enough_coins";

        } else {
            playerService.setGameInitializationState(GameInitializationState.CHOOSE_CITY);

            model.addAttribute("message4", applicationProperties.getMessage4());
            model.addAttribute("allCities", City.values());
            model.addAttribute("cityChosenForm", new CityChosenForm());

            return "choose_city";
        }
    }

    @PostMapping("/choose_city")
    public String postChosenCity(CityChosenForm cityChosenForm) {

        playerService.setCitySelected(cityChosenForm.getChosenCity());
        playerService.setCoins(playerService.getCoins() - 5);
        log.info("chosen city: " + playerService.getCitySelected());
        playerService.setGameInitializationState(null);

        return "redirect:/city_actions";
    }

    @GetMapping("/city_actions")
    public String showCityActions(Model model) {
        playerService.setCityActionSelected(CityAction.SHOW_CITY_ACTIONS);

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

    @GetMapping("not_enough_coins")
    public String noEnoughCoins(Model model) {
        model.addAttribute("noEnoughCoinsDialog", applicationProperties.getNotEnoughCoins());

        playerService.setCityActionSelected(CityAction.SHOW_CITY_ACTIONS);
        return "not_enough_coins";
    }

    @GetMapping("/meet_merchant")
    public String meetMerchant(Model model) {

        playerService.setCityActionSelected(CityAction.MEET_WITH_GOOD_MERCHANT);


        Good goodType = playerService.getCurrentlyVisitingMerchantGood();

        model.addAttribute("merchantAnswerForm", new MerchantAnswerForm());
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
        playerService.setCityActionSelected(CityAction.SHOW_CITY_ACTIONS);


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

        if (playerService.getCoins() < 5) {
            return "redirect:/not_enough_coins";

        } else {

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
        }

        playerService.setCoins(playerService.getCoins() - 5);
        playerService.setHealthPoints(100);
        playerService.setCityActionSelected(CityAction.SHOW_CITY_ACTIONS);

        return "go_on_vacation";
    }

    @GetMapping("/you_have_already_been_here")
    public String companyVisited(Model model) {
        model.addAttribute("companyVisitedDialog", applicationProperties.getYouHaveAlreadyBeenHere());

        return "you_have_already_been_here";
    }

    @GetMapping("/go_to_local_company")
    public String goToLocalCompany(Model model) {
        playerService.setCityActionSelected(CityAction.GO_TO_LOCAL_COMPANY);

        if (playerService.getListOfCities().contains(playerService.getCitySelected())) {
            playerService.setCityActionSelected(CityAction.SHOW_CITY_ACTIONS);
            return "redirect:/you_have_already_been_here";

        } else {

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

        }
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

            case TWOHANDEDSWORDER:
                model.addAttribute("workerLocalCompanyDialog", applicationProperties.getWorkerIronWorksDialog());
                break;

            case SWORDSHIELDER:
                model.addAttribute("workerLocalCompanyDialog", applicationProperties.getWorkerSawmillDialog());
                break;
        }

        playerService.setCityActionSelected(CityAction.GO_TO_LOCAL_COMPANY2);
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
                playerService.setEnemy(Enemy.FIREARMER);
                break;

            case TWOHANDEDSWORDER:
                model.addAttribute("workerLocalCompanyDialog2", applicationProperties.getWorkerIronWorksDialog2());
                playerService.setEnemy(Enemy.TWOHANDEDSWORDER);
                break;

            case SWORDSHIELDER:
                model.addAttribute("workerLocalCompanyDialog2", applicationProperties.getWorkerSawmillDialog2());
                playerService.setEnemy(Enemy.SWORDSHIELDER);

                break;

        }
        playerService.setCityActionSelected(CityAction.GO_TO_LOCAL_COMPANY3);
        return "go_to_local_company3";
    }

    @GetMapping("/go_to_local_company4")
    public String goToLocalCompany4(Model model) {
        playerService.setCityActionSelected(CityAction.SHOW_CITY_ACTIONS);

        BattleResult battleResult = playerService.paperScissorsRock(playerService.getEnemy());
        City citySelected = playerService.getCitySelected();

        switch (battleResult) {

            case WIN:

                if (citySelected == City.GDANSK) {
                    model.addAttribute("afterBattleDialog", applicationProperties.getWinDialogLocalCompanyCopper());
                    playerService.saveGood(Good.COPPER);

                } else if (citySelected == City.WARSAW) {
                    model.addAttribute("afterBattleDialog", applicationProperties.getWinDialogLocalCompanyIron());
                    playerService.saveGood(Good.IRON);

                } else if (citySelected == City.ZAKOPANE) {
                    model.addAttribute("afterBattleDialog", applicationProperties.getWinDialogLocalCompanyWood());
                    playerService.saveGood(Good.WOOD);
                }

                playerService.setEnemy(null);
                playerService.getListOfCities().add(playerService.getCitySelected());
                break;

            case LOSE:
                model.addAttribute("afterBattleDialog", applicationProperties.getLoseDialogLocalCompany());
                playerService.setHealthPoints(playerService.getHealthPoints() - 50);

                if (playerService.getHealthPoints() <= 0) {
                    playerService.setGameInitializationState(GameInitializationState.ENTER_NAME);
                    return "redirect:/health_points_below_0";
                }

                break;

            case DRAW:
                model.addAttribute("afterBattleDialog", applicationProperties.getDrawDialogLocalCompany());
                playerService.setHealthPoints(playerService.getHealthPoints() - 30);

                if (playerService.getHealthPoints() <= 0) {
                    playerService.setGameInitializationState(GameInitializationState.ENTER_NAME);
                    return "redirect:/health_points_below_0";
                }

                break;
        }

        playerService.setCityActionSelected(CityAction.SHOW_CITY_ACTIONS);
        return "/go_to_local_company4";
    }

    @GetMapping("leave_local_company")
    public String leaveLocalCompany(Model model) {
        playerService.setCityActionSelected(CityAction.SHOW_CITY_ACTIONS);

        model.addAttribute("leaveLocalCompany", applicationProperties.getLeaveLocalCompany());

        return "leave_local_company";
    }

    @GetMapping("/go_to_weapon_store")
    public String goToWeaponStore(Model model) {
        playerService.setCityActionSelected(CityAction.GO_TO_WEAPON_STORE);

        model.addAttribute("weaponStoreDialog", applicationProperties.getWeaponStoreDialog());
        model.addAttribute("allWeapons", Weapon.values());
        model.addAttribute("weaponOwnedForm", new WeaponOwnedForm());

        return "go_to_weapon_store";
    }

    @PostMapping("/go_to_weapon_store")
    public String postWeaponStore(WeaponOwnedForm weaponOwnedForm) {

        if (playerService.getListOfWeapons().contains(weaponOwnedForm.getOwnedWeapons())) {
            return "redirect:/duplicated_weapon";

        } else if (playerService.getCoins() < 10) {
            return "redirect:/not_enough_coins";

        } else {
            playerService.saveWeapon(weaponOwnedForm.getOwnedWeapons());
        }

        return "redirect:/weapon_bought";
    }

    @GetMapping("/duplicated_weapon")
    public String duplicatedWeapon(Model model) {
        playerService.setCityActionSelected(CityAction.SHOW_CITY_ACTIONS);
        model.addAttribute("duplicatedWeapon", applicationProperties.getDuplicatedWeapon());

        return "duplicated_weapon";
    }

    @GetMapping("weapon_bought")
    public String weaponBought(Model model) {
        playerService.setCityActionSelected(CityAction.SHOW_CITY_ACTIONS);

        model.addAttribute("weaponBought", applicationProperties.getWeaponBought());
        playerService.setCoins(playerService.getCoins() - 10);

        return "weapon_bought";
    }

    @GetMapping("random_action")
    public String getRandomAction(Model model) {
        RandomAction randomAction = playerService.getRandomAction();

        if (randomAction == null) {
            return "redirect:/random_action2";

        } else {

            switch (randomAction) {

                case FIND_COINS:
                    model.addAttribute("randomActionDialog", applicationProperties.getFindCoins());
                    playerService.setCoins(playerService.getCoins() + 15);
                    break;

                case FIND_WOOD:
                    model.addAttribute("randomActionDialog", applicationProperties.getFindWood());
                    playerService.getListOfGoods().add(Good.WOOD);
                    break;

                case FIND_COPPER:
                    model.addAttribute("randomActionDialog", applicationProperties.getFindCopper());
                    playerService.getListOfGoods().add(Good.COPPER);
                    break;

                case FIND_IRON:
                    model.addAttribute("randomActionDialog", applicationProperties.getFindIron());
                    playerService.getListOfGoods().add(Good.IRON);
                    break;

                case NEGATIVE_RANDOM_ACTION:
                    return "redirect:/random_action2";

            }

        }
        playerService.setCityActionSelected(CityAction.SHOW_CITY_ACTIONS);
        return "random_action";
    }

    @GetMapping("/random_action2")
    public String getRandomAction2(Model model) {

        model.addAttribute("robberDialog", applicationProperties.getRobberDialog());
        model.addAttribute("battleAnswerForm", new BattleAnswerForm());
        model.addAttribute("battleAnswers", BattleAnswer.values());

        playerService.setCityActionSelected(CityAction.RANDOM_ACTION2);

        return "random_action2";
    }

    @PostMapping("/random_action2")
    public String postRandomAction2(BattleAnswerForm battleAnswerForm) {

        return battleAnswerForm.getBattleAnswerSelected() == BattleAnswer.FIGHT ? "redirect:/random_action3" :
                "redirect:/run_away_from_robber";
    }


    @GetMapping("/random_action3")
    public String getRandomAction3(Model model) {

        Enemy enemy = playerService.getNegativeRandomAction();

        if (enemy == null) {
            model.addAttribute("robberDialog", applicationProperties.getRobberSwordShield());
            playerService.setEnemy(Enemy.SWORDSHIELDER);

        } else {

            switch (enemy) {

                case SWORDSHIELDER:
                    model.addAttribute("robberDialog", applicationProperties.getRobberSwordShield());
                    playerService.setEnemy(Enemy.SWORDSHIELDER);
                    break;

                case TWOHANDEDSWORDER:
                    model.addAttribute("robberDialog", applicationProperties.getRobberTwoHandedSword());
                    playerService.setEnemy(Enemy.TWOHANDEDSWORDER);
                    break;

                case FIREARMER:
                    model.addAttribute("robberDialog", applicationProperties.getRobberFireArm());
                    playerService.setEnemy(Enemy.FIREARMER);
                    break;
            }

        }
        playerService.setCityActionSelected(CityAction.RANDOM_ACTION3);
        return "random_action3";
    }


    @GetMapping("/random_action4")
    public String getRandomAction4(Model model) {

        BattleResult battleResult = playerService.paperScissorsRock(playerService.getEnemy());

        switch (battleResult) {

            case WIN:
                model.addAttribute("afterBattleRobberDialog", applicationProperties.getWinRobberDialog());
                playerService.setCoins(playerService.getCoins() + 15);
                break;

            case LOSE:
                model.addAttribute("afterBattleRobberDialog", applicationProperties.getLoseRobberDialog());
                playerService.setHealthPoints(playerService.getHealthPoints() - 40);
                playerService.setCoins(0);

                if (playerService.getHealthPoints() <= 0) {
                    playerService.setGameInitializationState(GameInitializationState.ENTER_NAME);
                    return "redirect:/health_points_below_0";
                }

                break;

            case DRAW:
                model.addAttribute("afterBattleRobberDialog", applicationProperties.getDrawRobberDialog());
                playerService.setHealthPoints(playerService.getHealthPoints() - 20);
                break;

        }
        playerService.setEnemy(null);
        playerService.setCityActionSelected(CityAction.SHOW_CITY_ACTIONS);
        return "random_action4";
    }

    @GetMapping("/run_away_from_robber")
    public String getRunAwayFromRobber(Model model) {

        model.addAttribute("runAwayFromRobber", applicationProperties.getRunAwayFromRobber());
        playerService.setCoins(0);

        playerService.setCityActionSelected(CityAction.SHOW_CITY_ACTIONS);
        return "run_away_from_robber";
    }

    @GetMapping("/health_points_below_0")
    public String getLoseTheGameBecauseOfHealthPoints(Model model) {
        model.addAttribute("healthPointsBelow0", applicationProperties.getHealthPointsBelow0());

        return "health_points_below_0";
    }


    @GetMapping("/you_won_the_game")
    public String getYouWonTheGame(Model model) {
        model.addAttribute("winTheGameDialog", applicationProperties.getYouWonTheGame());

        return "you_won_the_game";
    }

    @GetMapping("chuck_norris")
    public String getChuckNorrisJoke(Model model) throws IOException, InterruptedException {

        model.addAttribute("chuckNorrisJoke", chuckNorrisResponseMain.getChuckNorrisJoke());

        return "chuck_norris";
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

        }
        return "redirect:/game2";
    }

    @GetMapping("/game2")
    public String game2(Model model) {

        CityAction cityActionSelected = playerService.getCityActionSelected();

        if (cityActionSelected == CityAction.CHANGE_THE_CITY) {
            return showCitiesToChoose(model);

        } else if (cityActionSelected == CityAction.SHOW_CITY_ACTIONS) {
            return showCityActions(model);

        } else if (cityActionSelected == CityAction.MEET_WITH_GOOD_MERCHANT) {
            return meetMerchant(model);

        } else if (cityActionSelected == CityAction.GO_ON_VACATION) {
            return goOnVacation(model);

        } else if (cityActionSelected == CityAction.RANDOM_ACTION) {
            return getRandomAction(model);

        } else if (cityActionSelected == CityAction.RANDOM_ACTION2) {
            return getRandomAction2(model);

        } else if (cityActionSelected == CityAction.RANDOM_ACTION3) {
            return getRandomAction3(model);

        } else if (cityActionSelected == CityAction.GO_TO_LOCAL_COMPANY) {
            return goToLocalCompany(model);

        } else if (cityActionSelected == CityAction.GO_TO_LOCAL_COMPANY2) {
            return goToLocalCompany2(model);

        } else if (cityActionSelected == CityAction.GO_TO_LOCAL_COMPANY3) {
            return goToLocalCompany3(model);

        } else if (cityActionSelected == CityAction.GO_TO_WEAPON_STORE) {
            return goToWeaponStore(model);

        } else if (cityActionSelected == CityAction.CHOOSE_WEAPON_TO_FIGHT) {
            return showWeaponsToSelect(model);
        }

        return "/game2";
    }

}

