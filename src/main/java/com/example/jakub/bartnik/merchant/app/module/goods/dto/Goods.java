package com.example.jakub.bartnik.merchant.app.module.goods.dto;

public enum Goods {

    WOOD("WOOD"),
    IRON("IRON"),
    COPPER("COPPER");

    private String name;

    Goods(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
