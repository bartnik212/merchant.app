package com.example.jakub.bartnik.merchant.app.module.services;

import com.example.jakub.bartnik.merchant.app.core.config.ApplicationProperties;
import com.example.jakub.bartnik.merchant.app.module.enums.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.LinkedList;
import java.util.List;

@Data
@Component
@SessionScope
@Slf4j
public class PlayerService {

    private String name;
    private List<Good> listOfGoods = new LinkedList<>();
    private List<Weapon> listOfWeapons = new LinkedList<>();
    private Weapon weaponSelected;
    private int coins; // = 25
    private int healthPoints; // = 100
    private City citySelected;
    private GameInitializationState gameInitializationState = GameInitializationState.ENTER_NAME;
    private Answer answer;

    private CityAction cityActionSelected;


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

    // enum Enemy z każdym rodzajem broni

    // stworz metode getCurrentEnemy
    // musi ustalac w ktorym miescie jestesmy (mamy odzytane cityselected)
    // jesli cityselected to gdansk to enemy jest z huty miedzi

    // nastepnie, druga metoda fight

    // nastepnie trzecia metoda wdajSieWbojkeZZakladem (w controllerze)
    // 1 Krok = getCurrentEnemy
    // 2 krok = fight (papier kamien nozyce)
    // 3 krok = aktualizacja stanu gracza (zasoby, hp na podstawie bójki)

    public City getCurrentlyVisitingLocalCompany() {


        return cityActionSelected == CityAction.GO_TO_LOCAL_COMPANY ? citySelected : null;



//        City localCompany = null;
//
//        if(cityActionSelected == CityAction.GO_TO_LOCAL_COMPANY){
//            localCompany = citySelected;
//        }
//
//        return localCompany;
    }

    public Enemy fightWithWorkerOfLocalCompany() {

        City citySelected = getCurrentlyVisitingLocalCompany();
        Enemy enemy = null;

        if (citySelected == City.GDANSK) {
            enemy = Enemy.FIREARMER;

        } else if(citySelected == City.WARSAW) {
            enemy = Enemy.TWOHANDEDSWORDER;

        } else if(citySelected == City.ZAKOPANE){
            enemy = Enemy.SWORDSHIELDER;
        }

        return enemy;

    }



    public PositiveAnswerAction getPositiveAnswer () {

        PositiveAnswerAction positiveAnswerAction = null;

        if (citySelected == City.GDANSK && cityActionSelected == CityAction.MEET_WITH_GOOD_MERCHANT){

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

    public FightResult paperScissorsRock(Enemy enemy) {

        FightResult fightResult = null;

        if(weaponSelected == Weapon.SWORDSHIELD && enemy == Enemy.FIREARMER){
            fightResult = FightResult.WIN;

        }

        return fightResult;
    }
}

