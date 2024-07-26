package com.srikanth.recipe_app.exception;

public class ElementAlreadyExistsException extends RuntimeException{
    public ElementAlreadyExistsException(String message) {
        super(message);
    }
}
