package com.example.jakub.bartnik.merchant.app.module.enums;

public enum MerchantAnswer {

    YES("Yes"),
    NO("No");

    private String name;

    MerchantAnswer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
