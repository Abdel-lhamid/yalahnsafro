package com.yallahnsafro.yallahnsafrobackend.exceptions;

public class UserException extends RuntimeException{

    private static final long serialVersionUID = -7977019023643006200L;

    public UserException(String message){
        super(message);
    }
}
