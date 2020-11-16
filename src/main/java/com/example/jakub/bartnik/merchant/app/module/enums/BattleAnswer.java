package com.example.jakub.bartnik.merchant.app.module.enums;

public enum BattleAnswer {

    FIGHT ("Fight"),
    LEAVE ("Leave");

    private String name;

    BattleAnswer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
