package com.example.jakub.bartnik.merchant.app.module.goods.enums;

public enum Good {

    WOOD("WOOD"),
    IRON("IRON"),
    COPPER("COPPER");

    private String name;

     Good(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
