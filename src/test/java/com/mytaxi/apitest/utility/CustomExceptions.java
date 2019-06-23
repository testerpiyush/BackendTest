package com.mytaxi.apitest.utility;

public class CustomExceptions extends Exception {
    String message;

    public CustomExceptions(String msg) {
        message = msg;
    }

    public String toString() {
        return "CustomException['User not found']";
    }
}
