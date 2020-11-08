package com.example.jakub.bartnik.merchant.app.module.enums.goods;

public enum Good {

    WOOD("wood"),
    IRON("iron"),
    COPPER("copper");

    private String name;

     Good(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
