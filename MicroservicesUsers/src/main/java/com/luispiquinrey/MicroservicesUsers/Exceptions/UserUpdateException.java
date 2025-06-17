package com.luispiquinrey.MicroservicesUsers.Exceptions;

public class UserUpdateException extends RuntimeException{
    public UserUpdateException(String message){
        super(message);
    }
}
