package com.example.jakub.bartnik.merchant.app.core.config;

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
//    private String weapon; zobaczymy czy będzie potrzebne do weapon selected
    private int coins;
    private int healthPoints; // = 100


    public void saveGood(Good good){
        listOfGoods.add(good);
        log.info("chosen good: " + listOfGoods);

    }


}
