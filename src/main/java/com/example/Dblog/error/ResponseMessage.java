package com.example.Dblog.error;

import lombok.Data;

@Data
public class ErrorMessage {
    private ErrorStatus status;
    private String message;
    private Object data;

    public ErrorMessage(){
        this.status = ErrorStatus.BAD_REQUEST;
        this.message = null;
        this.data = null;
    }
}
