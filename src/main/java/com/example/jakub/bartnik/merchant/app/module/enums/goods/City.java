package com.example.jakub.bartnik.merchant.app.module.enums.goods;

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
