package com.example.jakub.bartnik.merchant.app.module.goods.enums;

public enum City {

    GDANSK ("Gdansk"),
    WARSAW ("Warsaw"),
    ZAKOPANE ("Zakopane");

    private String name;
    private String entertainment;

//    localworkshop good
    //

    City(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
