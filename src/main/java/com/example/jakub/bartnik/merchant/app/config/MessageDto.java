package com.example.jakub.bartnik.merchant.app.config;

public class MessageDto {

    private final String value;

    public MessageDto(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}