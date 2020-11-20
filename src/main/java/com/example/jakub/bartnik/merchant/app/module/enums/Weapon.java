package com.example.jakub.bartnik.merchant.app.module.enums;

public enum Weapon {

    TWOHANDEDSWORD("Two Handed Sword"),
    SWORDSHIELD("Sword and Shield"),
    FIREARM("Fire Arm");

    private final String name;

    Weapon(String name) {
        this.name = name;
    }
//    private final Weapon winsWith;


//    Weapon(String name, Weapon winsWith) {
//        this.name = name;
//        this.winsWith = winsWith;
//    }

//    public boolean isWinningWith(Weapon otherWeapon) {

//        return this.winsWith == otherWeapon;
//    }

    public String getName() {
        return name;
    }
}
