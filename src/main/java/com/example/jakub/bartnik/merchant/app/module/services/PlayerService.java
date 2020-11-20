package com.example.jakub.bartnik.merchant.app.module.services;

import com.example.jakub.bartnik.merchant.app.module.enums.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Data
@Component
@SessionScope
@Slf4j
public class PlayerService {

    private String name;
    private List<Good> listOfGoods = new LinkedList<>();
    private List<Weapon> listOfWeapons = new LinkedList<>();
    private List<City> listOfCities = new LinkedList<>();
    private Weapon weaponSelected;
    private int coins; // = 25
    private int healthPoints; // = 100
    private City citySelected;
    private GameInitializationState gameInitializationState = GameInitializationState.ENTER_NAME;
    private MerchantAnswer merchantAnswer;
    private CityAction cityActionSelected;
    private Enemy enemy;


    //lista city, miasta, w ktorych odiwedzielem warsztat
    //metody miast, a konkretnie te, ktore sie roznia

    public void saveGood(Good good) {
        listOfGoods.add(good);
        log.info("chosen good: " + listOfGoods);

    }


    public void saveWeapon(Weapon weapon) {
        listOfWeapons.add(weapon);
        log.info("chosen weapon: " + listOfWeapons);
    }

    public Good getCurrentlyVisitingMerchantGood() {

        Good goodType = null;

        if (citySelected == City.GDANSK &&
                cityActionSelected == CityAction.MEET_WITH_GOOD_MERCHANT &&
                listOfGoods.contains(Good.WOOD)) {

            goodType = Good.WOOD;

        } else if (citySelected == City.WARSAW &&
                cityActionSelected == CityAction.MEET_WITH_GOOD_MERCHANT &&
                listOfGoods.contains(Good.IRON)) {

            goodType = Good.IRON;

        } else if (citySelected == City.ZAKOPANE &&
                cityActionSelected == CityAction.MEET_WITH_GOOD_MERCHANT &&
                listOfGoods.contains(Good.COPPER)) {

            goodType = Good.COPPER;
        }

        return goodType;
    }

    public VacationPlace getCurrentlyVisitingVacationPlace() {

        VacationPlace vacationPlace = null;

        if (citySelected == City.GDANSK &&
                cityActionSelected == CityAction.GO_ON_VACATION) {

            vacationPlace = VacationPlace.MOTLAWA;

        } else if (citySelected == City.WARSAW &&
                cityActionSelected == CityAction.GO_ON_VACATION) {
            vacationPlace = VacationPlace.VISTULA;

        } else if (citySelected == City.ZAKOPANE &&
                cityActionSelected == CityAction.GO_ON_VACATION) {
            vacationPlace = VacationPlace.GUBALOWKA;

        }

        return vacationPlace;
    }


    public City getCurrentlyVisitingLocalCompany() {

        return cityActionSelected == CityAction.GO_TO_LOCAL_COMPANY || cityActionSelected == CityAction.GO_TO_LOCAL_COMPANY2
                || cityActionSelected == CityAction.GO_TO_LOCAL_COMPANY3 ? citySelected : null;

    }

    public Enemy fightWithWorkerOfLocalCompany() {

        City citySelected = getCurrentlyVisitingLocalCompany();
        Enemy enemy = null;

        if (citySelected == City.GDANSK) {
            enemy = Enemy.FIREARMER;

        } else if (citySelected == City.WARSAW) {
            enemy = Enemy.TWOHANDEDSWORDER;

        } else if (citySelected == City.ZAKOPANE) {
            enemy = Enemy.SWORDSHIELDER;
        }

        return enemy;

    }


    public PositiveAnswerAction getPositiveAnswer() {

        PositiveAnswerAction positiveAnswerAction = null;

        if (citySelected == City.GDANSK && cityActionSelected == CityAction.MEET_WITH_GOOD_MERCHANT) {

            positiveAnswerAction = PositiveAnswerAction.GOOD_MERCHANT_GDANSK;

        } else if (citySelected == City.WARSAW && cityActionSelected == CityAction.MEET_WITH_GOOD_MERCHANT) {

            positiveAnswerAction = PositiveAnswerAction.GOOD_MERCHANT_WARSAW;

        } else if (citySelected == City.ZAKOPANE && cityActionSelected == CityAction.MEET_WITH_GOOD_MERCHANT) {

            positiveAnswerAction = PositiveAnswerAction.GOOD_MERCHANT_ZAKOPANE;
        }

        return positiveAnswerAction;
    }

    public Boolean checkIfItsInitializingSelectWeapon() {

        return gameInitializationState == GameInitializationState.SELECT_WEAPON;

    }

    public BattleResult paperScissorsRock(Enemy enemy) {

        BattleResult battleResult;

        if (weaponSelected == Weapon.SWORDSHIELD && enemy == Enemy.FIREARMER) {
            battleResult = BattleResult.WIN;

        } else if (weaponSelected == Weapon.FIREARM && enemy == Enemy.TWOHANDEDSWORDER) {
            battleResult = BattleResult.WIN;

        } else if (weaponSelected == Weapon.TWOHANDEDSWORD && enemy == Enemy.SWORDSHIELDER) {
            battleResult = BattleResult.WIN;

        } else if (weaponSelected == Weapon.FIREARM && enemy == Enemy.SWORDSHIELDER) {
            battleResult = BattleResult.LOSE;

        } else if (weaponSelected == Weapon.TWOHANDEDSWORD && enemy == Enemy.FIREARMER) {
            battleResult = BattleResult.LOSE;

        } else if (weaponSelected == Weapon.SWORDSHIELD && enemy == Enemy.TWOHANDEDSWORDER) {
            battleResult = BattleResult.LOSE;

        } else {
            battleResult = BattleResult.DRAW;

        }

        return battleResult;
    }

    public RandomAction getRandomAction() {
        Random random = new Random();
        int randomNumber = random.nextInt(6);
        RandomAction randomAction = null;


        switch (randomNumber) {

            case 1:
                randomAction = RandomAction.FIND_COINS;
                break;

            case 2:
                randomAction = RandomAction.FIND_COPPER;
                break;

            case 3:
                randomAction = RandomAction.FIND_IRON;
                break;

            case 4:
                randomAction = RandomAction.FIND_WOOD;
                break;

            case 5:
                randomAction = RandomAction.NEGATIVE_RANDOM_ACTION;
                break;

        }

        return randomAction;
    }

    public Enemy getNegativeRandomAction() {
        Random random = new Random();
        int randomNumber = random.nextInt(4);


        Enemy enemy = null;

        switch (randomNumber) {
            case 1:
                enemy = Enemy.SWORDSHIELDER;
                break;

            case 2:
                enemy = Enemy.TWOHANDEDSWORDER;
                break;

            case 3:
                enemy = Enemy.FIREARMER;
                break;
        }

        return enemy;
    }


}

