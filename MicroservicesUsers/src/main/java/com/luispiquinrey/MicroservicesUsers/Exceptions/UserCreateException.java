package com.luispiquinrey.MicroservicesUsers.Exceptions;

public class UserCreateException extends RuntimeException{
    public UserCreateException(String message){
        super(message);
    }
}
