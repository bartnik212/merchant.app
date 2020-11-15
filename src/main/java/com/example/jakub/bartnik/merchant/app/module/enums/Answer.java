package com.example.jakub.bartnik.merchant.app.module.enums;

public enum Answer {

    YES("Yes"),
    NO("No");

    private String name;

    Answer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
