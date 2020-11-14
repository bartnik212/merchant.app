package com.example.jakub.bartnik.merchant.app.module.enums.goods;

public enum CityAction {

    SHOW_CITY_ACTIONS("Show city actions"),
    MEET_WITH_GOOD_MERCHANT ("Meet with goods merchant"),
    GO_ON_VACATION ("Go on a one-day vacation"),
    CHANGE_THE_CITY ("Change the city"),
    RANDOM_ACTION ("Random action which can be something good to you, like for example, additional coin for you or attack of evil robbers!"),
    GO_TO_LOCAL_COMPANY ("Go to the local company to look for some good"),
    GO_TO_WEAPON_STORE ("Go to the weapon store"),
    CHOOSE_WEAPON_TO_FIGHT ("Choose the weapon to fight");

    private String name;

    CityAction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
}
