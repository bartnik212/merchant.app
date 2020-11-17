package com.example.jakub.bartnik.merchant.app.module.enums;

public enum CityAction {

    SHOW_CITY_ACTIONS("Show city actions"),
    MEET_WITH_GOOD_MERCHANT ("Meet with goods merchant"),
    GO_ON_VACATION ("Go on one-day vacation (5 coins)"),
    CHANGE_THE_CITY ("Change the city (5 coins)"),

    RANDOM_ACTION ("Random action which can be something good to you, like for example, additional coin for you or attack of evil robbers!"),
    RANDOM_ACTION2("Random Action2"),
    RANDOM_ACTION3("Random Action3"),

    GO_TO_LOCAL_COMPANY ("Go to the local company to look for some good"),
    GO_TO_LOCAL_COMPANY2 ("Go to local company2"),
    GO_TO_LOCAL_COMPANY3 ("Go to local company3"),


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
