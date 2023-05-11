package com.inditex.stockmanager.controller;

public class DBAccessException extends RuntimeException {

    public DBAccessException(String message) {
        super(String.format("Database call error: %s", message));
    }
}
