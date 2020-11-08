package com.example.jakub.bartnik.merchant.app.module.services;

import com.example.jakub.bartnik.merchant.app.module.goods.enums.City;
import com.example.jakub.bartnik.merchant.app.module.goods.enums.GameState;
import com.example.jakub.bartnik.merchant.app.module.goods.enums.Good;
import com.example.jakub.bartnik.merchant.app.module.goods.enums.Weapon;
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
    private int coins;
    private int healthPoints; // = 100
    private City citySelected;
    private GameState gameState = GameState.ENTER_NAME;

    //lista city, miasta, w ktorych odiwedzielem warsztat
    //metody miast, a konkretnie te, ktore sie roznia

    public void saveGood(Good good){
        listOfGoods.add(good);
        log.info("chosen good: " + listOfGoods);

    }


    public void saveWeapon(Weapon weapon) {
        listOfWeapons.add(weapon);
        log.info("chosen weapon: " + listOfWeapons);
    }
}
