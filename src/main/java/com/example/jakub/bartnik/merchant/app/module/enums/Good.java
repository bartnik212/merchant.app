package com.example.jakub.bartnik.merchant.app.module.enums;

public enum Good {

    WOOD("Wood"),
    IRON("Iron"),
    COPPER("Copper");

    private String name;

     Good(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
