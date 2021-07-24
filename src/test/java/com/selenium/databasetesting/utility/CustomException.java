package com.selenium.databasetesting.utility;

public class CustomException extends Exception{
    ExceptionType type;

    public enum ExceptionType {
        FILE_NOT_EXIST,DB_CONNECTION_NOT_CONNECTED,USERNAME_ALLREADY_EXIST,
    }
    public CustomException(ExceptionType type, String message) {

        super(message);
        this.type = type;
    }
}
