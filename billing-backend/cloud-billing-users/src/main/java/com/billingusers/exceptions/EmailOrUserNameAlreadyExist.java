package com.billingusers.exceptions;

public class EmailOrUserNameAlreadyExist extends RuntimeException {
    public EmailOrUserNameAlreadyExist() {
        super("Email or Username already exists");
    }
}
