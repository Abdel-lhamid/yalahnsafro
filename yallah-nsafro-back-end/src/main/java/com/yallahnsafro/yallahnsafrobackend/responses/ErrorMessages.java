package com.yallahnsafro.yallahnsafrobackend.responses;

import lombok.Getter;

@Getter
public enum ErrorMessages {
    MISSING_REQUIRED_FIELD("Missing required field."),
    RECORD_ALREADY_EXISTS("Record already exists."),
    INTERNAL_SERVER_ERROR("Internal Server error."),
    NO_RECORD_FOUND("Record with provided ID is not found."),
    FIRST_LAST_NAME_ERROR("First name and last name should have only letters");

    private String errorMessage;

    private ErrorMessages (String errorMessage){
        this.errorMessage = errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
