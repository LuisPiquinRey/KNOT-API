package com.luispiquinrey.MicroservicesUsers.Exceptions;

public class UserDeleteException extends RuntimeException{
    public UserDeleteException(String message){
        super(message);
    }
}
