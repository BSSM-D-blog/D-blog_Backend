package com.example.Dblog.error;

public enum ErrorStatus {

    OK(200, "OK"),
    BAD_REQUEST(400, "OK"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    FORBIDDEN(403, "FORBIDDEN"),
    NOT_FOUND(404, "NOT_FOUND"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR");

    int statusCode;
    String message;
    ErrorStatus(int statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }
}
